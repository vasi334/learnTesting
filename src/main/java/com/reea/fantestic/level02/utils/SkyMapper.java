package com.reea.fantestic.level02.utils;

import com.reea.fantestic.level02.dto.SkyDTO;
import com.reea.fantestic.level02.entity.Sky;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class SkyMapper {

    public static final String WITH_THE_CLOUDS = " with the clouds";

    public static Sky from(SkyDTO skyDTO) {
        return Sky.builder()
                .clouds(skyDTO.getClouds().stream().map(CloudMapper::from).toList())
                .daylightDuration(Duration.ofHours(skyDTO.getDaylightDuration()))
                .name(skyDTO.getName() + WITH_THE_CLOUDS)
                .build();
    }

    public static SkyDTO from(Sky sky) {
        return SkyDTO.builder()
                .clouds(sky.getClouds().stream().map(CloudMapper::from).toList())
                .daylightDuration(sky.getDaylightDuration().toHours())
                .name(sky.getName().replace(WITH_THE_CLOUDS, ""))
                .build();
    }
}
