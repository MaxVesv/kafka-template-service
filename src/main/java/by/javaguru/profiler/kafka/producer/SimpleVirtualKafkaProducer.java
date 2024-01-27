package by.javaguru.profiler.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SimpleVirtualKafkaProducer {


    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendString(String topic, Integer partition, String key, String payload) {
        log.info("sending payload='{}' to topic='{}'", payload, topic);
        kafkaTemplate.send(topic, partition, key, payload);
    }

}
