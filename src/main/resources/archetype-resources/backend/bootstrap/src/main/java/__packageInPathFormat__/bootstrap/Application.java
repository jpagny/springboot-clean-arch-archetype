package ${package}.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "${package}")
@EnableJpaRepositories(basePackages = "${package}.external")
@EntityScan(basePackages = "${package}.external")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
