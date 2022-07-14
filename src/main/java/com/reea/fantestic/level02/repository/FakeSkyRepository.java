package com.reea.fantestic.level02.repository;

import com.reea.fantestic.level01.Cloud;
import com.reea.fantestic.level02.entity.Sky;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Do not worry about the absurdity of this class.
 * I just did not want to connect to a DB ;)
 */
@Repository
@Slf4j
public class FakeSkyRepository {

    public Optional<Sky> findByName(String name) {
        log.info("Simulated finding a sky...");
        return Optional.of(getFakeSky());
    }

    public Optional<Sky> findByNameAndCloud(String name, Cloud cloud) {
        log.info("Simulating finding a sky by name and cloud...");
        return Optional.of(getFakeSky());
    }

    public Optional<Sky> createSky(Sky sky) {
        log.info("Simulated creating a sky...");
        return Optional.of(sky);
    }

    public Optional<Sky> updateSky(Sky sky) {
        log.info("Simulated updating a sky...");
        return Optional.of(sky);
    }

    public void deleteByName(String name) {
        log.info("Simulated deleting a sky...");
    }

    private Sky getFakeSky() {
        Sky sky = new Sky();
        sky.setClouds(new ArrayList<>());
        for (int i = 0; i < 3; i++) {
            Cloud cloud = new Cloud((i + 100) * i);
            sky.getClouds().add(cloud);
        }
        sky.setName("Fake Sky");
        sky.setDaylightDuration(Duration.ofHours(14));
        return sky;
    }
}
