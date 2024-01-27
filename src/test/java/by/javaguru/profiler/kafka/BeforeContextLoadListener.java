package by.javaguru.profiler.kafka;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class BeforeContextLoadListener implements TestExecutionListener {

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {

        TestExecutionListener.super.beforeTestClass(testContext);
        System.setProperty("spring.kafka.bootstrap-servers", "localhost:9097");
    }
}
