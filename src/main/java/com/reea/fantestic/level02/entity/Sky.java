package com.reea.fantestic.level02.entity;

import com.reea.fantestic.level01.Cloud;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sky {

    private String name;
    private Duration daylightDuration;
    private  List<Cloud> clouds;
}
