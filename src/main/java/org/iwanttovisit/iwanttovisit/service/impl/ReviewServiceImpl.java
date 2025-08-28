package org.iwanttovisit.iwanttovisit.service.impl;

import lombok.RequiredArgsConstructor;
import org.iwanttovisit.iwanttovisit.model.Review;
import org.iwanttovisit.iwanttovisit.model.Status;
import org.iwanttovisit.iwanttovisit.model.criteria.ReviewCriteria;
import org.iwanttovisit.iwanttovisit.model.exception.ResourceNotFoundException;
import org.iwanttovisit.iwanttovisit.repository.ReviewRepository;
import org.iwanttovisit.iwanttovisit.repository.spec.ReviewSpec;
import org.iwanttovisit.iwanttovisit.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;

    @Override
    public Review getById(
            final UUID id,
            final boolean showAnyway
    ) {
        Review review = repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (showAnyway) {
            return review;
        }
        if (review.getStatus() != Status.ACTIVE) {
            throw new ResourceNotFoundException();
        }
        return review;
    }

    @Override
    public Page<Review> getAll(
            final ReviewCriteria criteria
    ) {
        return repository.findAll(
                Specification.allOf(
                        ReviewSpec.hasStatus(Status.ACTIVE),
                        ReviewSpec.hasAuthor(criteria.getAuthor()),
                        ReviewSpec.hasPlace(criteria.getPlace()),
                        ReviewSpec.hasMark(criteria.getMark())
                ),
                criteria.getPageable()
        );
    }

    @Override
    @Transactional
    public Review create(
            final Review entity
    ) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public Review update(
            final Review entity
    ) {
        Review review = getById(entity.getId(), false);
        review.setText(entity.getText());
        review.setMark(entity.getMark());
        review.setUpdated(LocalDateTime.now());
        return repository.save(review);
    }

    @Override
    @Transactional
    public void delete(
            final UUID id
    ) {
        Review review = getById(id, true);
        if (review.getStatus() != Status.DELETED) {
            review.setStatus(Status.DELETED);
            review.setUpdated(LocalDateTime.now());
            repository.save(review);
        }
    }

    @Override
    @Transactional
    public void block(
            final UUID id
    ) {
        Review review = getById(id);
        if (review.getStatus() != Status.DELETED) {
            review.setStatus(Status.BLOCKED);
            review.setUpdated(LocalDateTime.now());
            repository.save(review);
        }
    }

    @Override
    @Transactional
    public void unblock(
            final UUID id
    ) {
        Review review = getById(id, true);
        if (review.getStatus() == Status.BLOCKED) {
            review.setStatus(Status.ACTIVE);
            review.setUpdated(LocalDateTime.now());
            repository.save(review);
        }
    }

}
