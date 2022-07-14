package com.reea.fantestic.level02.utils;

import com.reea.fantestic.level01.Cloud;
import com.reea.fantestic.level02.dto.CloudDTO;
import org.springframework.stereotype.Component;

@Component
public class CloudMapper {

    public static Cloud from(CloudDTO cloudDTO) {
        return Cloud.builder()
                .altitude(cloudDTO.getAltitude())
                .build();
    }

    public static CloudDTO from(Cloud cloud) {
        return CloudDTO.builder()
                .altitude(cloud.getAltitude())
                .build();
    }
}
