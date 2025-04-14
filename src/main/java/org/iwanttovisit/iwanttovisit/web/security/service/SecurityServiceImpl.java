package org.iwanttovisit.iwanttovisit.web.security.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.iwanttovisit.iwanttovisit.model.User;
import org.iwanttovisit.iwanttovisit.web.security.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service("ss")
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser");
    }

    @Override
    public boolean validateUserAccess(
            final UUID userId
    ) {
        if (userId == null) {
            return false;
        }
        UUID userIdFromRequest = getUserIdFromRequest();
        return userId.equals(userIdFromRequest);
    }

    @Override
    public UUID getUserIdFromRequest() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!authentication.isAuthenticated()) {
            return null;
        }
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        return userDetails.getId();
    }

    @Override
    public String getIpFromRequest(
            final HttpServletRequest request
    ) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @Override
    public User getUserFromRequest() {
        UUID userId = getUserIdFromRequest();
        if (userId == null) {
            return null;
        }
        return new User(userId);
    }

    @Override
    public boolean validateUserRole(
            final User.Role... roles
    ) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!authentication.isAuthenticated()) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication
                .getAuthorities();
        for (User.Role role : roles) {
            if (authorities.contains(new SimpleGrantedAuthority(role.name()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canAccessUser(
            final UUID userId
    ) {
        return validateUserAccess(userId)
                || validateUserRole(User.Role.ROLE_ADMIN);
    }

}
