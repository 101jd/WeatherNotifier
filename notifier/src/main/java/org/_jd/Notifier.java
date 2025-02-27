package org._jd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

@SpringBootApplication
@EnableScheduling
public class Notifier {

    @Bean
    public Logger logger(){
        Logger logger = Logger.getLogger("log");
        Handler handler = new ConsoleHandler();
        logger.addHandler(handler);

        return logger;
    }

    @Bean
    public HttpHeaders headers(){
        return new HttpHeaders();
    }

    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Notifier.class, args);
    }

}