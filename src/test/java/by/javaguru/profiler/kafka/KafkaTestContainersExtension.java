package by.javaguru.profiler.kafka;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Slf4j
public class KafkaTestContainersExtension implements BeforeAllCallback {

    public static KafkaContainer kafka = null;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {

        Properties properties = getValueFromApplicationProject();

        kafka = new KafkaContainer(DockerImageName.parse("confluentinc/" + properties.getProperty("NAME_IMAGE_KAFKA")))
                .withKraft()
                .withNetworkAliases(properties.getProperty("KAFKA_HOSTNAME_ALIAS_NETWORK"))
                .withEnv("KAFKA_NODE_ID", properties.getProperty("KAFKA_NODE_ID"))
                .withEnv("KAFKA_LISTENER_SECURITY_PROTOCOL_MAP", properties.getProperty("KAFKA_LISTENER_SECURITY_PROTOCOL_MAP"))
                .withEnv("KAFKA_ADVERTISED_LISTENERS", properties.getProperty("KAFKA_ADVERTISED_LISTENERS"))
                .withEnv("KAFKA_JMX_PORT", properties.getProperty("KAFKA_JMX_PORT"))
                .withEnv("KAFKA_JMX_HOSTNAME", properties.getProperty(("KAFKA_JMX_HOSTNAME")))
                .withEnv("KAFKA_PROCESS_ROLES", properties.getProperty("KAFKA_PROCESS_ROLES"))
                .withEnv("KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR", properties.getProperty("KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR"))
                .withEnv("KAFKA_CONTROLLER_QUORUM_VOTERS", properties.getProperty("KAFKA_CONTROLLER_QUORUM_VOTERS"))
                .withEnv("KAFKA_LISTENERS", properties.getProperty("KAFKA_LISTENERS"))
                .withEnv("KAFKA_INTER_BROKER_LISTENER_NAME", properties.getProperty("KAFKA_INTER_BROKER_LISTENER_NAME"))
                .withEnv("KAFKA_CONTROLLER_LISTENER_NAMES", properties.getProperty("KAFKA_CONTROLLER_LISTENER_NAMES"))
                .withEnv("CLUSTER_ID", properties.getProperty("CLUSTER_ID"));

    }

    @DynamicPropertySource
    static void kafkaSetProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }



    private Properties getValueFromApplicationProject() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("./kafka/kafka.env")) {
            properties.load(input);
            if (input == null) {
                System.out.println("Sorry, cant find kafka.env");
                return null;
            }
            // Загрузка свойств из файла
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error reading kafka.env . Check for the file.", e);
        }
        return properties;
    }
}
