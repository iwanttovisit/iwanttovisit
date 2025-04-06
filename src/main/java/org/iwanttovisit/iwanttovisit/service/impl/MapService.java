package org.iwanttovisit.iwanttovisit.service.impl;

import org.iwanttovisit.iwanttovisit.model.Map;
import org.iwanttovisit.iwanttovisit.model.criteria.MapCriteria;
import org.iwanttovisit.iwanttovisit.service.Blockable;
import org.iwanttovisit.iwanttovisit.service.CrudService;

public interface MapService extends Blockable, CrudService<Map, MapCriteria> {
}
