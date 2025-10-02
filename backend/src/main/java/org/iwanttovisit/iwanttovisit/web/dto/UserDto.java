package org.iwanttovisit.iwanttovisit.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Object of user")
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
    @Schema(description = "Username of the user")
    private String username;

    @Length(
            max = Constants.FIELD_SIZE_SMALL,
            message = "Max length - {max}",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Schema(description = "Name of the user")
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
    @Schema(description = "Password of the user")
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Last seen timestamp of the user")
    private LocalDateTime lastSeen;

}
