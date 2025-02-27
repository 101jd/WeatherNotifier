package org._jd;

import org._jd.domain.NotUser;
import org._jd.domain.Note;
import org._jd.domain.NotifyParameter;
import org._jd.service.NotifyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static io.netty.channel.group.ChannelMatchers.isNot;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Notifier.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotifyNoticeTests {

    @Autowired
    NotifyService service;

    @Test
    public void testNotes(){
        NotUser user = new NotUser();
        user.setId(UUID.randomUUID());
        user.setCity("Moscow");
        user.setEmail("test@test.net");
        user.setParameter(NotifyParameter.STORM);

        Note note = service.notice(user);

        assertTrue(note != null);
    }
}
