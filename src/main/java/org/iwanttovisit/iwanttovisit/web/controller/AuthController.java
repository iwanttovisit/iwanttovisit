package org.iwanttovisit.iwanttovisit.web.controller;

import lombok.RequiredArgsConstructor;
import org.iwanttovisit.iwanttovisit.model.User;
import org.iwanttovisit.iwanttovisit.service.AuthService;
import org.iwanttovisit.iwanttovisit.service.UserService;
import org.iwanttovisit.iwanttovisit.web.dto.AuthRequest;
import org.iwanttovisit.iwanttovisit.web.dto.AuthResponse;
import org.iwanttovisit.iwanttovisit.web.dto.OnCreate;
import org.iwanttovisit.iwanttovisit.web.dto.PasswordResetDto;
import org.iwanttovisit.iwanttovisit.web.dto.RefreshRequestDto;
import org.iwanttovisit.iwanttovisit.web.dto.UserDto;
import org.iwanttovisit.iwanttovisit.web.dto.mappers.UserMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody
            @Validated
            final AuthRequest request
    ) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public void register(
            @RequestBody
            @Validated(OnCreate.class)
            final UserDto dto
    ) {
        User user = userMapper.toEntity(dto);
        userService.create(user);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(
            @RequestBody
            @Validated
            final RefreshRequestDto dto
    ) {
        return authService.refresh(dto);
    }

    @PostMapping("/activate")
    public void activate(
            @RequestBody
            final String token
    ) {
        userService.activate(token);
    }

    @PostMapping("/forget")
    public void sendResetEmail(
            @RequestBody
            final String email
    ) {
        authService.sendResetEmail(email);
    }

    @PostMapping("/password/restore")
    @Transactional
    public void restorePassword(
            @RequestBody
            @Validated(OnCreate.class)
            final PasswordResetDto dto
    ) {
        authService.resetPassword(dto);
    }

}
