package by.javaguru.profiler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProfilerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfilerApplication.class, args);

    }

}
