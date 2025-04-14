package org.iwanttovisit.iwanttovisit.service;

import org.iwanttovisit.iwanttovisit.model.Place;
import org.iwanttovisit.iwanttovisit.model.criteria.PlaceCriteria;

public interface PlaceService
        extends Blockable, CrudService<Place, PlaceCriteria> {
}
