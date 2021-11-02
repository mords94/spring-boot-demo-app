package com.dravaib.dravaib.seed;

import java.util.stream.Stream;

import com.dravaib.dravaib.model.Role;
import com.dravaib.dravaib.model.RoleType;
import com.dravaib.dravaib.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleSeed implements Seed {

    @Autowired
    private RoleRepository roleRepository;

    public void run() {
        if (roleRepository.count() == 0) {
            Stream.of(RoleType.values()).forEach(roleType -> roleRepository.save(new Role(roleType)));
        }
    }

}
