package org.iwanttovisit.iwanttovisit.web.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.iwanttovisit.iwanttovisit.model.exception.ResourceAlreadyExistsException;
import org.iwanttovisit.iwanttovisit.model.exception.ResourceNotFoundException;
import org.iwanttovisit.iwanttovisit.model.exception.TokenNotValidException;
import org.iwanttovisit.iwanttovisit.web.dto.MessageDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public MessageDto handleAccessDeniedException() {
        return new MessageDto(
                "You have no permission to access this object."
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public MessageDto handleResourceAlreadyExistsException(
            final ResourceAlreadyExistsException e
    ) {
        return new MessageDto(
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public MessageDto handleResourceNotFoundException() {
        return new MessageDto(
                "Object is not found."
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BadCredentialsException.class,
            InternalAuthenticationServiceException.class
    })
    public MessageDto handleBadCredentialsException(
            final RuntimeException e
    ) {
        return new MessageDto(
                "Incorrect username or password."
        );
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ExpiredJwtException.class)
    public MessageDto handleException() {
        return new MessageDto(
                "Token is expired."
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public MessageDto handleIllegalArgumentException(
            final IllegalArgumentException e
    ) {
        return new MessageDto(
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public MessageDto handleIllegalStateException(
            final IllegalStateException e
    ) {
        return new MessageDto(
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TokenNotValidException.class)
    public MessageDto handleTokenNotValidException(
            final TokenNotValidException e
    ) {
        return new MessageDto(
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public MessageDto handleException(
            final Exception e
    ) {
        log.error(e.getMessage());
        return new MessageDto(
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public MessageDto handleConstraintViolationException(
            final ConstraintViolationException e
    ) {
        Map<String, String> errors = e.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        error -> {
                            String path = error.getPropertyPath().toString();
                            String[] parts = path.split("\\.");
                            return parts.length > 0
                                    ? parts[parts.length - 1]
                                    : path;
                        },
                        ConstraintViolation::getMessage,
                        (existingMessage, newMessage) ->
                                existingMessage
                ));
        return new MessageDto(
                "Data fields are incorrect.",
                errors
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public MessageDto handleException(
            final MethodArgumentNotValidException e
    ) {
        Map<String, String> errors = e.getBindingResult()
                .getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage,
                        (existingMessage, newMessage) ->
                                existingMessage + " " + newMessage
                ));
        return new MessageDto(
                "Data fields are incorrect.",
                errors
        );
    }

}
