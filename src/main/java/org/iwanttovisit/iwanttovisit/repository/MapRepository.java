package org.iwanttovisit.iwanttovisit.repository;

import org.iwanttovisit.iwanttovisit.model.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface MapRepository extends JpaRepository<Map, UUID>,
        JpaSpecificationExecutor<Map> {
}
