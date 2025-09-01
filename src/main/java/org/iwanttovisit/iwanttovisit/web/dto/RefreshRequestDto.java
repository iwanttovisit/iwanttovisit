package org.iwanttovisit.iwanttovisit.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Object for tokens refreshing")
public class RefreshRequestDto {

    @NotEmpty(message = "Token is required.")
    @Schema(description = "Valid refresh token")
    private String refreshToken;

}
