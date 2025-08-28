package org.iwanttovisit.iwanttovisit.service;

import org.iwanttovisit.iwanttovisit.model.User;
import org.iwanttovisit.iwanttovisit.model.criteria.UserCriteria;

public interface UserService extends CrudService<User, UserCriteria> {

    User getByUsername(
            String username
    );

    void updateLastSeen(
            String username
    );

    void activate(
            String token
    );

}
