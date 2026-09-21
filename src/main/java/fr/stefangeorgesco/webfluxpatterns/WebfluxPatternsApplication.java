package fr.stefangeorgesco.webfluxpatterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fr.stefangeorgesco.webfluxpatterns.${package}")
public class WebfluxPatternsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxPatternsApplication.class, args);
	}

}
