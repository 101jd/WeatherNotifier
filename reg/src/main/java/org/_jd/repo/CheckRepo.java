package org._jd.repo;

import org._jd.domain.CheckKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CheckRepo {

    @Autowired
    private JdbcTemplate template;

    public CheckKeys addKey(String email){
        String query0 = "MERGE INTO keys KEY (email) VALUES (?, ?)";
        CheckKeys keys = new CheckKeys();
        keys.setEmail(email);
        keys.setUuid(UUID.randomUUID());
        template.update(query0, keys.getEmail(), keys.getUuid());
        return keys;
    }

    public CheckKeys getByEmail(String email){
        ResultSetExtractor<CheckKeys> extractor = rs -> {
            CheckKeys keys = new CheckKeys();

            keys.setEmail(rs.getString("email"));
            keys.setUuid(rs.getObject("uuid", UUID.class));

            return keys;
        };

        return template.query("SELECT * FROM keys WHERE email = ?", new Object[]{email}, extractor);
    }

    public List<CheckKeys> getAllKeys(){
        RowMapper<CheckKeys> mapper = (rs, rowNum) -> {
            CheckKeys keys = new CheckKeys();

            keys.setEmail(rs.getString("email"));
            keys.setUuid(UUID.fromString(rs.getString("uuid")));

            return keys;
        };

        return template.query("SELECT * FROM keys", mapper);
    }

    //костыль, но работает
    public CheckKeys getCheckKeys(String email){
        return getAllKeys().stream().filter(checkKeys -> checkKeys.getEmail().equals(email)).findFirst().get();
    }
}
