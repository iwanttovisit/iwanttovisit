package org.iwanttovisit.iwanttovisit.repository;

import org.iwanttovisit.iwanttovisit.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PlaceRepository extends JpaRepository<Place, UUID>,
        JpaSpecificationExecutor<Place> {
}
