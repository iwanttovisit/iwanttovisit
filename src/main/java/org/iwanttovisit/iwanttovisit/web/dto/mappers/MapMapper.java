package org.iwanttovisit.iwanttovisit.web.dto.mappers;

import org.iwanttovisit.iwanttovisit.model.Map;
import org.iwanttovisit.iwanttovisit.web.dto.MapDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapMapper extends BaseMappable<Map, MapDto> {
}
