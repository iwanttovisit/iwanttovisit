package org.iwanttovisit.iwanttovisit.repository;

import org.iwanttovisit.iwanttovisit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>,
        JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(
            String username
    );

    @Modifying
    @Query(value = """
            UPDATE users u
            SET last_seen = :lastSeen
            WHERE u.username = :username
            """, nativeQuery = true)
    void updateLastSeen(
            @Param("username") String username,
            @Param("lastSeen")
            LocalDateTime lastSeen
    );

}
