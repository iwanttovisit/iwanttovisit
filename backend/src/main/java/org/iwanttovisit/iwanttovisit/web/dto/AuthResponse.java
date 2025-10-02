package org.iwanttovisit.iwanttovisit.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Authentication response after login or token refreshing")
public class AuthResponse {

    @Schema(description = "Id of logged in user")
    private UUID userId;

    @Schema(description = "Username of logged in user")
    private String username;

    @Schema(description = "Access token of logged in user")
    private String token;

    @Schema(description = "Refresh token of logged in user")
    private String refreshToken;

}
