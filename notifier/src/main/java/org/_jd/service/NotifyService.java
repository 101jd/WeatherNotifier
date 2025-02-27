package org._jd.service;

import jakarta.annotation.PostConstruct;
import org._jd.domain.Note;
import org._jd.domain.NotRemResponse;
import org._jd.domain.NotUser;
import org._jd.repo.NotUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
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
    private EmailExceptionHandler gateway;

    @Autowired
    Logger logger;

    private String REMOTE = "http://127.0.0.1:8087/rem";
    private String GRAB = "/grab";
    private String NOTICE = "/notify";
    private String PING = "/ping";

    public List<NotUser> getAllUsers(){
        return repo.getAllUsers();
    }

    public Boolean addUser(NotUser user){
        logger.info("New user: " + user);
        return repo.addUser(user);
    }

    public NotRemResponse grab(String city) {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return template.exchange(REMOTE + GRAB + "?city=" + city, HttpMethod.GET, entity, NotRemResponse.class)
                .getBody();

    }


    public Note notice(NotUser user) {

        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NotUser> entity = new HttpEntity<>(user, headers);

        return template.exchange(REMOTE + NOTICE, HttpMethod.POST, entity, Note.class).getBody();

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
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    public void start(){

        if (remoteIsUp()) {
            logger.info("Sending messages...");
            if (!getAllUsers().isEmpty())
                for (NotUser user : getAllUsers()) {
                    Note note = notice(user);
                    if (!note.getNote().equals("ERROR") && !note.getNote().contains("no incidents"))
                        gateway.sendEmail("info@localhost", user.getEmail(), "WEATHER", note.getNote());
                }
        } else logger.warning("REMOTE SERVICE IS DOWN");
    }

}
