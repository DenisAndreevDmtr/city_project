package com.andersen.cities.service;

import com.andersen.cities.dto.request.LoginAndPasswordDto;
import com.andersen.cities.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByLogin(String login);

    User validateUser(LoginAndPasswordDto loginAndPasswordDto);
}