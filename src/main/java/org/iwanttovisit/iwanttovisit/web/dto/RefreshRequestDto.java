package org.iwanttovisit.iwanttovisit.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class RefreshRequestDto {

    @NotEmpty(message = "Token is required.")
    private String refreshToken;

}
