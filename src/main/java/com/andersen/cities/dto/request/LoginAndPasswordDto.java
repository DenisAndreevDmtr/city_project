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
public class LoginAndPasswordDto {
    @NotNull
    @Schema(example = "admin")
    private String login;
    @NotNull
    @Schema(example = "admin")
    private String password;
}