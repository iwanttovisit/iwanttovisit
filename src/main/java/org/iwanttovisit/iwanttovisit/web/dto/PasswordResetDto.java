package org.iwanttovisit.iwanttovisit.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Object for password resetting")
public class PasswordResetDto {

    @NotEmpty(
            message = "Token can't be null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Schema(description = "Token of `PasswordReset` type")
    private String token;

    @NotEmpty(
            message = "New password can't be null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Length(
            min = 4,
            message = "Length must be between {min} - {max} symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Schema(description = "New password for token owner")
    private String newPassword;

}
