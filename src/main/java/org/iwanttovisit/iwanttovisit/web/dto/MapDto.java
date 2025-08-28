package org.iwanttovisit.iwanttovisit.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.iwanttovisit.iwanttovisit.model.Constants;

@Getter
@Setter
@ToString(callSuper = true)
public class MapDto extends BaseEntityDto {

    @NotNull(
            message = "Name can't be null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Length(
            max = Constants.FIELD_SIZE_SMALL,
            message = "Length must be less than {max} symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String name;

    @Length(
            max = Constants.FIELD_SIZE_BIG,
            message = "Length must be less than {max} symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto author;

    @NotNull(
            message = "Publicity can't be null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private boolean isPublic;

}
