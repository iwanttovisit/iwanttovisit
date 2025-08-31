package org.iwanttovisit.iwanttovisit.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Authentication request object for login")
public class AuthRequest {

    @NotEmpty(message = "Email can't be null.")
    @Email(message = "Email must be valid.")
    @Schema(description = "Username of account")
    private String username;

    @NotEmpty(message = "Password can't be null.")
    @Schema(description = "Password of account")
    private String password;

}
