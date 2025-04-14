package org.iwanttovisit.iwanttovisit.web.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.iwanttovisit.iwanttovisit.model.Status;
import org.iwanttovisit.iwanttovisit.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class JwtUser implements UserDetails {

    private final UUID id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Status status;

    public JwtUser(
            final User user
    ) {
        this(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getUserRoles())),
                user.getStatus()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(
            final List<User.Role> userRoles
    ) {
        return userRoles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new).
                collect(Collectors.toList());
    }

    private JwtUser(
            final UUID id,
            final String username,
            final String password,
            final Collection<? extends GrantedAuthority> authorities,
            final Status status
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.status = status;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return status == Status.ACTIVE;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return status == Status.ACTIVE;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return status == Status.ACTIVE;
    }

    @Override
    public boolean isEnabled() {
        return status == Status.ACTIVE;
    }

}
