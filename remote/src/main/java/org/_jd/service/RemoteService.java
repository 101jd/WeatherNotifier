package org._jd.service;

import org._jd.domain.notes.*;
import org._jd.domain.requestsobj.RemUser;
import org._jd.domain.weatherobj.DTO.NoteDTO;
import org._jd.domain.weatherobj.RemResponseObj;
import org._jd.domain.weatherobj.inter.Notes;
import org._jd.repo.RemoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

@Service
public class RemoteService {

    private final KeyProperties keyProperties = new KeyProperties();

    private final String filename = "access-key.properties";

    private final String secret = keyProperties.getKey(filename, "secret");


    private final String remote = "http://api.weatherstack.com/current" +
            "?access_key=" + secret +
            "&query=";


    @Autowired
    private HttpHeaders headers;

    @Autowired
    private RestTemplate template;

    @Autowired
    RemoteRepo repo;

    @Autowired
    Logger logger;


    public RemResponseObj grabWeather(String city) {

        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            RemResponseObj response = template.exchange(remote + city, HttpMethod.GET, entity, RemResponseObj.class).getBody();
            logger.info("New weather object for " + response.getLocation().getName() + " recieved");


            if (repo.getMinMax(response.getLocation().getName()) == null) {
                String location = response.getLocation().getName();
                Integer temp = response.getCurrent().getTemperature();
                repo.InitMinmax(location, temp);
            } else {
                if (response.getCurrent().getTemperature() > repo.getMaxTemperature(response.getLocation().getName())) {
                    repo.refreshMax(response.getLocation().getName(), response.getCurrent().getTemperature());
                    logger.info("In " + response.getLocation().getName()
                            + " max temp = " + response.getCurrent().getTemperature());
                } else if (response.getCurrent().getTemperature()
                        < repo.getMinTemperature(response.getLocation().getName())) {
                    repo.refreshMin(response.getLocation().getName(), response.getCurrent().getTemperature());
                    logger.info("In " + response.getLocation().getName()
                            + " min temp = " + response.getCurrent().getTemperature());
                }
            }
            return response;
        }catch (RestClientException e){
            logger.warning(e.getMessage());
            return null;
        }

    }

    public Notes notice(RemUser user) {
        RemResponseObj response = grabWeather(user.getCity());
        return notice(user, response);
    }

    public Notes notice(RemUser user, RemResponseObj response) {
        Integer storm_index = 20;
        int tempdiff = 10;

        if (response.getRequest() != null) {
            System.out.println(response.getRequest());
                switch (user.getParameter()) {
                    case STORM -> {
                        if (response.getCurrent().getWind_speed() > storm_index) {
                            return new StormNote(
                                    response.getCurrent().getWind_speed(), response.getLocation().getName()
                            );

                        }
                    }
                    case TEMPDIFF -> {
                        int diff = mod(response.getCurrent().getTemperature()
                                - repo.getTemperature(response.getLocation().getName()));
                        if (diff >= tempdiff) {
                            return new TempdiffNote(
                                    diff, response.getLocation().getName(),
                                    response.getCurrent().getTemperature());
                        }

                    }
                    case MINMAXTEMP -> {
                        int current = response.getCurrent().getTemperature();
                        int max = repo.getMaxTemperature(response.getLocation().getName());
                        int min = repo.getMinTemperature(response.getLocation().getName());
                        int diff;
                        if (current > max) {
                            diff = mod(current - max);
                            return new MinmaxNote(diff, "more", response.getLocation().getName(), "MAX");
                        } else if (current < min) {
                            diff = mod(current - min);
                            return new MinmaxNote(diff, "less", response.getLocation().getName(), "MIN");
                        }
                    }
                }
            return new NoneNote(response.getLocation().getName());
        } else return new NoteDTO();

    }

    private int mod(int val){
        return val > 0 ? val : val * -1;
    }
}
