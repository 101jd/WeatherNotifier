package org._jd.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import org._jd.domain.NotUser;
import org._jd.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/not")
public class NotifyController {

    @Autowired
    NotifyService service;

    @KafkaListener(topics = "reg-data", groupId = "all-data-group", containerFactory = "userKafkaListenerContainerFactory")
    public Boolean listenUser(NotUser payload) throws JsonProcessingException {
        return service.addUser(service.listenUser(payload));

    }

    @GetMapping("/ping")
    ResponseEntity<Boolean> ping(){
        return ResponseEntity.ok(true);
    }
}
