package co.jagerpoc;

import io.jaegertracing.internal.JaegerTracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.jaegertracing.Configuration;

@SpringBootApplication
public class JagerTracingApplication {

    @Bean
    public static JaegerTracer getTracer() {
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        Configuration config = new Configuration("jaeger elastic search trace").withSampler(samplerConfig).withReporter(reporterConfig);
        return config.getTracer();
    }

    public static void main(String args[])
    {
        SpringApplication.run(JagerTracingApplication.class,args);
    }
}
