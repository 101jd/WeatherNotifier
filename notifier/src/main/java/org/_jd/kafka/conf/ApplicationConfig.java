package org._jd.kafka.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org._jd.domain.NotUser;
import org._jd.domain.Note;
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
    public ConcurrentKafkaListenerContainerFactory<Integer, Note> kafkaListenerContainerFactory(
            ConsumerFactory<Integer, Note> consumerFactory
    ){
        ConcurrentKafkaListenerContainerFactory<Integer, Note> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, Note> consumerFactory(Map<String, Object> consumerProps){
        return new DefaultKafkaConsumerFactory<>(consumerProps);
    }

    @Bean
    public Map<String, Object> consumerProps(){
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "all-data-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Note.class.getName());
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "not");

        return props;
    }

    @Bean
    public Map<String, Object> consumerUserProps(){
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "all-data-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, NotUser.class.getName());
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "not");

        return props;
    }

    @Bean
    ConsumerFactory<Integer, NotUser> consumerFactoryUser(){
        return new DefaultKafkaConsumerFactory<>(consumerUserProps());
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<Integer, NotUser> userKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryUser());

        return factory;
    }

    @Bean
    public BrokerRegistrationRequestData.Listener listener(){
        return new BrokerRegistrationRequestData.Listener();
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
    public Map<String, Object> kaconfs(){
        Map<String, Object> confs = new HashMap<>();

        confs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        return confs;
    }

    @Bean
    public KafkaAdmin kafkaAdmin(){
        return new KafkaAdmin(kaconfs());
    }


    @Bean
    public Map<String, Object> prodConfsByte(){
        Map<String, Object> confs = new HashMap<>();

        confs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        confs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        confs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        confs.put(ProducerConfig.ACKS_CONFIG, "all");

        return confs;
    }

    @Bean
    public ProducerFactory<Integer, NotUser> producerFactoryByte(){
        return new DefaultKafkaProducerFactory<>(prodConfsByte(), () -> keySerializer(), () -> serializer());
    }

    @Bean
    public KafkaTemplate<Integer, NotUser> kafkaTemplateByte(){
        return new KafkaTemplate<>(producerFactoryByte(),
                Collections.singletonMap(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class));
    }

    @Bean
    public NewTopic userTopic(){
        return TopicBuilder.name("user-data").partitions(1).compact().build();
    }


}
