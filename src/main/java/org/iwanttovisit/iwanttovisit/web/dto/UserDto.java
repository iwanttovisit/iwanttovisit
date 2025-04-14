package org.iwanttovisit.iwanttovisit.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.iwanttovisit.iwanttovisit.model.Constants;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class UserDto extends BaseEntityDto {

    @NotNull(
            message = "Email can't be null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Length(
            min = 4,
            max = Constants.FIELD_SIZE_SMALL,
            message = "Length must be between {min} - {max} symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String username;

    @Length(
            max = Constants.FIELD_SIZE_SMALL,
            message = "Max length - {max}",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String name;

    @NotNull(
            message = "Password can't be null.",
            groups = {OnCreate.class}
    )
    @Length(
            min = 4,
            max = Constants.FIELD_SIZE_SMALL,
            message = "Length must be between {min} - {max} symbols.",
            groups = {OnCreate.class}
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastSeen;

}
