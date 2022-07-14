package com.reea.fantestic.level02.service;

import com.reea.fantestic.level01.Cloud;
import com.reea.fantestic.level01.CloudWentDownException;
import com.reea.fantestic.level02.entity.Sky;
import com.reea.fantestic.level02.exception.SkyNotFoundException;
import com.reea.fantestic.level02.repository.FakeSkyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SkyService {

    public static final String SKY_NOT_FOUND = "Sky not found!";
    public static final int INITIAL_MOVEMENT = 100;
    public static final int UPDATE_SKY_MOVEMENT = 10;
    private final FakeSkyRepository fakeSkyRepository;

    public Sky findByName(String name) throws SkyNotFoundException {
        // this is for the sake of having an if, do not do such useless if statements in your code
        if (!name.equals(name.toLowerCase())) {
            log.info("The sky name <<{}>> is invalid. Converted the wanted sky name to lowercase.", name);
            name = name.toLowerCase();
        }
        log.info("Searching for a sky.");
        return fakeSkyRepository
                .findByName(name)
                .orElseThrow(() -> new SkyNotFoundException(SKY_NOT_FOUND)
                );
    }

    public Sky findByNameAndCloud(String name, Cloud cloud) throws SkyNotFoundException {
        log.info("Searching for a sky by name and cloud.");
        return fakeSkyRepository
                .findByNameAndCloud(name.toLowerCase(), cloud)
                .orElseThrow(
                        () -> new SkyNotFoundException(SKY_NOT_FOUND)
                );
    }

    public Sky createSky(Sky sky) throws SkyNotFoundException {
        sky.setName(sky.getName().toLowerCase());
        sky.setClouds(sky.getClouds()
                .stream()
                .peek((cloud) -> cloud.floatUp(INITIAL_MOVEMENT))
                .toList());
        log.info("Creating a new sky.");
        return fakeSkyRepository
                .createSky(sky)
                .orElseThrow(
                        () -> new SkyNotFoundException(SKY_NOT_FOUND)
                );
    }

    public Sky updateSky(Sky sky) throws SkyNotFoundException {
        sky.setName(sky.getName().toLowerCase());
        sky.setClouds(sky.getClouds()
                .stream()
                .filter((cloud) -> {
                    try {
                        cloud.floatDown(UPDATE_SKY_MOVEMENT);
                        return true;
                    } catch (CloudWentDownException e) {
                        log.warn("One of the clouds was destroyed");
                        return false;
                    }
                }).toList());
        log.info("Updating a sky.");
        return fakeSkyRepository
                .updateSky(sky)
                .orElseThrow(
                        () -> new SkyNotFoundException(SKY_NOT_FOUND)
                );
    }

    public void deleteByName(String name) {
        log.info("Deleting a sky by name");
        fakeSkyRepository.deleteByName(name.toLowerCase());
    }


}
