# Sleuth (Spring Cloud Sleuth)

Sleuth identify logs pertaining to a specific job, thread, or request. Sleuth integrates effortlessly with logging frameworks like Logback and SLF4J to add unique identifiers that help track and diagnose issues using logs.

## Trace

Traces can be thought of like a single request or job that is triggered in an application. All the various steps in that request, even across application and thread boundaries, will have the same traceId.

## Span
Spans can be thought of as sections of a job or request. A single trace can be composed of multiple spans each correlating to a specific step or section of the request. Using trace and span ids we can pinpoint exactly when and where our application is as it processes a request. Making reading our logs much easier.

## Example:

The part in the beginning of the log between the brackets is the core information that Spring Sleuth has added. The data follows the format of:

**[application name, traceId, spanId, export]**

~~~
2017-01-10 22:36:38.254  INFO 
  [Hello Sleuth App,4e30f7340b3fb631,4e30f7340b3fb631,false] 12516 
  --- [nio-8080-exec-1] c.b.spring.session.SleuthController : Hello Sleuth
~~~

**Export** is a boolean that indicates whether or not this log was exported to an aggregator like *Zipkin*.

## Manually add span

~~~
Span newSpan = tracer.nextSpan.name("newSpan").start();
try (SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
    // Do something
	logger.info("In the new span");
} finally {
    newSpan.finish();
}
~~~

The tracer is an instance of Tracer which is created by Spring Sleuth during startup.

## Span in thread

~~~
@Configuration
public class ThreadConfig {
 
    @Autowired
    private BeanFactory beanFactory;
 
    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor
         = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(1);
        threadPoolTaskExecutor.setMaxPoolSize(1);
        threadPoolTaskExecutor.initialize();
 
        return new LazyTraceExecutor(beanFactory, threadPoolTaskExecutor);
    }
}
~~~

**LazyTraceExecutor** comes from the Sleuth library and is a special kind of executor that will propagate *traceIds* to new threads and create new *spanIds* in the process.

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




