package org.iwanttovisit.iwanttovisit.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString(callSuper = true)
public class PasswordResetDto {

    @NotEmpty(
            message = "Token can't be null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
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
    private String newPassword;

}
