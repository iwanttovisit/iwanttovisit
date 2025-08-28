package org.iwanttovisit.iwanttovisit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    private String name;

    @Column(unique = true)
    private String username;

    private String password;
    private LocalDateTime lastSeen;

    public User(
            final UUID id
    ) {
        super(id);
    }

    public enum SortType {

        CREATED,
        USERNAME,
        LAST_SEEN

    }

}
