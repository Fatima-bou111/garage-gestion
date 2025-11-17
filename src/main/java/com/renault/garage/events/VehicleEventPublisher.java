package com.renault.garage.events;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class VehicleEventPublisher {

    private final KafkaTemplate<String, VehicleEvent> kafkaTemplate;
    private final String topic;

    public VehicleEventPublisher(KafkaTemplate<String, VehicleEvent> kafkaTemplate,
                                 @Value("${app.kafka.topic.vehicle}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void publishVehicleCreated(VehicleEvent event) {
        kafkaTemplate.send(topic, String.valueOf(event.getId()), event);
    }
}

