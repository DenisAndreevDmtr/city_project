package com.andersen.cities.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.andersen.cities.entity.Role;
import com.andersen.cities.entity.User;
import com.andersen.cities.service.UserService;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserService userService;

    @Test
    void shouldReturnCustomUserDetails_whenLoadUserByUsername_givenExistingUser() throws UsernameNotFoundException {
        Optional<User> user = Optional.of(generateUser());
        when(userService.findUserByLogin(any())).thenReturn(user);

        CustomUserDetails actual = customUserDetailsService.loadUserByUsername("username");
        List<? extends GrantedAuthority> authorities = actual.getAuthorities().stream().toList();
        assertEquals(1, authorities.size());
        assertEquals("password", actual.getPassword());
        assertEquals("login", actual.getUsername());
        assertEquals("name", authorities.get(0).getAuthority());
        verify(userService).findUserByLogin("username");
    }

    @Test
    public void shouldReturnNull_whenLoadUserByUsername_givenNonExistingUser() {
        when(userService.findUserByLogin(any())).thenReturn(Optional.empty());

        CustomUserDetails actual = customUserDetailsService.loadUserByUsername("username");

        assertNull(actual);
    }

    private User generateUser() {
        Role role = Role.builder().id(1).name("name").build();
        return User.builder().id(2).login("login").password("password").role(role).build();
    }
}