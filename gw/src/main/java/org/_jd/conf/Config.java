package org._jd.conf;

import org.springframework.boot.autoconfigure.web.client.RestClientBuilderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class Config {
    @Bean
    public RestClient restClient(){
        return RestClient.builder().build();
    }

    @Bean
    public RestClient.Builder builder(){
        return RestClient.builder();
    }
}
