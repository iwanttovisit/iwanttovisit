package org.iwanttovisit.iwanttovisit.service.impl;

import org.iwanttovisit.iwanttovisit.model.Place;
import org.iwanttovisit.iwanttovisit.model.criteria.PlaceCriteria;
import org.iwanttovisit.iwanttovisit.service.Blockable;
import org.iwanttovisit.iwanttovisit.service.CrudService;

public interface PlaceService
        extends Blockable, CrudService<Place, PlaceCriteria> {
}
