package by.javaguru.profiler.kafka;



import by.javaguru.profiler.kafka.consumer.SimpleKafkaConsumer;
import by.javaguru.profiler.kafka.producer.SimpleKafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

@ExtendWith({KafkaTestContainersExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@DirtiesContext
public class KafkaInfrastructureWithTestcontainersIntegrationTest {

    @Value("${custom_test.topic_for_inner_test}")
    private String topic;
    private Integer partition = 0;

    @Autowired
    private SimpleKafkaProducer producer;

    @Autowired
    private SimpleKafkaConsumer consumer;

    @Test
    void souldReturnStringMessageFromKafkaWhenSendCorrectStringMessage() throws Exception {
        String testString = "Test message!";
        String keyMessage = "Special key";

        producer.sendString(topic, partition, keyMessage, testString);

        log.info("From Integration test topic for sent: " + topic);
        log.info("From Integration test partition number for sent: " + partition);
        log.info("From Integration test key for message : " + keyMessage);
        log.info("From Integration test Message for sent: " + testString);

        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);
        log.info("messageConsumed: " + messageConsumed);
        Assertions.assertTrue(messageConsumed);

        log.info("!!!! Data from consumer.getPayloadMap(): " + consumer.getPayloadMap().toString());
        Assertions.assertTrue(consumer.getPayloadMap().containsKey(keyMessage));
        Assertions.assertEquals(testString, consumer.getPayloadMap().get(keyMessage));
    }


}
