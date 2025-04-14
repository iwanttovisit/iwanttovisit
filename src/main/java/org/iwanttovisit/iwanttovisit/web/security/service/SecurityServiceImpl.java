package org.iwanttovisit.iwanttovisit.web.security.service;

import lombok.RequiredArgsConstructor;
import org.iwanttovisit.iwanttovisit.model.User;
import org.iwanttovisit.iwanttovisit.web.security.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    public boolean canAccessUser(
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
    public User getUserFromRequest() {
        UUID userId = getUserIdFromRequest();
        if (userId == null) {
            return null;
        }
        return new User(userId);
    }

}
