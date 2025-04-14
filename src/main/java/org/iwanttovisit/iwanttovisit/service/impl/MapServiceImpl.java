package org.iwanttovisit.iwanttovisit.service.impl;

import lombok.RequiredArgsConstructor;
import org.iwanttovisit.iwanttovisit.model.Map;
import org.iwanttovisit.iwanttovisit.model.Status;
import org.iwanttovisit.iwanttovisit.model.criteria.MapCriteria;
import org.iwanttovisit.iwanttovisit.model.exception.ResourceNotFoundException;
import org.iwanttovisit.iwanttovisit.repository.MapRepository;
import org.iwanttovisit.iwanttovisit.repository.spec.MapSpec;
import org.iwanttovisit.iwanttovisit.service.MapService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapServiceImpl implements MapService {

    private final MapRepository repository;

    @Override
    public Map getById(
            final UUID id,
            final boolean showAnyway
    ) {
        Map map = repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (showAnyway) {
            return map;
        }
        if (map.getStatus() != Status.ACTIVE) {
            throw new ResourceNotFoundException();
        }
        return map;
    }

    @Override
    public Page<Map> getAll(
            final MapCriteria criteria
    ) {
        return repository.findAll(
                Specification.allOf(
                        MapSpec.isPublic(criteria.isPublic()),
                        MapSpec.hasAuthor(criteria.getAuthor()),
                        MapSpec.hasStatus(criteria.getStatus()),
                        MapSpec.fullTextSearch(criteria.getQuery()),
                        MapSpec.orderByTsRank(criteria.getQuery())
                ),
                criteria.getPageable()
        );
    }

    @Override
    @Transactional
    public Map create(
            final Map entity
    ) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public Map update(
            final Map entity
    ) {
        Map map = getById(entity.getId());
        map.setName(entity.getName());
        map.setDescription(entity.getDescription());
        map.setPublic(entity.isPublic());
        map.setUpdated(LocalDateTime.now());
        return repository.save(map);
    }

    @Override
    @Transactional
    public void delete(
            final UUID id
    ) {
        Map map = getById(id, true);
        if (map.getStatus() != Status.DELETED) {
            map.setStatus(Status.DELETED);
            map.setUpdated(LocalDateTime.now());
            repository.save(map);
        }
    }

    @Override
    @Transactional
    public void block(
            final UUID id
    ) {
        Map map = getById(id);
        if (map.getStatus() != Status.DELETED) {
            map.setStatus(Status.BLOCKED);
            map.setUpdated(LocalDateTime.now());
            repository.save(map);
        }
    }

    @Override
    @Transactional
    public void unblock(
            final UUID id
    ) {
        Map map = getById(id, true);
        if (map.getStatus() == Status.BLOCKED) {
            map.setStatus(Status.ACTIVE);
            map.setUpdated(LocalDateTime.now());
            repository.save(map);
        }
    }

}
