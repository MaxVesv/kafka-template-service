package by.javaguru.profiler.service;

import by.javaguru.profiler.kafka.producer.SimpleKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SenderTestMessageToKafka {

    @Value("${custom_test.topic_for_inner_test}")
    private String topic;
    private final Integer partition = 0;
    private final String keyMessage = "key-test";
    private final Integer maxCount = 100;
    private Integer counter = 0;

    private final SimpleKafkaProducer producer;

    @Scheduled(fixedDelay = 2000)
    public void sendMessage() {
        if (counter <= maxCount) {
            log.info("!!!!!!!SEND TO KAFKA!!!!");
            counter += 1;
            String message = "Hello, Kafka! " + counter;
            producer.sendString(topic, partition, keyMessage, message);
            System.out.println("Message sent: " + message);
        }
    }

}
