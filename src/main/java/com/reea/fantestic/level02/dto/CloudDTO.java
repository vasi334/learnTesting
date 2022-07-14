package com.reea.fantestic.level02.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonDeserialize
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloudDTO {

    private int altitude;
}
