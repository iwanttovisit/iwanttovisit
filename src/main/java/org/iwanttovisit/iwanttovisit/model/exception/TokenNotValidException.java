package org.iwanttovisit.iwanttovisit.model.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenNotValidException extends RuntimeException {

    public TokenNotValidException(
            final String message
    ) {
        super(message);
    }

}
