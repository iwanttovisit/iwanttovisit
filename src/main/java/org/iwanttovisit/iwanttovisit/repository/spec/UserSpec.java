package org.iwanttovisit.iwanttovisit.repository.spec;

import org.iwanttovisit.iwanttovisit.model.Status;
import org.iwanttovisit.iwanttovisit.model.User;
import org.springframework.data.jpa.domain.Specification;

public interface UserSpec {

    static Specification<User> hasUsername(
            final String username
    ) {
        return (root, query, criteriaBuilder) -> {
            if (username == null || username.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("username")),
                    username.toLowerCase()
            );
        };
    }

    static Specification<User> containsUsername(
            final String username
    ) {
        return (root, query, criteriaBuilder) -> {
            if (username == null || username.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            String pattern = "%" + username.toLowerCase() + "%";
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("username")),
                    pattern
            );
        };
    }

    static Specification<User> hasRole(
            final User.Role role
    ) {
        return (root, query, criteriaBuilder) -> {
            if (role == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.and(
                    root.join("userRoles")
                            .in(role)
            );
        };
    }

    static Specification<User> hasStatus(
            final Status status
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("status"), status);
    }

}
