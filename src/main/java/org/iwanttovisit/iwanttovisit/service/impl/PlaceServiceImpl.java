package org.iwanttovisit.iwanttovisit.service.impl;

import lombok.RequiredArgsConstructor;
import org.iwanttovisit.iwanttovisit.model.Place;
import org.iwanttovisit.iwanttovisit.model.Status;
import org.iwanttovisit.iwanttovisit.model.criteria.PlaceCriteria;
import org.iwanttovisit.iwanttovisit.model.exception.ResourceNotFoundException;
import org.iwanttovisit.iwanttovisit.repository.PlaceRepository;
import org.iwanttovisit.iwanttovisit.repository.spec.PlaceSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository repository;

    @Override
    public Place getById(
            final UUID id,
            final boolean showAnyway
    ) {
        Place place = repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (showAnyway) {
            return place;
        }
        if (place.getStatus() != Status.ACTIVE) {
            throw new ResourceNotFoundException();
        }
        return place;
    }

    @Override
    public Page<Place> getAll(
            final PlaceCriteria criteria
    ) {
        return repository.findAll(
                Specification.allOf(
                        PlaceSpec.hasStatus(criteria.getStatus()),
                        PlaceSpec.isVisited(criteria.isVisited()),
                        PlaceSpec.hasAuthor(criteria.getAuthor()),
                        PlaceSpec.hasMap(criteria.getMap()),
                        PlaceSpec.hasCategory(criteria.getCategory()),
                        PlaceSpec.fullTextSearch(criteria.getQuery()),
                        PlaceSpec.orderByTsRank(criteria.getQuery())
                ),
                criteria.getPageable()
        );
    }

    @Override
    public Place create(
            final Place entity
    ) {
        return repository.save(entity);
    }

    @Override
    public Place update(
            final Place entity
    ) {
        Place place = getById(entity.getId());
        place.setName(entity.getName());
        place.setDescription(entity.getDescription());
        place.setCategory(entity.getCategory());
        place.setVisited(entity.isVisited());
        place.setCoordinates(entity.getCoordinates());
        place.setUpdated(LocalDateTime.now());
        return repository.save(place);
    }

    @Override
    public void delete(
            final UUID id
    ) {
        Place place = getById(id, true);
        if (place.getStatus() != Status.DELETED) {
            place.setStatus(Status.DELETED);
            place.setUpdated(LocalDateTime.now());
            repository.save(place);
        }
    }

    @Override
    public void block(
            final UUID id
    ) {
        Place place = getById(id);
        if (place.getStatus() != Status.DELETED) {
            place.setStatus(Status.BLOCKED);
            place.setUpdated(LocalDateTime.now());
            repository.save(place);
        }
    }

    @Override
    public void unblock(
            final UUID id
    ) {
        Place place = getById(id, true);
        if (place.getStatus() == Status.BLOCKED) {
            place.setStatus(Status.ACTIVE);
            place.setUpdated(LocalDateTime.now());
            repository.save(place);
        }
    }

}
