package com.override.service;

import com.override.config.security.JwtProvider;
import com.override.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomStudentDetailService studentDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String login, String password) {
        UserDetails userDetails;
        try {
            userDetails = studentDetailService.loadUserByUsername(login);
        } catch (UsernameNotFoundException e) {
            log.warn("Пользователь с логином " + login + " не был найден!");
            throw new UsernameNotFoundException("Пользователь с логином " + login + " не найден!");
        }
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return jwtProvider.generateToken(login, userDetails.getAuthorities());
        }

        throw new AuthException("Данные пользователя неверны");
    }
}