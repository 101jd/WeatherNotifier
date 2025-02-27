package org._jd;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

@SpringBootApplication
@EnableScheduling
public class Remote {

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
    public RestTemplate restTemplate() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault()));
    }

    public static void main(String[] args) {
        SpringApplication.run(Remote.class, args);
    }

}