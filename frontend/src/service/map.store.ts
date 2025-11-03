import {ErrorCallback, Pageable} from "../model/common";
import {handleErrorOrReturn} from "../utils/storeUtils";
import {IMap, IMapCriteria, IPlace, IPlaceCriteria} from "../model/map";
import MapService from "./map.service";

class MapStore {
	async get(id: string): Promise<IMap> {
		const response = await MapService.get(id);
		return response.data;
	}

	async update(map: IMap, onError: ErrorCallback) {
		const response = await MapService.update(map);
		handleErrorOrReturn(response, onError);
	}

	async create(map: IMap, onError: ErrorCallback) {
		const response = await MapService.create(map);
		handleErrorOrReturn(response, onError);
	}

	async getAllByCriteria(criteria: IMapCriteria): Promise<Pageable<IMap>> {
		const response = await MapService.getAllByCriteria(criteria);
		return response.data;
	}

	async getPlacesByMapId(id: string, criteria: IPlaceCriteria): Promise<Pageable<IPlace>> {
		const response = await MapService.getPlacesByMapId(id, criteria);
		return response.data;
	}

	async delete(id: string, onError: ErrorCallback) {
		const response = await MapService.delete(id);
		return handleErrorOrReturn(response, onError);
	}
}

export const mapStore = new MapStore();
