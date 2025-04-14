package org.iwanttovisit.iwanttovisit.web.security.service;

import org.iwanttovisit.iwanttovisit.model.User;

import java.util.UUID;

public interface SecurityService {

    boolean isAuthenticated();

    UUID getUserIdFromRequest();

    User getUserFromRequest();

    boolean canAccessUser(
            UUID userId
    );

}
