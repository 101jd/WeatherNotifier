package org._jd.kafka.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org._jd.domain.RegUser;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class ApplicationConfig {

    @Bean
    public ObjectMapper objectMapper(){
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public Serializer serializer(){
        return new JsonSerializer();
    }

    @Bean
    public Serializer keySerializer(){
        return new IntegerSerializer();
    }

    @Bean
    public KafkaAdmin admin(){
        Map<String, Object> confs = new HashMap<>();
        confs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        return new KafkaAdmin(confs);
    }

    @Bean
    public NewTopic regTopic(){
        return TopicBuilder.name("reg-data")
                .partitions(2)
                .compact().build();
    }

    @Bean
    public KafkaTemplate<Integer, RegUser> bytesTemplate(ProducerFactory<Integer, RegUser> pf) {
        return new KafkaTemplate<>(pf,
                Collections.singletonMap(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class));
    }


    @Bean
    public ProducerFactory<Integer, RegUser> byteProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps, () -> keySerializer(), () -> serializer());
    }
}
