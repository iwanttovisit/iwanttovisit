package org.iwanttovisit.iwanttovisit.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

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

    @Column(name = "role")
    @ElementCollection
    @CollectionTable(name = "user_roles")
    @Enumerated(EnumType.STRING)
    private Set<Role> userRoles;

    public enum Role {

        ROLE_USER,
        ROLE_ADMIN

    }

    public enum SortType {

        CREATED,
        USERNAME,
        LAST_SEEN

    }

}
