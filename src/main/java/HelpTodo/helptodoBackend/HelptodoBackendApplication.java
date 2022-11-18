package HelpTodo.helptodoBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@PropertySource("classpath:/env.properties")
public class HelptodoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelptodoBackendApplication.class, args);
	}

}
