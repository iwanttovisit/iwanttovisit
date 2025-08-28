package org.iwanttovisit.iwanttovisit.service.impl;

import io.github.ilyalisov.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.iwanttovisit.iwanttovisit.model.Status;
import org.iwanttovisit.iwanttovisit.model.User;
import org.iwanttovisit.iwanttovisit.model.criteria.UserCriteria;
import org.iwanttovisit.iwanttovisit.model.exception.ResourceAlreadyExistsException;
import org.iwanttovisit.iwanttovisit.model.exception.ResourceNotFoundException;
import org.iwanttovisit.iwanttovisit.model.exception.TokenNotValidException;
import org.iwanttovisit.iwanttovisit.repository.UserRepository;
import org.iwanttovisit.iwanttovisit.repository.spec.UserSpec;
import org.iwanttovisit.iwanttovisit.service.UserService;
import org.iwanttovisit.iwanttovisit.web.security.TokenType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final TokenService jwtService;
    private final PasswordEncoder passwordEncoder;

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
    public User getByUsername(
            final String username
    ) {
        User user = repository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
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
                    "This email can not be used."
            );
        }
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setStatus(Status.NOT_ACTIVE);
        entity.setLastSeen(LocalDateTime.now());
        return repository.save(entity);
    }

    @Override
    public void activate(
            final String token
    ) {
        if (jwtService.isExpired(token)) {
            throw new TokenNotValidException("Token expired.");
        }
        if (!jwtService.getType(token).equals(TokenType.ACTIVATION.name())) {
            throw new TokenNotValidException("Token type is incorrect.");
        }
        UUID userId = UUID.fromString(
                (String) jwtService.claim(token, "userId")
        );
        User user = getById(userId, true);
        user.setStatus(Status.ACTIVE);
        user.setUpdated(LocalDateTime.now());
        repository.save(user);
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
        if (entity.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setUpdated(LocalDateTime.now());
        return repository.save(user);
    }

    @Override
    public void updateLastSeen(
            final String username
    ) {
        repository.updateLastSeen(
                username,
                LocalDateTime.now()
        );
    }

    @Override
    @Transactional
    public void delete(
            final UUID id
    ) {
        User user = getById(id, true);
        if (user.getStatus() != Status.DELETED) {
            user.setStatus(Status.DELETED);
            user.setUpdated(LocalDateTime.now());
            repository.save(user);
        }
    }

}
