package org.iwanttovisit.iwanttovisit.model.criteria;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.iwanttovisit.iwanttovisit.model.Review;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;
import java.util.UUID;

@Getter
@SuperBuilder
public final class ReviewCriteria extends Criteria {

    private UUID place;
    private UUID author;
    private Integer mark;
    private Review.SortType sort;

    public abstract static class ReviewCriteriaBuilder<
            C extends ReviewCriteria,
            B extends ReviewCriteriaBuilder<C, B>
            > extends CriteriaBuilder<C, B> {

        public ReviewCriteriaBuilder() {
            this.sort = Review.SortType.CREATED;
        }

        public B place(
                final UUID place
        ) {
            this.place = place;
            return self();
        }

        public B author(
                final UUID author
        ) {
            this.author = author;
            return self();
        }

        public B mark(
                final Integer mark
        ) {
            this.mark = mark;
            return self();
        }

        public B sort(
                final Review.SortType sort
        ) {
            this.sort = Objects.requireNonNullElse(
                    sort,
                    Review.SortType.CREATED
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
                    case CREATED -> Sort.by("created")
                            .descending();
                    case MARK -> Sort.by("mark");
                }
        );
        return PageRequest.of(
                this.pageId - 1,
                this.perPage,
                sort
        );
    }

}
