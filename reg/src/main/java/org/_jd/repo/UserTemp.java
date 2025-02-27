package org._jd.repo;

import org._jd.domain.RegUser;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserTemp {
    private final List<RegUser> users = new ArrayList<>();

    public Boolean addTempUser(RegUser user){
        RegUser u = new RegUser();
        u.setEmail(user.getEmail());
        u.setParameter(user.getParameter());
        u.setCity(user.getCity());
        return users.add(u);
    }

    public RegUser getUser(String email){
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst().get();
    }

    public void removeTempUser(RegUser user){
        users.remove(user);
    }
}
