package org._jd.controller;

import org._jd.domain.CheckKeys;
import org._jd.domain.RegUser;
import org._jd.exceptions.EmailException;
import org._jd.repo.UserTemp;
import org._jd.service.MailGateway;
import org._jd.service.RegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("reg")
public class RegController {

    @Autowired
    RegService service;

    @Autowired
    UserTemp temp;

    @Autowired
    MailGateway gateway;

    @PostMapping("/")
    public ResponseEntity<RegUser> regiser(@RequestBody RegUser user){
        CheckKeys keys = service.addKey(user.getEmail());
        String message = "<a href=\"http://127.0.0.1:8087/reg/check?email="
                + keys.getEmail() + "&key=" + keys.getUuid() + "\">click!</a>";
        try {
            gateway.sendEmail("check@localhost", user.getEmail(), "VALIDATE", message);
            temp.addTempUser(user);
            return ResponseEntity.ok(temp.getUser(user.getEmail()));
        }catch (EmailException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkEmail(@RequestParam("email") String email,
                                              @RequestParam("key") UUID uuid){
        RegUser user = temp.getUser(email);
        ResponseEntity<Boolean> entity;
        if (service.checkUser(email, uuid))
            entity = ResponseEntity.ok(service.addUser(temp.getUser(email)));
        else entity = ResponseEntity.badRequest().build();
        temp.removeTempUser(user);
        return entity;
    }
}
