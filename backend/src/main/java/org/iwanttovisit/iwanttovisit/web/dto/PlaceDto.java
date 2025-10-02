package org.iwanttovisit.iwanttovisit.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.iwanttovisit.iwanttovisit.model.Category;
import org.iwanttovisit.iwanttovisit.model.Constants;

@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Object of place on map")
public class PlaceDto extends BaseEntityDto {

    @NotNull(
            message = "Name can't be null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Length(
            max = Constants.FIELD_SIZE_SMALL,
            message = "Length must be less than {max} symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Schema(description = "Name of the place")
    private String name;

    @Length(
            max = Constants.FIELD_SIZE_BIG,
            message = "Length must be less than {max} symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Schema(description = "Description of the place")
    private String description;

    @Length(
            max = 50,
            message = "Coordinates length must be less than {max} symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @NotNull(
            message = "Coordinates can't be null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Schema(description = "Coordinates of the place")
    private String coordinates;

    @Length(
            max = Constants.FIELD_SIZE_SMALL,
            message = "Length must be less than {max} symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Schema(description = "Social networks url")
    private String url;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Rating of the place")
    private float rating;

    @NotNull(
            message = "Category can't be null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Schema(description = "Category of the place")
    private Category category;

    @NotNull(
            message = "Visited mark can't be null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Schema(description = "Visited flag of the place")
    private boolean isVisited;

    @NotNull(
            message = "Map can't be null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Valid
    @ConvertGroup(from = OnCreate.class, to = NestedObjectId.class)
    @ConvertGroup(from = OnUpdate.class, to = NestedObjectId.class)
    @Schema(description = "Map of the place")
    private MapDto map;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Author of the place")
    private UserDto author;

}
