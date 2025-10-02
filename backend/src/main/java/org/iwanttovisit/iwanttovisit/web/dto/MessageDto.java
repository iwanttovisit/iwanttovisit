package org.iwanttovisit.iwanttovisit.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Schema(description = "Error message object")
public class MessageDto {

    @Schema(description = "Message of the error")
    private String message;

    @Schema(description = "Map of errors in 'field-error' format")
    private Map<String, String> errors;

    public MessageDto(
            final String message
    ) {
        this.message = message;
        this.errors = new HashMap<>();
    }

}
