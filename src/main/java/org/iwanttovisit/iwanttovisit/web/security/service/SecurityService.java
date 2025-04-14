package org.iwanttovisit.iwanttovisit.web.security.service;

import jakarta.servlet.http.HttpServletRequest;
import org.iwanttovisit.iwanttovisit.model.User;

import java.util.UUID;

public interface SecurityService {

    boolean isAuthenticated();

    boolean validateUserAccess(
            UUID userId
    );

    UUID getUserIdFromRequest();

    String getIpFromRequest(
            HttpServletRequest request
    );

    User getUserFromRequest();

    boolean validateUserRole(
            User.Role... roles
    );

    boolean canAccessUser(
            UUID userId
    );

}
