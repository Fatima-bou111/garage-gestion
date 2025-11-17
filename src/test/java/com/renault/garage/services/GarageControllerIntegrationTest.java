package com.renault.garage.services;


import com.renault.garage.entities.Garage;
import com.renault.garage.events.VehicleEventPublisher;
import com.renault.garage.repositories.GarageRepository;
import com.renault.garage.repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GarageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @MockBean
    private VehicleEventPublisher vehicleEventPublisher;

    private Garage garage;

    @BeforeEach
    void setup() {
        vehicleRepository.deleteAll();
        garageRepository.deleteAll();

        garage = new Garage();
        garage.setName("Garage Test");
        garage.setEmail("test@garage.com");
        garage.setAddress("123 Rue de Garage");
        garage.setTelephone("+33123456789");
        garage = garageRepository.save(garage);
    }

    @Test
    void addVehicle_success_when_quota_not_reached() throws Exception {
        String payload = """
        {
            "brand": "Renault",
            "model": "Megane E-Tech",
            "year": 2023,
            "fuelType": "ELECTRIQUE"
        }
        """;

        mockMvc.perform(post("/api/garages/{id}/vehicles", garage.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk());

        // Vérifier qu'un véhicule a bien été ajouté dans ce garage
        long count = vehicleRepository.countByGarageId(garage.getId());
        assertThat(count).isEqualTo(1);

        // Vérifier que ce véhicule a bien les bonnes informations
        var vehicles = vehicleRepository.findByGarageId(garage.getId());
        assertThat(vehicles).hasSize(1);
        assertThat(vehicles.get(0).getBrand()).isEqualTo("Renault");
        assertThat(vehicles.get(0).getModel()).isEqualTo("Megane E-Tech");
        assertThat(vehicles.get(0).getFuelType().toString()).isEqualTo("ELECTRIQUE");
    }
}
