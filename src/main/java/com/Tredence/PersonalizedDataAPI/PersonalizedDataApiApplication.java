package com.Tredence.PersonalizedDataAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class PersonalizedDataApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalizedDataApiApplication.class, args);
	}

}
