package org.iwanttovisit.iwanttovisit.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
public class AuthResponse {

    private UUID userId;
    private String username;
    private String token;
    private String refreshToken;

}
