package org.iwanttovisit.iwanttovisit.web.dto.mappers;

import org.iwanttovisit.iwanttovisit.model.BaseEntity;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

public interface BaseMappable<E extends BaseEntity, D> extends Mappable<E, D> {

    @Override
    @Mappings({
            @Mapping(
                    target = "status",
                    ignore = true
            ),
            @Mapping(
                    target = "created",
                    ignore = true
            ),
            @Mapping(
                    target = "updated",
                    ignore = true
            )
    })
    E toEntity(D dto);

}
