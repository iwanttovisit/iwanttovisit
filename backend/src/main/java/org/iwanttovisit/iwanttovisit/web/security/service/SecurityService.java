package org.iwanttovisit.iwanttovisit.web.security.service;

import org.iwanttovisit.iwanttovisit.model.User;

import java.util.UUID;

public interface SecurityService {

    UUID getUserIdFromRequest();

    User getUserFromRequest();

    boolean hasAccess(
            UUID entityId,
            String className
    );

    boolean canRead(
            UUID entityId,
            String className
    );

}
