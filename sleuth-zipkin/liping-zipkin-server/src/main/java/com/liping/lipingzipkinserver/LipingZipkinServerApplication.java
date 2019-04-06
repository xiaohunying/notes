package com.liping.lipingzipkinserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class LipingZipkinServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LipingZipkinServerApplication.class, args);
	}

}
