package org._jd.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org._jd.domain.Note;
import org._jd.domain.NotRemResponse;
import org._jd.domain.NotUser;
import org._jd.repo.NotUserRepo;
import org._jd.repo.NotesRepo;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


@Service
public class NotifyService {

    @Autowired
    private HttpHeaders headers;

    @Autowired
    private RestTemplate template;

    @Autowired
    private NotUserRepo repo;

    @Autowired
    private NotesRepo notesRepo;

    @Autowired
    private org._jd.service.MailGateway gateway;

    @Autowired
    Logger logger;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    KafkaTemplate<Integer, NotUser> kafkaTemplate;


    private String REMOTE = "http://127.0.0.1:8087/rem";
    private String PING = "/ping";

    public List<NotUser> getAllUsers(){
        return repo.getAllUsers();
    }

    public Boolean addUser(NotUser user){
        return repo.addUser(user);
    }


    public Boolean sendDataToRemote(NotUser user){
        ProducerRecord<Integer, NotUser> record = new ProducerRecord<>(
                "user-data", user.hashCode(), user
        );

        SendResult<Integer, NotUser> result = new SendResult<>(record, null);

        CompletableFuture<SendResult<Integer, NotUser>> future = kafkaTemplate.send(record);
        logger.info("send user-data: " + user.getCity());

        return future.complete(result);
    }

    @KafkaListener(topics = "weather-data", groupId = "all-data-group")
    public Boolean listenWeather(Note payload){

            return notesRepo.addNote(payload);

    }

    private boolean remoteIsUp(){
        try{
            return template.exchange(
                    REMOTE + PING, HttpMethod.GET, null, Boolean.class).getBody();
        }catch (RestClientException e){
            return false;
        }
    }


    @PostConstruct
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void start() throws org._jd.exceptions.EmailException {

        if (remoteIsUp()) {

            for (NotUser user : getAllUsers()){
                sendDataToRemote(user);
            }

            logger.info("Sending messages...");
            for (Note note : notesRepo.getAllNotes()){
                if (!note.getNote().equals("ERROR") && !note.getNote().contains("no incidents"))
                        gateway.sendEmail("info@localhost", note.getEmail(), "WEATHER", note.getNote());
            }
        } else logger.warning("REMOTE SERVICE IS DOWN");
    }

    public NotUser listenUser(NotUser user) throws JsonProcessingException {
        repo.addUser(user);
        logger.info("message recieved " + user);
        return user;
    }

}
