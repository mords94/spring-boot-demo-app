package com.dravaib.dravaib.factory.user;

import com.dravaib.dravaib.model.RoleType;
import com.dravaib.dravaib.model.User;
import com.dravaib.dravaib.model.embed.PersonDetails;

import org.springframework.stereotype.Component;

@Component
public class AdminUserFactory extends UserFactory {
    public User makeUser(Integer index) {
        var user = makeBase();
        var personalDetails = new PersonDetails("Admin", "User", String.format("admin%s@example.com", index),
                "06301234567");
        user.setPersonDetails(personalDetails);

        user.setRole(roleRepository.findByRoleType(RoleType.ROLE_ADMIN).get());

        return user;
    }
}
