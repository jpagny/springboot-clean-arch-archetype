package ${package}.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "${package}")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
