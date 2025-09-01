package org.iwanttovisit.iwanttovisit.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(
        name = "AuthController",
        description = "API for authentication"
)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    @Operation(summary = "Login to account and get pair of tokens")
    public AuthResponse login(
            @RequestBody
            @Validated
            final AuthRequest request
    ) {
        return authService.login(request);
    }

    @PostMapping("/register")
    @Operation(summary = "Create new account")
    public void register(
            @RequestBody
            @Validated(OnCreate.class)
            final UserDto dto
    ) {
        User user = userMapper.toEntity(dto);
        userService.create(user);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh pair of tokens by refresh token")
    public AuthResponse refresh(
            @RequestBody
            @Validated
            final RefreshRequestDto dto
    ) {
        return authService.refresh(dto);
    }

    @PostMapping("/activate")
    @Operation(summary = "Activate account")
    public void activate(
            @RequestBody
            final String token
    ) {
        userService.activate(token);
    }

    @PostMapping("/forget")
    @Operation(summary = "Send email with reset url")
    public void sendResetEmail(
            @RequestBody
            final String email
    ) {
        authService.sendResetEmail(email);
    }

    @PostMapping("/password/restore")
    @Operation(summary = "Set new password")
    public void restorePassword(
            @RequestBody
            @Validated(OnCreate.class)
            final PasswordResetDto dto
    ) {
        authService.resetPassword(dto);
    }

}
