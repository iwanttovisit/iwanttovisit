package org.iwanttovisit.iwanttovisit.web.dto.mappers;

import org.iwanttovisit.iwanttovisit.model.User;
import org.iwanttovisit.iwanttovisit.web.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMappable<User, UserDto> {
}
