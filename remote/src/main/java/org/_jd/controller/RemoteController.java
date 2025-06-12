package org._jd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org._jd.domain.requestsobj.RemUser;
import org._jd.domain.weatherobj.RemResponseObj;

import org._jd.domain.weatherobj.inter.Notes;
import org._jd.service.RemoteService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/rem")
public class RemoteController {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    KafkaTemplate<Integer, Notes> kafkaTemplate;

    @Autowired
    RemoteService service;


    @KafkaListener(topics = "user-data", groupId = "remote-group")
    public Boolean notice(RemUser user){
        RemResponseObj responseObj;

            responseObj = service.grabWeather(user);

        if (user != null && responseObj != null) {
            Notes note = service.notice(user, responseObj);
            ProducerRecord<Integer, Notes> record = new ProducerRecord<>(
                    "weather-data", note.hashCode(), note
            );
            CompletableFuture<SendResult<Integer, Notes>> future = kafkaTemplate.send(
                    record
            );
            return future.complete(new SendResult<>(record, null));
        }

        return false;
    }

    @GetMapping("/ping")
    public ResponseEntity<Boolean> ping(){
        return ResponseEntity.ok(true);
    }




}
