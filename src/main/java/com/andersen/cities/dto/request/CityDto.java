package com.andersen.cities.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CityDto {
    @NotNull
    @Schema(example = "1")
    private int id;
    @NotNull
    @Schema(example = "Change name")
    private String name;
    @NotNull
    @Schema(example = "Change photo path")
    private String photo;
}