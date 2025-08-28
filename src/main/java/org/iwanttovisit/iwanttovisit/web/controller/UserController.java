package org.iwanttovisit.iwanttovisit.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iwanttovisit.iwanttovisit.model.User;
import org.iwanttovisit.iwanttovisit.service.UserService;
import org.iwanttovisit.iwanttovisit.web.dto.OnUpdate;
import org.iwanttovisit.iwanttovisit.web.dto.UserDto;
import org.iwanttovisit.iwanttovisit.web.dto.mappers.UserMapper;
import org.iwanttovisit.iwanttovisit.web.security.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;
    private final UserMapper userMapper;

    @PutMapping
    @PreAuthorize("#ss.hasAccess(#dto.id, 'User')")
    public UserDto update(
            @Validated(OnUpdate.class)
            @RequestBody
            final UserDto dto
    ) {
        User user = userMapper.toEntity(dto);
        userService.update(user);
        log.warn(
                "User {} is updated by user {}.",
                dto.getId(),
                securityService.getUserIdFromRequest()
        );
        return userMapper.toDto(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#ss.hasAccess(#id, 'User')")
    public UserDto getById(
            @PathVariable
            final UUID id
    ) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#ss.hasAccess(#id, 'User')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable
            final UUID id
    ) {
        userService.delete(id);
        log.warn(
                "User {} is deleted by user {}.",
                id,
                securityService.getUserIdFromRequest()
        );
    }

}
