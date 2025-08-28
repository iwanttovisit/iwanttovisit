package org.iwanttovisit.iwanttovisit.service.impl;

import io.github.ilyalisov.jwt.config.TokenParameters;
import io.github.ilyalisov.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.iwanttovisit.iwanttovisit.model.User;
import org.iwanttovisit.iwanttovisit.model.exception.TokenNotValidException;
import org.iwanttovisit.iwanttovisit.service.AuthService;
import org.iwanttovisit.iwanttovisit.service.UserService;
import org.iwanttovisit.iwanttovisit.web.dto.AuthRequest;
import org.iwanttovisit.iwanttovisit.web.dto.AuthResponse;
import org.iwanttovisit.iwanttovisit.web.dto.PasswordResetDto;
import org.iwanttovisit.iwanttovisit.web.dto.RefreshRequestDto;
import org.iwanttovisit.iwanttovisit.web.security.JwtProperties;
import org.iwanttovisit.iwanttovisit.web.security.TokenType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService jwtService;
    private final JwtProperties jwtProperties;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse login(
            final AuthRequest request
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userService.getByUsername(request.getUsername());
        userService.updateLastSeen(request.getUsername());
        return generateTokens(user);
    }

    private AuthResponse generateTokens(
            final User user
    ) {
        AuthResponse response = new AuthResponse();
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setToken(
                jwtService.create(
                        TokenParameters.builder(
                                        user.getUsername(),
                                        TokenType.ACCESS.name(),
                                        jwtProperties.getAccess()
                                )
                                .claim(
                                        "userId",
                                        user.getId()
                                )
                                .build()
                )
        );
        response.setRefreshToken(
                jwtService.create(
                        TokenParameters.builder(
                                        user.getUsername(),
                                        TokenType.REFRESH.name(),
                                        jwtProperties.getRefresh()
                                )
                                .claim(
                                        "userId",
                                        user.getId()
                                )
                                .build()
                )
        );
        return response;
    }

    @Override
    @Transactional
    public AuthResponse refresh(
            final RefreshRequestDto request
    ) {
        if (request.getRefreshToken() == null
                || request.getRefreshToken().isEmpty()) {
            throw new TokenNotValidException("Token is missing.");
        }
        if (jwtService.isExpired(request.getRefreshToken())) {
            throw new TokenNotValidException("Token is expired.");
        }
        if (!jwtService.getType(request.getRefreshToken())
                .equals(TokenType.REFRESH.name())) {
            throw new TokenNotValidException("Token is incorrect.");
        }
        UUID id = UUID.fromString(
                (String) jwtService.claim(
                        request.getRefreshToken(),
                        "userId"
                )
        );
        User user = userService.getById(id);
        userService.updateLastSeen(user.getUsername());
        return generateTokens(user);
    }

    @Override
    public void sendResetEmail(
            final String email
    ) {
        //TODO implement after email is added
    }

    @Override
    @Transactional
    public void resetPassword(
            final PasswordResetDto dto
    ) {
        if (jwtService.isExpired(dto.getToken())) {
            throw new TokenNotValidException("Токен устарел.");
        }
        if (!jwtService.getType(dto.getToken())
                .equals(TokenType.PASSWORD_RESET.name())) {
            throw new TokenNotValidException("Неверный тип токена.");
        }
        UUID userId = UUID.fromString(
                (String) jwtService.claim(dto.getToken(), "userId")
        );
        User user = new User(userId);
        user.setPassword(
                passwordEncoder.encode(dto.getNewPassword())
        );
        userService.update(user);
    }

}
