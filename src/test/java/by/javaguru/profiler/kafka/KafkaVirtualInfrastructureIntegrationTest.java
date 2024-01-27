package by.javaguru.profiler.kafka;


import by.javaguru.profiler.kafka.consumer.SimpleVirtualKafkaConsumer;
import by.javaguru.profiler.kafka.producer.SimpleVirtualKafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.concurrent.TimeUnit;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(
        partitions = 1,
        topics = {"virtual-kafka-topic1"},
        brokerProperties = { "listeners=PLAINTEXT://localhost:9093"},
        ports = 9093,
        count = 1
)
@Slf4j
public class KafkaVirtualInfrastructureIntegrationTest {

    @Value("${custom_test.topic_for_virtual_kafka_test}")
    private String topic;
    private Integer partition = 0;

    @Autowired
    private SimpleVirtualKafkaConsumer consumer;

    @Autowired
    private SimpleVirtualKafkaProducer producer;


    @Test
    void shouldReturnCorrectMessageFromVirtualKafkaWhenSendCorrectMessage() throws Exception {

        String testString = "Virtual message!";
        String keyMessage = "Virtual key";

        producer.sendString(topic, partition, keyMessage, testString);
        log.info("!!!!!!Virtual kafka: sending payload='{}' to topic='{}'", testString, topic);

        log.info("Virtual kafka: From Integration test topic for sent: " + topic);
        log.info("Virtual kafka: From Integration test partition number for sent: " + partition);
        log.info("Virtual kafka: From Integration test key for message : " + keyMessage);
        log.info("Virtual kafka: From Integration test Message for sent: " + testString);

        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);
        log.info("!!!!!!!Virtual kafka: messageConsumed: " + messageConsumed);
        Assertions.assertTrue(messageConsumed);

        log.info("!!!!!!!Virtual kafka: !!!! ПОЛУЧАЕМ ДАННЫЕ из consumer.getPayloadMap(): " + consumer.getPayloadMap().toString());
        Assertions.assertTrue(consumer.getPayloadMap().containsKey(keyMessage));
        Assertions.assertEquals(testString, consumer.getPayloadMap().get(keyMessage));

    }

}
