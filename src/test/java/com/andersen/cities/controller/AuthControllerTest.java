package com.andersen.cities.controller;

import com.andersen.cities.dto.request.LoginAndPasswordDto;
import com.andersen.cities.entity.Role;
import com.andersen.cities.entity.User;
import com.andersen.cities.exception.forbidden.IncorrectCredentialsException;
import com.andersen.cities.security.JwtProvider;
import com.andersen.cities.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AuthController.class})
@ExtendWith(SpringExtension.class)
class AuthControllerTest {

    private static final String AUTHORIZATION_URL = "/api/v1/authorization";

    @Autowired
    private AuthController authController;
    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private UserService userService;

    @Test
    void shouldReturnSuccessResponse_whenLogin_givenCorrectCredentials() throws Exception {
        LoginAndPasswordDto loginAndPasswordDto = LoginAndPasswordDto.builder().login("login").password("password").build();
        String json = convertRequestDtoToJson(loginAndPasswordDto);

        when(userService.validateUser(any())).thenReturn(generateUser());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(AUTHORIZATION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void shouldReturnForbidden_whenLogin_givenInvalidCredentials() throws Exception {
        LoginAndPasswordDto loginAndPasswordDto = LoginAndPasswordDto.builder().login("login").password("password").build();
        String json = convertRequestDtoToJson(loginAndPasswordDto);

        doThrow(new IncorrectCredentialsException()).when(userService).validateUser(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(AUTHORIZATION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    private User generateUser() {
        Role role = Role.builder().id(1).name("name").build();
        return User.builder().id(2).login("login").password("password").role(role).build();
    }

    private <T> String convertRequestDtoToJson(T requestDto) {
        Gson gson = new Gson();
        return gson.toJson(requestDto);
    }
}