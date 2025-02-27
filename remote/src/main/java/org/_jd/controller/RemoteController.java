package org._jd.controller;

import org._jd.domain.requestsobj.RemUser;
import org._jd.domain.weatherobj.RemResponseObj;

import org._jd.domain.weatherobj.inter.Notes;
import org._jd.service.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rem")
public class RemoteController {

    @Autowired
    RemoteService service;

    @GetMapping("/grab")
    public ResponseEntity<RemResponseObj> grabWeather(@org.springframework.web.bind.annotation.RequestParam("city") String city){
        return ResponseEntity.ok(service.grabWeather(city));
    }

    @PostMapping("/notify")
    public ResponseEntity<Notes> notice(@RequestBody RemUser user){
        return ResponseEntity.ok(service.notice(user));
    }

    @GetMapping("/ping")
    public ResponseEntity<Boolean> ping(){
        return ResponseEntity.ok(true);
    }

}
