package org._jd;

import org._jd.domain.NotUser;
import org._jd.domain.NotifyParameter;
import org._jd.repo.NotUserRepo;


import org.junit.Test;
import org.junit.runner.RunWith;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/schema.sql")
@Import(NotUserRepo.class)
public class NotifyRepoTests {

    @Autowired
    NotUserRepo repo;

    @Test
    @Commit
    public void testAddUser(){
        String email = "test@test.net";
        NotUser user = new NotUser();
        user.setId(UUID.randomUUID());
        user.setCity("Kyiv");
        user.setEmail(email);
        user.setParameter(NotifyParameter.STORM);

        boolean check = repo.addUser(user);

        assertTrue(check);

        assertEquals(repo.getAllUsers().stream().filter(u -> u.getEmail().equals(email)).findFirst().get(),
                user);
    }
}
