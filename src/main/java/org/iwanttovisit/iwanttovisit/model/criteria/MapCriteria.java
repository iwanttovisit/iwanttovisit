package org.iwanttovisit.iwanttovisit.model.criteria;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.iwanttovisit.iwanttovisit.model.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;
import java.util.UUID;

@Getter
@SuperBuilder
public final class MapCriteria extends Criteria {

    private boolean isPublic;
    private UUID author;
    private Map.SortType sort;

    public abstract static class MapCriteriaBuilder<
            C extends MapCriteria,
            B extends MapCriteriaBuilder<C, B>
            > extends CriteriaBuilder<C, B> {

        public MapCriteriaBuilder() {
            this.sort = Map.SortType.CREATED;
        }

        public B isPublic(
                final Boolean isPublic
        ) {
            this.isPublic = Objects.requireNonNullElse(
                    isPublic,
                    false
            );
            return self();
        }

        public B author(
                final UUID author
        ) {
            this.author = author;
            return self();
        }

        public B sort(
                final Map.SortType sort
        ) {
            this.sort = Objects.requireNonNullElse(
                    sort,
                    Map.SortType.CREATED
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
                    case UPDATED -> Sort.by("updated")
                            .descending();
                    case CREATED -> Sort.by("created")
                            .descending();
                    case NAME -> Sort.by("name");
                }
        );
        return PageRequest.of(
                this.pageId - 1,
                this.perPage,
                sort
        );
    }

}
