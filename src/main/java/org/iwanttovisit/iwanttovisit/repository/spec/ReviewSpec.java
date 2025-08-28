package org.iwanttovisit.iwanttovisit.repository.spec;

import org.iwanttovisit.iwanttovisit.model.Review;
import org.iwanttovisit.iwanttovisit.model.Status;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface ReviewSpec {

    static Specification<Review> hasStatus(
            final Status status
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("status"), status);
    }

    static Specification<Review> hasAuthor(
            final UUID author
    ) {
        return (root, query, criteriaBuilder) -> {
            if (author == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("author").get("id"), author);
        };
    }

    static Specification<Review> hasPlace(
            final UUID place
    ) {
        return (root, query, criteriaBuilder) -> {
            if (place == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("place").get("id"), place);
        };
    }

    static Specification<Review> hasMark(
            final Integer mark
    ) {
        return (root, query, criteriaBuilder) -> {
            if (mark == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("mark"), mark);
        };
    }

}
