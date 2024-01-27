//package by.javaguru.profiler.kafka;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.extension.BeforeAllCallback;
//import org.junit.jupiter.api.extension.ExtensionContext;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//
//import org.testcontainers.containers.KafkaContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.utility.DockerImageName;
//
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Objects;
//import java.util.Properties;
//import java.util.function.Supplier;
//
//import static java.lang.Thread.sleep;
//
//
//@Slf4j
//@Testcontainers
//public class KafkaTestContainersExtension implements BeforeAllCallback {
//
//    private final Properties properties = getValueFromApplicationProject();
//    public static String portNumberStartedContainer;
//
//    @Container
//    private final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/" + properties.getProperty("NAME_IMAGE_KAFKA")))
//                .withKraft()
//                .withEnv("KAFKA_NODE_ID", properties.getProperty("KAFKA_NODE_ID"))
//                .withEnv("KAFKA_LISTENER_SECURITY_PROTOCOL_MAP", properties.getProperty("KAFKA_LISTENER_SECURITY_PROTOCOL_MAP"))
//                .withEnv("KAFKA_ADVERTISED_LISTENERS", properties.getProperty("KAFKA_ADVERTISED_LISTENERS"))
//                .withEnv("KAFKA_JMX_PORT", properties.getProperty("KAFKA_JMX_PORT"))
//                .withEnv("KAFKA_JMX_HOSTNAME", properties.getProperty(("KAFKA_JMX_HOSTNAME")))
//                .withEnv("KAFKA_PROCESS_ROLES", properties.getProperty("KAFKA_PROCESS_ROLES"))
//                .withEnv("KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR", properties.getProperty("KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR"))
//                .withEnv("KAFKA_CONTROLLER_QUORUM_VOTERS", properties.getProperty("KAFKA_CONTROLLER_QUORUM_VOTERS"))
//                .withEnv("KAFKA_LISTENERS", properties.getProperty("KAFKA_LISTENERS"))
//                .withEnv("KAFKA_INTER_BROKER_LISTENER_NAME", properties.getProperty("KAFKA_INTER_BROKER_LISTENER_NAME"))
//                .withEnv("KAFKA_CONTROLLER_LISTENER_NAMES", properties.getProperty("KAFKA_CONTROLLER_LISTENER_NAMES"))
//                .withEnv("CLUSTER_ID", properties.getProperty("CLUSTER_ID"));
//
//
//    @Override
//    public void beforeAll(ExtensionContext extensionContext) throws Exception {
//
////        kafka = new KafkaContainer(DockerImageName.parse("confluentinc/" + properties.getProperty("NAME_IMAGE_KAFKA")))
////        kafka = new KafkaContainer()
////                .withKraft()
////                .withEnv("KAFKA_NODE_ID", properties.getProperty("KAFKA_NODE_ID"))
////                .withEnv("KAFKA_LISTENER_SECURITY_PROTOCOL_MAP", properties.getProperty("KAFKA_LISTENER_SECURITY_PROTOCOL_MAP"))
////                .withEnv("KAFKA_ADVERTISED_LISTENERS", properties.getProperty("KAFKA_ADVERTISED_LISTENERS"))
////                .withEnv("KAFKA_JMX_PORT", properties.getProperty("KAFKA_JMX_PORT"))
////                .withEnv("KAFKA_JMX_HOSTNAME", properties.getProperty(("KAFKA_JMX_HOSTNAME")))
////                .withEnv("KAFKA_PROCESS_ROLES", properties.getProperty("KAFKA_PROCESS_ROLES"))
////                .withEnv("KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR", properties.getProperty("KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR"))
////                .withEnv("KAFKA_CONTROLLER_QUORUM_VOTERS", properties.getProperty("KAFKA_CONTROLLER_QUORUM_VOTERS"))
////                .withEnv("KAFKA_LISTENERS", properties.getProperty("KAFKA_LISTENERS"))
////                .withEnv("KAFKA_INTER_BROKER_LISTENER_NAME", properties.getProperty("KAFKA_INTER_BROKER_LISTENER_NAME"))
////                .withEnv("KAFKA_CONTROLLER_LISTENER_NAMES", properties.getProperty("KAFKA_CONTROLLER_LISTENER_NAMES"))
////                .withEnv("CLUSTER_ID", properties.getProperty("CLUSTER_ID"));
//        kafka.start();
//
//        while (kafka.getBootstrapServers() == null) {
//            log.info("Переходим в сон после ожидания!!!!!!!!!!!!!!!!!!!!!!!");
//            sleep(1000);
//            log.info("Проснулись после ожидания!!!!!!!!!!!!!!!!!!!!!!!");
//        }
//        log.info("Вытаскивваем номер порта!!!!!!!!!!!!!!!!!!!!!!!");
//        portNumberStartedContainer = kafka.getBootstrapServers();
//        log.info("!!!!!!!!!!!!portNumberStartedContainer = " + portNumberStartedContainer);
//        System.setProperty("spring.kafka.bootstrap-servers", portNumberStartedContainer);
//
//    }
//
//    private Properties getValueFromApplicationProject() {
//        Properties properties = new Properties();
//        try (InputStream input = getClass().getClassLoader().getResourceAsStream("./kafka/kafka.env")) {
//            properties.load(input);
//            if (input == null) {
//                System.out.println("Sorry, cant find kafka.env");
//                return null;
//            }
//            // Загрузка свойств из файла
//            properties.load(input);
//        } catch (IOException e) {
//            throw new RuntimeException("Error reading kafka.env . Check for the file.", e);
//        }
//        return properties;
//    }
//}
