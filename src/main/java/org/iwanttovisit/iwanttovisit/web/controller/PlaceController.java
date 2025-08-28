package org.iwanttovisit.iwanttovisit.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iwanttovisit.iwanttovisit.model.Place;
import org.iwanttovisit.iwanttovisit.model.User;
import org.iwanttovisit.iwanttovisit.service.PlaceService;
import org.iwanttovisit.iwanttovisit.web.dto.OnCreate;
import org.iwanttovisit.iwanttovisit.web.dto.OnUpdate;
import org.iwanttovisit.iwanttovisit.web.dto.PlaceDto;
import org.iwanttovisit.iwanttovisit.web.dto.mappers.PlaceMapper;
import org.iwanttovisit.iwanttovisit.web.security.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PlaceController {

    private final PlaceService placeService;
    private final SecurityService securityService;
    private final PlaceMapper placeMapper;

    @PostMapping
    @PreAuthorize("#ss.hasAccess(#dto.map.id, 'Map')")
    public PlaceDto create(
            @RequestBody
            @Validated(OnCreate.class)
            final PlaceDto dto
    ) {
        Place place = placeMapper.toEntity(dto);
        User user = securityService.getUserFromRequest();
        place.setAuthor(user);
        placeService.create(place);
        log.warn(
                "Place {} is created by user {}.",
                place.getId(),
                user.getId()
        );
        return placeMapper.toDto(place);
    }

    @PutMapping
    @PreAuthorize("#ss.hasAccess(#dto.map.id, 'Map') "
            + "&& #ss.hasAccess(#dto.id, 'Place')")
    public PlaceDto update(
            @RequestBody
            @Validated(OnUpdate.class)
            final PlaceDto dto
    ) {
        Place place = placeMapper.toEntity(dto);
        placeService.update(place);
        log.warn(
                "Place {} is updated by user {}.",
                place.getId(),
                securityService.getUserIdFromRequest()
        );
        return placeMapper.toDto(place);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#ss.hasAccess(#id, 'Place')")
    public PlaceDto getById(
            @PathVariable
            final UUID id
    ) {
        Place place = placeService.getById(id);
        return placeMapper.toDto(place);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#ss.hasAccess(#id, 'Place')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable
            final UUID id
    ) {
        placeService.delete(id);
        log.warn(
                "Place {} is deleted by user {}",
                id,
                securityService.getUserIdFromRequest()
        );
    }

}
