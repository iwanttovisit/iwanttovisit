package org.iwanttovisit.iwanttovisit.web.dto.mappers;

import org.iwanttovisit.iwanttovisit.model.Place;
import org.iwanttovisit.iwanttovisit.web.dto.PlaceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaceMapper extends BaseMappable<Place, PlaceDto> {
}
