package org._jd;


import org._jd.domain.NotifyParameter;
import org._jd.domain.RegUser;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Registration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegServiceIntegrTest {

    @Test
    public void TestExchange(){
        TestRestTemplate template = new TestRestTemplate();

        RegUser user = new RegUser();

        user.setId(UUID.randomUUID());
        user.setEmail("test@test.net");
        user.setCity("DefaultCity");
        user.setParameter(NotifyParameter.TEMPDIFF);

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String NOT = "http://127.0.0.1:8087/not/add";

        HttpEntity<RegUser> entity = new HttpEntity<>(user, headers);

        ResponseEntity<Boolean> response = template.exchange(NOT, HttpMethod.POST, entity, Boolean.class);

        assertThat(response.getBody(), is(true));
    }

}
