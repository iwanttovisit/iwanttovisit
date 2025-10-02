package org.iwanttovisit.iwanttovisit.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.iwanttovisit.iwanttovisit.model.Status;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Base DTO for all entities")
public class BaseEntityDto {

    @NotNull(
            message = "Идентификатор не должен быть пустым.",
            groups = {OnUpdate.class, NestedObjectId.class}
    )
    @Null(
            message = "Идентификатор должен быть пустым.",
            groups = OnCreate.class
    )
    @Schema(description = "Entity id")
    protected UUID id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Entity status")
    private Status status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Created timestamp")
    protected LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Updated timestamp")
    protected LocalDateTime updated;

}
