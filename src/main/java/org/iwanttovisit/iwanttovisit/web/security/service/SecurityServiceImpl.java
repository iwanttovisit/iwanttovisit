package org.iwanttovisit.iwanttovisit.web.security.service;

import lombok.RequiredArgsConstructor;
import org.iwanttovisit.iwanttovisit.model.Map;
import org.iwanttovisit.iwanttovisit.model.User;
import org.iwanttovisit.iwanttovisit.service.MapService;
import org.iwanttovisit.iwanttovisit.web.security.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Function;

@Service("ss")
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final MapService mapService;

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

    @Override
    public boolean hasAccess(
            final UUID entityId,
            final String className
    ) {
        return switch (className) {
            case "User" -> canAccessUser(entityId);
            case "Map" -> canAccessObject(
                    entityId,
                    id -> mapService.getById(
                            id,
                            true
                    ).getAuthor()
            );
            default -> false;
        };
    }

    @Override
    public boolean canRead(
            final UUID entityId,
            final String className
    ) {
        return switch (className) {
            case "User" -> hasAccess(entityId, "User");
            case "Map" -> canReadMap(entityId);
            default -> false;
        };
    }

    private boolean canAccessUser(
            final UUID userId
    ) {
        if (userId == null) {
            return false;
        }
        UUID userIdFromRequest = getUserIdFromRequest();
        return userId.equals(userIdFromRequest);
    }

    private boolean canAccessObject(
            final UUID objectId,
            final Function<UUID, User> getUserFunction
    ) {
        UUID userId = getUserIdFromRequest();
        if (userId == null) {
            return false;
        }
        return getUserFunction.apply(objectId).getId().equals(userId);
    }

    private boolean canReadMap(
            final UUID mapId
    ) {
        Map map = mapService.getById(mapId);
        return map.isPublic() || canAccessObject(
                mapId,
                id -> map.getAuthor()
        );
    }

}
