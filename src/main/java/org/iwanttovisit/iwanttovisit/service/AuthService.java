package org.iwanttovisit.iwanttovisit.service;

import org.iwanttovisit.iwanttovisit.web.dto.AuthRequest;
import org.iwanttovisit.iwanttovisit.web.dto.AuthResponse;
import org.iwanttovisit.iwanttovisit.web.dto.PasswordResetDto;
import org.iwanttovisit.iwanttovisit.web.dto.RefreshRequestDto;

public interface AuthService {

    AuthResponse login(
            AuthRequest request
    );

    AuthResponse refresh(
            RefreshRequestDto request
    );

    void sendResetEmail(
            String email
    );

    void resetPassword(
            PasswordResetDto dto
    );

}
