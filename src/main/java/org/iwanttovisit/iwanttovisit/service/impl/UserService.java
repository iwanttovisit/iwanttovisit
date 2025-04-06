package org.iwanttovisit.iwanttovisit.service.impl;

import org.iwanttovisit.iwanttovisit.model.User;
import org.iwanttovisit.iwanttovisit.model.criteria.UserCriteria;
import org.iwanttovisit.iwanttovisit.service.Blockable;
import org.iwanttovisit.iwanttovisit.service.CrudService;

public interface UserService
        extends Blockable, CrudService<User, UserCriteria> {
}
