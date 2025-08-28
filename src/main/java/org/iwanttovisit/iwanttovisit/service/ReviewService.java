package org.iwanttovisit.iwanttovisit.service;

import org.iwanttovisit.iwanttovisit.model.Review;
import org.iwanttovisit.iwanttovisit.model.criteria.ReviewCriteria;

public interface ReviewService
        extends Blockable, CrudService<Review, ReviewCriteria> {
}
