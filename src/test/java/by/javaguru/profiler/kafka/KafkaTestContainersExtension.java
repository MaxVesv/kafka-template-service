package by.javaguru.profiler.kafka;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

import static java.lang.Thread.sleep;


@Slf4j
public class KafkaTestContainersExtension implements BeforeAllCallback {

    private final Properties properties = getValueFromApplicationProject();
    public static String portNumberStartedContainer;


    private final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/" + properties.getProperty("NAME_IMAGE_KAFKA")))
                .withKraft()
                .withEnv("KAFKA_ADVERTISED_LISTENERS", properties.getProperty("KAFKA_ADVERTISED_LISTENERS"))
                .withEnv("KAFKA_LISTENER_SECURITY_PROTOCOL_MAP", properties.getProperty("KAFKA_LISTENER_SECURITY_PROTOCOL_MAP"))
                .withEnv("KAFKA_PROCESS_ROLES", properties.getProperty("KAFKA_PROCESS_ROLES"))
                .withEnv("KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR", properties.getProperty("KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR"))
                .withEnv("KAFKA_CONTROLLER_QUORUM_VOTERS", properties.getProperty("KAFKA_CONTROLLER_QUORUM_VOTERS"))
                .withEnv("KAFKA_INTER_BROKER_LISTENER_NAME", properties.getProperty("KAFKA_INTER_BROKER_LISTENER_NAME"))
                .withEnv("KAFKA_CONTROLLER_LISTENER_NAMES", properties.getProperty("KAFKA_CONTROLLER_LISTENER_NAMES"))
//                .withEnv("KAFKA_LISTENERS", properties.getProperty("KAFKA_LISTENERS"))
                .withEnv("CLUSTER_ID", properties.getProperty("CLUSTER_ID"))
                .withEnv("KAFKA_NODE_ID", properties.getProperty("KAFKA_NODE_ID"));


    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {

        kafka.start();

        while (kafka.getBootstrapServers() == null) {
            sleep(2000);
        }

        portNumberStartedContainer = kafka.getBootstrapServers();
        System.setProperty("spring.kafka.bootstrap-servers", portNumberStartedContainer);
    }

    private Properties getValueFromApplicationProject() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("./kafka/kafka.env")) {
            properties.load(input);
            if (input == null) {
                System.out.println("Sorry, cant find kafka.env");
                return null;
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error reading kafka.env . Check for the file.", e);
        }
        return properties;
    }
}
