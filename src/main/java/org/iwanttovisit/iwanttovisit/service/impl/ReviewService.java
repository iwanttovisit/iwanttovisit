package org.iwanttovisit.iwanttovisit.service.impl;

import org.iwanttovisit.iwanttovisit.model.Review;
import org.iwanttovisit.iwanttovisit.model.criteria.ReviewCriteria;
import org.iwanttovisit.iwanttovisit.service.Blockable;
import org.iwanttovisit.iwanttovisit.service.CrudService;

public interface ReviewService
        extends Blockable, CrudService<Review, ReviewCriteria> {
}
