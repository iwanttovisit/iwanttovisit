package org.iwanttovisit.iwanttovisit.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iwanttovisit.iwanttovisit.model.Map;
import org.iwanttovisit.iwanttovisit.model.User;
import org.iwanttovisit.iwanttovisit.model.criteria.MapCriteria;
import org.iwanttovisit.iwanttovisit.service.MapService;
import org.iwanttovisit.iwanttovisit.service.UserService;
import org.iwanttovisit.iwanttovisit.web.dto.MapDto;
import org.iwanttovisit.iwanttovisit.web.dto.OnUpdate;
import org.iwanttovisit.iwanttovisit.web.dto.UserDto;
import org.iwanttovisit.iwanttovisit.web.dto.mappers.MapMapper;
import org.iwanttovisit.iwanttovisit.web.dto.mappers.UserMapper;
import org.iwanttovisit.iwanttovisit.web.security.service.SecurityService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(
        name = "UserController",
        description = "API for users"
)
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {

    private final UserService userService;
    private final MapService mapService;
    private final SecurityService securityService;
    private final UserMapper userMapper;
    private final MapMapper mapMapper;

    @PutMapping
    @Operation(summary = "Update user")
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
    @Operation(summary = "Get user by id")
    @PreAuthorize("#ss.hasAccess(#id, 'User')")
    public UserDto getById(
            @PathVariable
            final UUID id
    ) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @GetMapping("/{id}/maps")
    @Operation(summary = "Get user's maps by its id")
    public Page<MapDto> getAllMapsByCriteria(
            @RequestParam(required = false)
            @Parameter(description = "Search query")
            final String query,
            @RequestParam(required = false)
            @Parameter(description = "Publicity flag")
            final Boolean isPublic,
            @RequestParam(required = false)
            @Parameter(description = "Sort of results")
            final Map.SortType sort
    ) {
        UUID userId = securityService.getUserIdFromRequest();
        MapCriteria criteria = MapCriteria.builder()
                .query(query)
                .isPublic(isPublic)
                .author(userId)
                .sort(sort)
                .build();
        Page<Map> maps = mapService.getAll(criteria);
        return maps.map(mapMapper::toDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
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
