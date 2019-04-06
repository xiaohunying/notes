# Zipkin

Zipkin is an open source project that provides mechanisms for sending, receiving, storing, and visualizing traces. This allows us to correlate activity between servers and get a much clearer picture of exactly what is happening in our services.

# Example

## Zipkin Server

Create a spring boot application. Add dependencies in *Pom.xml*:

~~~
<dependency>
	<groupId>io.zipkin.java</groupId>
	<artifactId>zipkin-server</artifactId>
</dependency>
<dependency>
	<groupId>io.zipkin.java</groupId>
	<artifactId>zipkin-autoconfigure-ui</artifactId>
</dependency>
~~~

Enable zipkin server in the main class:

~~~
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
~~~

Configure port in *application.properties*:

~~~
spring.application.name=liping-zipkin-server
server.port=9411
~~~

## Zipkin Clients

Create two spring boot applications. Add dependencies in *Pom.xml*:

~~~
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
~~~

Configure zipkin in *application.properties*:

~~~
spring.application.name=liping-zipkin-client1
spring.zipkin.base-url=http://localhost:9411/
spring.sleuth.sampler.probability=1.0
~~~

Have one client app call the other's Rest API. Open browser and go to http://localhost:9411/zipkin/. You can trace the request from zipkin UI.



