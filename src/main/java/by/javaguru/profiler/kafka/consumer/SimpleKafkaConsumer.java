package by.javaguru.profiler.kafka.consumer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
@Getter
public class SimpleKafkaConsumer {

    private CountDownLatch latch = new CountDownLatch(1);       // for testing
    private Map<String, String> payloadMap = new HashMap<>();

    @KafkaListener(topics = "${custom_test.topic_for_inner_test}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        log.info("received payload='{}'", consumerRecord.toString());

        payloadMap.put((String) consumerRecord.key(), (String) consumerRecord.value());
        log.info("!!!!!!!! Map<String,String> payloadMap=" + payloadMap.toString());

        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }
}


