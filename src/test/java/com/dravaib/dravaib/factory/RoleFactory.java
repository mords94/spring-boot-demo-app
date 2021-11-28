package com.dravaib.dravaib.factory;

import com.dravaib.dravaib.model.Role;
import com.dravaib.dravaib.model.RoleType;

import org.springframework.stereotype.Component;

@Component
public class RoleFactory extends Factory<Role> {

    public Role make(RoleType type) {
        return new Role(type);
    }

    public Role make() {
        return new Role();
    }

}
