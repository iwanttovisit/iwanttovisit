package org.iwanttovisit.iwanttovisit.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.iwanttovisit.iwanttovisit.model.Constants;

@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Map object")
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
    @Schema(description = "Name of the map")
    private String name;

    @Length(
            max = Constants.FIELD_SIZE_BIG,
            message = "Length must be less than {max} symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Schema(description = "Description of the map")
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Author of the map")
    private UserDto author;

    @NotNull(
            message = "Publicity can't be null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Schema(description = "Publicity flag of the map")
    private boolean isPublic;

}
