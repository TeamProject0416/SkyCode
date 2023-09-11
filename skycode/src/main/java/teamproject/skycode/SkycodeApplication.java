package teamproject.skycode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class SkycodeApplication {
	public static void main(String[] args) {
		SpringApplication.run(SkycodeApplication.class, args);
	}
}
