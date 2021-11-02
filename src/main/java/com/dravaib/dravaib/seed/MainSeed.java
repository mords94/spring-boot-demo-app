package com.dravaib.dravaib.seed;

import java.util.ArrayList;
import java.util.List;

import com.dravaib.dravaib.model.Country;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainSeed {
    Logger logger = LoggerFactory.logger(MainSeed.class);

    private List<Seed> seeds = new ArrayList<Seed>();

    private void registerSeed(Seed seed) {
        this.seeds.add(seed);
    }

    @Autowired
    public MainSeed(RoleSeed roleSeed, UserSeed userSeed, CountrySeed countrySeed) {
        registerSeed(roleSeed);
        registerSeed(countrySeed);
        registerSeed(userSeed);
    }

    public void run() {
        logger.trace("SEEDING STARTED");
        this.seeds.forEach(seed -> seed.run());
        logger.trace("SEEDING FINISHED");
    }
}
