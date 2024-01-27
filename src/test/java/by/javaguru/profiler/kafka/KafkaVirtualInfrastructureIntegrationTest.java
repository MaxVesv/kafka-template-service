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
import org.springframework.test.context.TestExecutionListeners;

import java.util.concurrent.TimeUnit;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(
        partitions = 1,
        topics = {"virtual-kafka-topic1", "inner-test-topic"},
        brokerProperties = { "listeners=PLAINTEXT://localhost:9097"},
        ports = 9097,
        count = 1
)
@Slf4j
@TestExecutionListeners(
        listeners = BeforeContextLoadListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class KafkaVirtualInfrastructureIntegrationTest {

//    Для встроенного Kafka используется @EmbeddedKafka на порту 9093 (потом учто на 9092 висят тесты с testcontainers),
//    но у нас есть сервис, который стучится постоянно в порт 9092, который в этом тесте будет приводить к ошибке
//    и для этого мы для него добавляем топик "inner-test-topic" и через аннотацию @TestExecutionListeners меняем порт
//    перед загрузкой всего контекста (можно все что угодно поменять в контексте) - все стучаться на 9093 @EmbeddedKafka

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

        boolean messageConsumed = consumer.getLatch().await(20, TimeUnit.SECONDS);
        log.info("!!!!!!!Virtual kafka: messageConsumed: " + messageConsumed);
        Assertions.assertTrue(messageConsumed);

        log.info("!!!!!!!Virtual kafka: !!!! data from consumer.getPayloadMap(): " + consumer.getPayloadMap().toString());
        Assertions.assertTrue(consumer.getPayloadMap().containsKey(keyMessage));
        Assertions.assertEquals(testString, consumer.getPayloadMap().get(keyMessage));

    }

}
