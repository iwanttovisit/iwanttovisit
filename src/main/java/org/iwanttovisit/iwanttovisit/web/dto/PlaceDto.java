package org.iwanttovisit.iwanttovisit.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.iwanttovisit.iwanttovisit.model.Category;
import org.iwanttovisit.iwanttovisit.model.Constants;
import org.iwanttovisit.iwanttovisit.model.Map;
import org.iwanttovisit.iwanttovisit.model.User;

@Getter
@Setter
@ToString(callSuper = true)
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
    private String name;

    @Length(
            max = Constants.FIELD_SIZE_BIG,
            message = "Length must be less than {max} symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
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
    private String coordinates;

    @Length(
            max = Constants.FIELD_SIZE_SMALL,
            message = "Length must be less than {max} symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String url;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private float rating;

    @NotNull(
            message = "Category can't be null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private Category category;

    @NotNull(
            message = "Visited mark can't be null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private boolean isVisited;

    @NotNull(
            message = "Map can't be null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Valid
    @ConvertGroup(from = OnCreate.class, to = NestedObjectId.class)
    @ConvertGroup(from = OnUpdate.class, to = NestedObjectId.class)
    private Map map;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private User author;

}
