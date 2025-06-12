package org._jd;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import src.main.java.org.jd._jd.Remote;
import src.main.java.org.jd._jd.domain.requestsobj.NotifyParameter;
import src.main.java.org.jd._jd.domain.requestsobj.RemUser;
import src.main.java.org.jd._jd.domain.weatherobj.DTO.WeatherError;
import src.main.java.org.jd._jd.domain.weatherobj.RemResponseObj;
import src.main.java.org.jd._jd.domain.weatherobj.inter.Notes;
import src.main.java.org.jd._jd.service.KeyProperties;
import src.main.java.org.jd._jd.service.RemoteService;


import java.util.List;
import java.util.UUID;


import static org.junit.Assert.*;
import static src.main.java.org.jd._jd.domain.requestsobj.NotifyParameter.STORM;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Remote.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({WeatherError.class, RemResponseObj.class, NotifyParameter.class, RemUser.class})
public class RemServiceTest {


    @MockitoBean
    KeyProperties properties;

    @InjectMocks
    RemoteService service;

    @Test
    public void testGrabFail(){
        String secret = "0";
        String remote = "https://api.weatherstack.com/current" +
                "?access_key=" + secret +
                "&query=";

        TestRestTemplate template = new TestRestTemplate();

        HttpHeaders headers = new HttpHeaders();

        RemUser user = new RemUser();
        user.setId(UUID.randomUUID());
        user.setCity("");
        user.setEmail("test@test.net");
        user.setParameter(STORM);

        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<RemResponseObj> response = template.exchange(remote, HttpMethod.GET, entity,
                RemResponseObj.class);

        assertFalse(response.getBody().isSuccess());

    }

    @Test
    public void testFailNote(){
        String secret = "0";
        String remote = "https://api.weatherstack.com/current" +
                "?access_key=" + secret +
                "&query=";

        RemUser user = new RemUser();
        user.setId(UUID.randomUUID());
        user.setCity("");
        user.setEmail("test@test.net");
        user.setParameter(STORM);

        TestRestTemplate template = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RemResponseObj response = template.exchange(remote, HttpMethod.GET, entity, RemResponseObj.class)
                .getBody();

        Notes note = service.notice(user, response);

        assertEquals(note.getNote(), "Can't get response from server");
    }

}

