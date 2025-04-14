package org.iwanttovisit.iwanttovisit.model.criteria;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.iwanttovisit.iwanttovisit.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Getter
@SuperBuilder
public final class UserCriteria extends Criteria {

    private User.SortType sort;

    public abstract static class UserCriteriaBuilder<
            C extends UserCriteria,
            B extends UserCriteriaBuilder<C, B>
            > extends Criteria.CriteriaBuilder<C, B> {

        public UserCriteriaBuilder() {
            this.sort = User.SortType.CREATED;
        }

        public B sort(
                final User.SortType sort
        ) {
            this.sort = Objects.requireNonNullElse(
                    sort,
                    User.SortType.CREATED
            );
            return self();
        }

    }

    @Override
    public Pageable getPageable(
            final Sort defaultSort
    ) {
        Sort sort = Objects.requireNonNullElseGet(
                defaultSort,
                () -> switch (this.sort) {
                    case USERNAME -> Sort.by("username");
                    case CREATED -> Sort.by("created")
                            .descending();
                    case LAST_SEEN -> Sort.by("lastSeen")
                            .descending();
                }
        );
        return PageRequest.of(
                this.pageId - 1,
                this.perPage,
                sort
        );
    }

}
