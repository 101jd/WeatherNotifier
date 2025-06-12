package org._jd.kafka.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org._jd.domain.weatherobj.inter.Notes;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.message.BrokerRegistrationRequestData;
import org.apache.kafka.common.serialization.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org._jd.domain.requestsobj.RemUser;

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
    public KafkaAdmin kafkaAdmin(){
        Map<String, Object> confs = new HashMap<>();

        confs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        return new KafkaAdmin(confs);
    }

    @Bean
    public NewTopic weatherTopic(){
        return TopicBuilder.name("weather-data").partitions(1).compact().build();
    }

    @Bean
    public ProducerFactory<Integer, Notes> producerFactoryByte(){
        Map<String, Object> confsByte = new HashMap<>();

        confsByte.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        confsByte.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        confsByte.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        confsByte.put(ProducerConfig.ACKS_CONFIG, "all");

        return new DefaultKafkaProducerFactory<>(confsByte, () -> keySerializer(), () -> serializer());
    }

    @Bean
    public KafkaTemplate<Integer, Notes> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactoryByte(), Collections.singletonMap(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class));
    }

    // CONSUMER

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, RemUser> kafkaListenerContainerFactory(
            ConsumerFactory<Integer, RemUser> consumerFactory
    ){
        ConcurrentKafkaListenerContainerFactory<Integer, RemUser> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, RemUser> consumerFactory(Map<String, Object> consProps){
        return new DefaultKafkaConsumerFactory<>(consProps);
    }

    @Bean
    public Map<String, Object> consProps(){
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "remote-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "rem");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, RemUser.class.getName());
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");

        return props;
    }

    @Bean
    public BrokerRegistrationRequestData.Listener listener(){
        return new BrokerRegistrationRequestData.Listener();
    }
}
