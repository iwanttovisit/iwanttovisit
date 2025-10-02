package org.iwanttovisit.iwanttovisit.service;

import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CrudService<E, C> {

    default E getById(
            final UUID id
    ) {
        return getById(id, false);
    }

    E getById(
            UUID id,
            boolean showAnyway
    );

    Page<E> getAll(
            C criteria
    );

    E create(
            E entity
    );

    E update(
            E entity
    );

    void delete(
            UUID id
    );

}
