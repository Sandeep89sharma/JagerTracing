package co.jagerpoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
public class JaegerTraceController
{
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private Tracer tracer;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot! ";
    }

    @RequestMapping("/helloWorld")
    public String helloWorld() {
        return "Hello World from Spring Boot! ";
    }

    @GetMapping("/chaining")
    public String chaining() {

        Span span = tracer.buildSpan("generate-name").start();

        Span helloSpan = tracer.buildSpan("hello-service").asChildOf(span).start();
        ResponseEntity<String> responsehello = restTemplate.getForEntity("http://localhost:8053/hello", String.class);
        helloSpan.finish();

        Span helloWorldSpan = tracer.buildSpan("hello-world-service").asChildOf(span).start();
        ResponseEntity<String> responseHelloWOrld = restTemplate.getForEntity("http://localhost:8053/helloWorld", String.class);
        helloWorldSpan.finish();

        span.finish();
        return responsehello.getBody() + "-->" + responseHelloWOrld.getBody();

    }
}
