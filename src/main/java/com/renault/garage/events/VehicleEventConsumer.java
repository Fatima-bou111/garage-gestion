package com.renault.garage.events;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class VehicleEventConsumer {
    private final Logger log = LoggerFactory.getLogger(VehicleEventConsumer.class);

    @KafkaListener(topics = "${app.kafka.topic.vehicle}", groupId = "${spring.kafka.consumer.group-id}")
    public void onVehicleEvent(VehicleEvent event) {
        log.info("Received vehicle event: id={}, action={}, timestamp={}",
                event.getId(), event.getAction(), event.getTimestamp());
    }
}
