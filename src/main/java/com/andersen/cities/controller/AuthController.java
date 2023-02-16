package com.andersen.cities.controller;

import com.andersen.cities.dto.request.LoginAndPasswordDto;
import com.andersen.cities.dto.response.AuthResponse;
import com.andersen.cities.dto.response.GeneralErrorResponse;
import com.andersen.cities.entity.User;
import com.andersen.cities.security.JwtProvider;
import com.andersen.cities.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.andersen.cities.dto.response.ResponseMessageConstant.INCORRECT_CREDENTIALS;

@Tag(name = "authorization", description = "The authorization API")
@RestController
@RequestMapping("/api/v1/authorization")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Operation(summary = "Login and get jwt token")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully logged in system",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - forbidden access",
                    content = @Content(schema = @Schema(implementation = GeneralErrorResponse.class),
                            examples = @ExampleObject(INCORRECT_CREDENTIALS))
            )
    })
    @PostMapping
    public AuthResponse login(@RequestBody LoginAndPasswordDto loginAndPasswordDto) {
        User user = userService.validateUser(loginAndPasswordDto);
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse(token);
    }
}