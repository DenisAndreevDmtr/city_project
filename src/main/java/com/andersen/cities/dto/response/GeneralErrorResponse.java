package com.andersen.cities.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralErrorResponse {

    @Schema(example = "2022-04-16T23:07:59.441143700Z")
    private Instant dateTime;

    @Schema(example = "Operation completed unsuccessfully")
    private String message;
}