package org._jd.service;

import org._jd.domain.CheckKeys;
import org._jd.domain.RegUser;
import org._jd.repo.CheckRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class RegService {
    @Autowired
    private HttpHeaders headers;

    @Autowired
    private RestTemplate template;

    @Autowired
    CheckRepo repo;

    @Autowired
    Logger logger;

    private String NOTICE = "http://127.0.0.1:8087/not";
    private String ADD = "/add";
    private String PING = "/ping";


    public Boolean addUser(RegUser user){
        if (noticeIsUp()) {
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<RegUser> entity = new HttpEntity<>(user, headers);

            return template.exchange(NOTICE + ADD, HttpMethod.POST, entity, Boolean.class).getBody();
        } else {
            logger.warning("NOTICE IS DOWN");
            return noticeIsUp();
        }
    }

    public boolean checkUser(String email, UUID uuid) {
        if (repo.getCheckKeys(email).getUuid().equals(uuid)) {
            logger.info(email + " accepted");
            return true;
        }
        return false;
    }

    public CheckKeys addKey(String email){
        return repo.addKey(email);
    }

    public CheckKeys getByEmail(String email){
        return repo.getCheckKeys(email);
    }

    public List<CheckKeys> getAllKeys(){
        return repo.getAllKeys();
    }

    private boolean noticeIsUp(){
        try {
            return template.exchange(NOTICE + PING, HttpMethod.GET, null, Boolean.class).getBody();

        } catch (RestClientException e){
            return false;
        }
    }
}
