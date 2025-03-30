package org.iwanttovisit.iwanttovisit.service.impl;

import lombok.RequiredArgsConstructor;
import org.iwanttovisit.iwanttovisit.model.Status;
import org.iwanttovisit.iwanttovisit.model.User;
import org.iwanttovisit.iwanttovisit.model.criteria.UserCriteria;
import org.iwanttovisit.iwanttovisit.model.exception.ResourceAlreadyExistsException;
import org.iwanttovisit.iwanttovisit.model.exception.ResourceNotFoundException;
import org.iwanttovisit.iwanttovisit.repository.UserRepository;
import org.iwanttovisit.iwanttovisit.repository.spec.UserSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User getById(
            final UUID id,
            final boolean showAnyway
    ) {
        User user = repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (showAnyway) {
            return user;
        }
        if (user.getStatus() != Status.ACTIVE) {
            throw new ResourceNotFoundException();
        }
        return user;
    }

    @Override
    public Page<User> getAll(
            final UserCriteria criteria
    ) {
        return repository.findAll(
                Specification.allOf(
                        UserSpec.containsUsername(criteria.getQuery()),
                        UserSpec.hasRole(criteria.getRole()),
                        UserSpec.hasStatus(criteria.getStatus())
                ),
                criteria.getPageable()
        );
    }

    @Override
    @Transactional
    public User create(
            final User entity
    ) {
        if (existsByUsername(entity.getUsername())) {
            throw new ResourceAlreadyExistsException(
                    "Этот email недоступен."
            );
        }
        //TODO
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entity.setStatus(Status.NOT_ACTIVE);
        entity.setUserRoles(Set.of(User.Role.ROLE_USER));
        repository.save(entity);
        //TODO
//        String token = jwtService.create(
//                TokenParameters.builder(
//                                user.getUsername(),
//                                TokenType.ACTIVATION.name(),
//                                jwtProperties.getActivation()
//                        )
//                        .claim("userId", user.getId())
//                        .build()
//        );
//        MailParameters params = MailParameters.builder(
//                        user.getUsername(),
//                        MailType.ACTIVATION.name()
//                )
//                .property("token", token)
//                .property(
//                        "name",
//                        user.getName() != null
//                                ? user.getName()
//                                : "Пользователь"
//                )
//                .property(
//                        "link",
//                        "https://reviewer.of.by/auth/confirm?token=" + token
//                )
//                .build();
//        mailService.send(params);
        return repository.findOne(
                UserSpec.hasUsername(entity.getUsername())
        ).get();
    }

    public boolean existsByUsername(
            final String username
    ) {
        return repository.exists(
                UserSpec.hasUsername(username)
        );
    }

    @Override
    @Transactional
    public User update(
            final User entity
    ) {
        User user = getById(entity.getId());
        user.setName(entity.getName());
        //TODO
//        if (entity.getPassword() != null) {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
        user.setUpdated(LocalDateTime.now());
        return repository.save(user);
    }

    @Override
    @Transactional
    public void delete(
            final UUID id
    ) {
        User user = getById(id);
        user.setStatus(Status.DELETED);
        user.setUpdated(LocalDateTime.now());
        repository.save(user);
    }

    @Override
    @Transactional
    public void block(
            final UUID id
    ) {

    }

    @Override
    @Transactional
    public void unblock(
            final UUID id
    ) {

    }

}
