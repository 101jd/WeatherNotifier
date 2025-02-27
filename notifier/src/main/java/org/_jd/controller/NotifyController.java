package org._jd.controller;


import org._jd.domain.NotUser;
import org._jd.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/not")
public class NotifyController {

    @Autowired
    NotifyService service;

    @PostMapping("/add")
    public ResponseEntity<Boolean> addUser(@RequestBody NotUser user){
        return ResponseEntity.ok(service.addUser(user));
    }

    @GetMapping("/ping")
    ResponseEntity<Boolean> ping(){
        return ResponseEntity.ok(true);
    }
}
