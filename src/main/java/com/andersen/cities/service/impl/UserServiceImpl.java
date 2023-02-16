package com.andersen.cities.service.impl;

import com.andersen.cities.dto.request.LoginAndPasswordDto;
import com.andersen.cities.entity.User;
import com.andersen.cities.exception.forbidden.IncorrectCredentialsException;
import com.andersen.cities.repository.UserRepository;
import com.andersen.cities.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Override
    public User validateUser(LoginAndPasswordDto loginAndPasswordDto) {
        Optional<User> optionalUser = userRepository.findUserByLogin(loginAndPasswordDto.getLogin());
        if (optionalUser.isEmpty()) {
            throw new IncorrectCredentialsException();
        }
        User user = optionalUser.get();
        boolean validPassword = new BCryptPasswordEncoder().matches(loginAndPasswordDto.getPassword(), user.getPassword());
        if (!validPassword) {
            throw new IncorrectCredentialsException();
        }
        return user;
    }
}