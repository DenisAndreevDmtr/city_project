package com.andersen.cities.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.andersen.cities.entity.Role;
import com.andersen.cities.entity.User;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

class CustomUserDetailsTest {

    @Test
    void shouldReturnCustomUserDetails_whenFromUserEntityToCustomUserDetails_givenUser() {
        User user = generateUser();

        CustomUserDetails actual = CustomUserDetails.fromUserEntityToCustomUserDetails(user);
        List<? extends GrantedAuthority> actualAuthorities = actual.getAuthorities().stream().toList();

        assertEquals(1, actualAuthorities.size());
        assertEquals("password", actual.getPassword());
        assertEquals("login", actual.getUsername());
        assertEquals("name", actualAuthorities.get(0).getAuthority());
    }

    private User generateUser() {
        Role role = Role.builder().id(1).name("name").build();
        return User.builder().id(2).login("login").password("password").role(role).build();
    }
}