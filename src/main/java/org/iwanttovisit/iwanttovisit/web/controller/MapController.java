package org.iwanttovisit.iwanttovisit.web.controller;

import lombok.RequiredArgsConstructor;
import org.iwanttovisit.iwanttovisit.model.Map;
import org.iwanttovisit.iwanttovisit.model.User;
import org.iwanttovisit.iwanttovisit.service.MapService;
import org.iwanttovisit.iwanttovisit.web.dto.MapDto;
import org.iwanttovisit.iwanttovisit.web.dto.OnCreate;
import org.iwanttovisit.iwanttovisit.web.dto.OnUpdate;
import org.iwanttovisit.iwanttovisit.web.dto.mappers.MapMapper;
import org.iwanttovisit.iwanttovisit.web.security.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/maps")
@RequiredArgsConstructor
@Validated
public class MapController {

    private final MapService mapService;
    private final SecurityService securityService;
    private final MapMapper mapMapper;

    @PostMapping
    public MapDto create(
            @RequestBody
            @Validated(OnCreate.class)
            final MapDto dto
    ) {
        Map map = mapMapper.toEntity(dto);
        User user = securityService.getUserFromRequest();
        map.setAuthor(user);
        mapService.create(map);
        return mapMapper.toDto(map);
    }

    @PutMapping
    public MapDto update(
            @RequestBody
            @Validated(OnUpdate.class)
            final MapDto dto
    ) {
        Map map = mapMapper.toEntity(dto);
        mapService.update(map);
        return mapMapper.toDto(map);
    }

    @GetMapping("/{id}")
    public MapDto getById(
            @PathVariable
            final UUID id
    ) {
        Map map = mapService.getById(id);
        return mapMapper.toDto(map);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable
            final UUID id
    ) {
        mapService.delete(id);
    }

}
