package org.iwanttovisit.iwanttovisit.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iwanttovisit.iwanttovisit.model.Category;
import org.iwanttovisit.iwanttovisit.model.Map;
import org.iwanttovisit.iwanttovisit.model.Place;
import org.iwanttovisit.iwanttovisit.model.User;
import org.iwanttovisit.iwanttovisit.model.criteria.MapCriteria;
import org.iwanttovisit.iwanttovisit.model.criteria.PlaceCriteria;
import org.iwanttovisit.iwanttovisit.service.MapService;
import org.iwanttovisit.iwanttovisit.service.PlaceService;
import org.iwanttovisit.iwanttovisit.web.dto.MapDto;
import org.iwanttovisit.iwanttovisit.web.dto.OnCreate;
import org.iwanttovisit.iwanttovisit.web.dto.OnUpdate;
import org.iwanttovisit.iwanttovisit.web.dto.PlaceDto;
import org.iwanttovisit.iwanttovisit.web.dto.mappers.MapMapper;
import org.iwanttovisit.iwanttovisit.web.dto.mappers.PlaceMapper;
import org.iwanttovisit.iwanttovisit.web.security.service.SecurityService;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/maps")
@RequiredArgsConstructor
@Validated
@Slf4j
public class MapController {

    private final MapService mapService;
    private final PlaceService placeService;
    private final SecurityService securityService;
    private final MapMapper mapMapper;
    private final PlaceMapper placeMapper;

    @GetMapping
    public Page<MapDto> getAllByCriteria(
            @RequestParam(required = false)
            final String query,
            @RequestParam(required = false)
            final Boolean isPublic,
            @RequestParam(required = false)
            final Map.SortType sort
    ) {
        MapCriteria criteria = MapCriteria.builder()
                .query(query)
                .isPublic(isPublic)
                .sort(sort)
                .build();
        Page<Map> maps = mapService.getAll(criteria);
        return maps.map(mapMapper::toDto);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public MapDto create(
            @RequestBody
            @Validated(OnCreate.class)
            final MapDto dto
    ) {
        Map map = mapMapper.toEntity(dto);
        User user = securityService.getUserFromRequest();
        map.setAuthor(user);
        mapService.create(map);
        log.warn(
                "Map {} is created by user {}.",
                map.getId(),
                user.getId()
        );
        return mapMapper.toDto(map);
    }

    @PutMapping
    @PreAuthorize("@ss.hasAccess(#dto.id, 'Map')")
    public MapDto update(
            @RequestBody
            @Validated(OnUpdate.class)
            final MapDto dto
    ) {
        Map map = mapMapper.toEntity(dto);
        mapService.update(map);
        log.warn(
                "Map {} is updated by user {}.",
                dto.getId(),
                securityService.getUserIdFromRequest()
        );
        return mapMapper.toDto(map);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.canRead(#id, 'Map')")
    public MapDto getById(
            @PathVariable
            final UUID id
    ) {
        Map map = mapService.getById(id);
        return mapMapper.toDto(map);
    }

    @GetMapping("/{id}/places")
    @PreAuthorize("@ss.canRead(#id, 'Map')")
    public Page<PlaceDto> getPlacesById(
            @PathVariable
            final UUID id,
            @RequestParam(required = false)
            final String query,
            @RequestParam(required = false)
            final Boolean isVisited,
            @RequestParam(required = false)
            final Category category,
            @RequestParam(required = false)
            final double latitude,
            @RequestParam(required = false)
            final double longitude,
            @RequestParam(
                    name = "bounds[0][0]",
                    required = false
            )
            final double minLatitude,
            @RequestParam(
                    name = "bounds[0][1]",
                    required = false
            )
            final double minLongitude,
            @RequestParam(
                    name = "bounds[1][0]",
                    required = false
            )
            final double maxLatitude,
            @RequestParam(
                    name = "bounds[1][1]",
                    required = false
            )
            final double maxLongitude,
            @RequestParam(required = false)
            final Place.SortType sort
    ) {
        Page<Place> places = placeService.getAll(
                PlaceCriteria.builder()
                        .map(id)
                        .query(query)
                        .isVisited(isVisited)
                        .category(category)
                        .latitude(latitude)
                        .longitude(longitude)
                        .bounds(new double[][]{
                                {minLatitude, minLongitude},
                                {maxLatitude, maxLongitude}
                        })
                        .sort(sort)
                        .build()
        );
        return places.map(placeMapper::toDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasAccess(#id, 'Map')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable
            final UUID id
    ) {
        mapService.delete(id);
        log.warn(
                "Map {} is deleted by user {}.",
                id,
                securityService.getUserIdFromRequest()
        );
    }

}
