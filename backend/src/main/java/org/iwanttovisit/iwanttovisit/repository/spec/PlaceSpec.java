package org.iwanttovisit.iwanttovisit.repository.spec;

import org.iwanttovisit.iwanttovisit.model.Category;
import org.iwanttovisit.iwanttovisit.model.Place;
import org.iwanttovisit.iwanttovisit.model.Status;
import org.iwanttovisit.iwanttovisit.service.Utils;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface PlaceSpec {

    static Specification<Place> hasStatus(
            final Status status
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("status"), status);
    }

    static Specification<Place> isVisited(
            final boolean isVisited
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("is_visited"), isVisited);
    }

    static Specification<Place> hasCategory(
            final Category category
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("category"), category);
    }

    static Specification<Place> hasAuthor(
            final UUID author
    ) {
        return (root, query, criteriaBuilder) -> {
            if (author == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("author").get("id"), author);
        };
    }

    static Specification<Place> hasMap(
            final UUID map
    ) {
        return (root, query, criteriaBuilder) -> {
            if (map == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("map").get("id"), map);
        };
    }

    //TODO add search by coordinates bounds

    static Specification<Place> fullTextSearch(
            final String searchQuery
    ) {
        return (root, query, criteriaBuilder) -> {
            if (searchQuery == null || searchQuery.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            String modifiedQuery = Utils.modifyFTS(searchQuery);
            return criteriaBuilder.isTrue(
                    criteriaBuilder.function(
                            "tsvector_match",
                            Boolean.class,
                            root.get("fts"),
                            criteriaBuilder.function(
                                    "to_tsquery",
                                    String.class,
                                    criteriaBuilder.literal("russian"),
                                    criteriaBuilder.literal(modifiedQuery)
                            )
                    )
            );
        };
    }

    static Specification<Place> orderByTsRank(
            final String orderQuery
    ) {
        return (root, query, criteriaBuilder) -> {
            if (orderQuery != null && !orderQuery.isEmpty()) {
                String modifiedQuery = Utils.modifyFTS(orderQuery);
                var rank = criteriaBuilder.function(
                        "ts_rank",
                        Double.class,
                        root.get("fts"),
                        criteriaBuilder.function(
                                "to_tsquery",
                                Object.class,
                                criteriaBuilder.literal("russian"),
                                criteriaBuilder.literal(modifiedQuery)
                        )
                );
                query.orderBy(criteriaBuilder.desc(rank));
            }
            return criteriaBuilder.conjunction();
        };
    }

}
