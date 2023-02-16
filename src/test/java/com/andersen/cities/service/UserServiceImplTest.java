package com.andersen.cities.service;

import com.andersen.cities.dto.request.LoginAndPasswordDto;
import com.andersen.cities.entity.Role;
import com.andersen.cities.entity.User;
import com.andersen.cities.exception.forbidden.IncorrectCredentialsException;
import com.andersen.cities.repository.UserRepository;
import com.andersen.cities.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void shouldReturnUser_whenFindUserByLogin_givenLogin() {
        String login = "login";
        Optional<User> expected = Optional.of(generateUser());
        when(userRepository.findUserByLogin(any())).thenReturn(expected);

        Optional<User> actual = userService.findUserByLogin(login);

        verify(userRepository).findUserByLogin(login);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowIncorrectCredential_whenValidateUser_givenInvalidPassword() {
        LoginAndPasswordDto loginAndPassword = generateLoginAndPasswordDto();
        Optional<User> user = Optional.of(generateUser());
        when(userRepository.findUserByLogin(any())).thenReturn(user);

        assertThrows(IncorrectCredentialsException.class, () -> userService.validateUser(loginAndPassword));
    }

    @Test
    public void shouldThrowIncorrectCredential_whenValidateUser_givenInvalidLogin() {
        LoginAndPasswordDto loginAndPassword = generateLoginAndPasswordDto();
        when(userRepository.findUserByLogin(any())).thenReturn(Optional.empty());

        assertThrows(IncorrectCredentialsException.class, () -> userService.validateUser(loginAndPassword));
    }

    private User generateUser() {
        Role role = Role.builder().id(1).name("name").build();
        return User.builder().id(1).login("login").password("password").role(role).build();
    }

    private LoginAndPasswordDto generateLoginAndPasswordDto() {
        return LoginAndPasswordDto.builder().login("login").password("password").build();
    }
}