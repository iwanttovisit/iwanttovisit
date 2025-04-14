package org.iwanttovisit.iwanttovisit.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class AuthRequest {

    @NotEmpty(message = "Email can't be null.")
    @Email(message = "Email must be valid.")
    private String username;

    @NotEmpty(message = "Password can't be null.")
    private String password;

}
