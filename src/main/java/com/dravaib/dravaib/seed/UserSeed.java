package com.dravaib.dravaib.seed;

import com.dravaib.dravaib.factory.user.AdminUserFactory;
import com.dravaib.dravaib.factory.user.GuestUserFactory;
import com.dravaib.dravaib.factory.user.OwnerUserFactory;
import com.dravaib.dravaib.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSeed implements Seed {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AdminUserFactory adminFactory;

    @Autowired
    GuestUserFactory guestFactory;

    @Autowired
    OwnerUserFactory ownerFactory;

    private void addAdmins() {
        userRepository.save(adminFactory.makeUser(1));
    }

    private void addGuests() {
        for (int i = 0; i < 10; i++) {
            userRepository.save(guestFactory.makeUser(i + 1));
        }
    }

    private void addOwners() {
        for (int i = 0; i < 10; i++) {
            userRepository.save(ownerFactory.makeUser(i + 1));
        }
    }

    @Override
    public void run() {
        if (userRepository.count() == 0) {
            addAdmins();
            addGuests();
            addOwners();
        }
    }

}
