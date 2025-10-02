package org.iwanttovisit.iwanttovisit.model.criteria;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.iwanttovisit.iwanttovisit.model.Category;
import org.iwanttovisit.iwanttovisit.model.Place;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Getter
@SuperBuilder
public final class PlaceCriteria extends Criteria {

    private boolean isVisited;
    private Category category;
    private UUID author;
    private UUID map;
    private double latitude;
    private double longitude;
    private double[][] bounds;
    private Place.SortType sort;

    public abstract static class PlaceCriteriaBuilder<
            C extends PlaceCriteria,
            B extends PlaceCriteriaBuilder<C, B>
            > extends CriteriaBuilder<C, B> {

        public PlaceCriteriaBuilder() {
            this.sort = Place.SortType.CREATED;
        }

        public B isVisited(
                final Boolean isVisited
        ) {
            this.isVisited = Objects.requireNonNullElse(
                    isVisited,
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

        public B map(
                final UUID map
        ) {
            this.map = map;
            return self();
        }

        public B latitude(
                final double latitude
        ) {
            this.latitude = latitude;
            return self();
        }

        public B longitude(
                final double longitude
        ) {
            this.longitude = longitude;
            return self();
        }

        public B bounds(
                final double[][] bounds
        ) {
            if (bounds == null || bounds.length != 2
                    || bounds[0].length != 2 || bounds[1].length != 2) {
                throw new IllegalArgumentException(
                        "Границы должны быть двумерным массивом из двух точек: "
                                + Arrays.deepToString(bounds)
                );
            }
            this.bounds = bounds;
            return self();
        }

        public B sort(
                final Place.SortType sort
        ) {
            this.sort = Objects.requireNonNullElse(
                    sort,
                    Place.SortType.CREATED
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
                    case RATING -> Sort.by("rating")
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
