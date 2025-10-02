package org.iwanttovisit.iwanttovisit.repository.spec;

import org.iwanttovisit.iwanttovisit.model.Map;
import org.iwanttovisit.iwanttovisit.model.Status;
import org.iwanttovisit.iwanttovisit.service.Utils;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface MapSpec {

    static Specification<Map> hasStatus(
            final Status status
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("status"), status);
    }

    static Specification<Map> isPublic(
            final boolean isPublic
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("is_public"), isPublic);
    }

    static Specification<Map> hasAuthor(
            final UUID author
    ) {
        return (root, query, criteriaBuilder) -> {
            if (author == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("author").get("id"), author);
        };
    }

    static Specification<Map> fullTextSearch(
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

    static Specification<Map> orderByTsRank(
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
