package org.iwanttovisit.iwanttovisit.web.dto;

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
public class MessageDto {

    private String message;
    private Map<String, String> errors;

    public MessageDto(
            final String message
    ) {
        this.message = message;
        this.errors = new HashMap<>();
    }

}
