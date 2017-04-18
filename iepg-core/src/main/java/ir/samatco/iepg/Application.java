package ir.samatco.iepg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * User: Maryam Sedghi Moradi
 */
@SpringBootApplication
@EnableAutoConfiguration()
@EnableScheduling
public class Application {
	public static void main(String[] args){
		SpringApplication.run(Application.class);
	}
}
