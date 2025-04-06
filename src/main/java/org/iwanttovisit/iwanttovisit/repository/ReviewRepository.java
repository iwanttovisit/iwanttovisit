package org.iwanttovisit.iwanttovisit.repository;

import org.iwanttovisit.iwanttovisit.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID>,
        JpaSpecificationExecutor<Review> {
}
