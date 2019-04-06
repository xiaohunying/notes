package com.liping.lipingzipkinclient2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@SpringBootApplication
public class LipingZipkinClient2Application {

	public static void main(String[] args) {
		SpringApplication.run(LipingZipkinClient2Application.class, args);
	}

	@GetMapping
	public String client2() {
		log.info("Client 2");
		return "Client 2";
	}
}
