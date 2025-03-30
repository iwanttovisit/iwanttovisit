package org.iwanttovisit.iwanttovisit.model.criteria;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.iwanttovisit.iwanttovisit.model.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Getter
@SuperBuilder
public abstract class Criteria {

    protected int pageId;
    protected int perPage;
    protected Status status;
    protected String query;

    public String getQuery() {
        if (query != null) {
            return query.trim();
        }
        return null;
    }

    public abstract static class CriteriaBuilder<
            C extends Criteria,
            B extends CriteriaBuilder<C, B>
            > {

        protected int pageId;
        protected int perPage;

        public CriteriaBuilder() {
            this.pageId = 1;
            this.perPage = 10;
            this.status = Status.ACTIVE;
        }

        public B pageId(
                final Integer pageId
        ) {
            this.pageId = Objects.requireNonNullElse(
                    pageId,
                    1
            );
            return self();
        }

        public B perPage(
                final Integer perPage
        ) {
            if (perPage == null) {
                this.perPage = 10;
            } else {
                this.perPage = Math.min(
                        perPage,
                        Constants.MAX_FETCH_SIZE
                );
            }
            return self();
        }

        public B status(
                final Status status
        ) {
            this.status = Objects.requireNonNullElse(
                    status,
                    Status.ACTIVE
            );
            return self();
        }

        public B query(
                final String query
        ) {
            this.query = query;
            return self();
        }

    }

    public Pageable getPageable() {
        return getPageable(null);
    }

    public abstract Pageable getPageable(
            Sort defaultSort
    );

}
