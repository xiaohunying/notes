package com.liping.lipingzipkinclient1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@Configuration
@SpringBootApplication
public class LipingZipkinClient1Application {

	public static void main(String[] args) {
		SpringApplication.run(LipingZipkinClient1Application.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@GetMapping
	public String client1() {
		log.info("Client 1");
		final ResponseEntity res = restTemplate().getForEntity("http://localhost:8181/", String.class);
		return "Client 1 => " + res.getBody().toString();
	}
}
