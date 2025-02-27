package org._jd.repo;

import org._jd.domain.NotifyParameter;
import org._jd.domain.NotUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class NotUserRepo {

    @Autowired
    JdbcTemplate template;

    public List<NotUser> getAllUsers() {
        RowMapper<NotUser> mapper = ((rs, rowNum) -> {
           NotUser user = new NotUser();

           user.setId(UUID.fromString(rs.getString("id")));
           user.setCity(rs.getString("city"));
           user.setEmail(rs.getString("email"));
           user.setParameter(NotifyParameter.valueOf(rs.getString("parameter")));

           return user;
        });

        return template.query("SELECT * FROM users", mapper);
    }

    public Boolean addUser(NotUser user) {
        try {
            template.update("INSERT INTO users VALUES (?, ?, ?, ?)",
                    user.getId(), user.getEmail(), user.getParameter().get(), user.getCity());
            return true;
        }catch (DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }
}
