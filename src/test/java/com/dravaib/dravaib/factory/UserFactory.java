package com.dravaib.dravaib.factory;

import com.dravaib.dravaib.model.RoleType;
import com.dravaib.dravaib.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserFactory extends Factory<User> {
    @Autowired
    private PersonDetailsFactory personDetailsFactory;

    @Autowired
    private RoleFactory roleFactory;

    @Autowired
    private PasswordEncoder passwordEncode;

    public User make() {
        var user = new User();

        user.setPersonDetails(personDetailsFactory.make());
        user.setPassword(passwordEncode.encode("Test@7890"));

        return user;
    }

    public User make(RoleType type) {
        var user = make();
        user.setRole(roleFactory.make(RoleType.ROLE_ADMIN));

        return user;
    }

}
