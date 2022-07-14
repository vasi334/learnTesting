package com.reea.fantestic.level02.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

@JsonDeserialize
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkyDTO {

    private String name;
    private Long daylightDuration;
    private List<CloudDTO> clouds;
}
