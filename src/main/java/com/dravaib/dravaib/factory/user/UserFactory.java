package com.dravaib.dravaib.factory.user;

import com.dravaib.dravaib.model.User;
import com.dravaib.dravaib.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class UserFactory {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    protected RoleRepository roleRepository;

    protected User makeBase() {
        var user = new User();

        user.setPassword(passwordEncoder.encode("Test@7890"));
        return user;
    }

    public abstract User makeUser(Integer index);
}
