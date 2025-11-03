import {AxiosResponse} from "axios";
import api from "../http";
import {IMap, IMapCriteria, IPlace, IPlaceCriteria} from "../model/map";
import {IMessage, Pageable} from "../model/common";

export default class MapService {
	static async get(id: string): Promise<AxiosResponse<IMap>> {
		return api.get<IMap>(`/maps/${id}`);
	}

	static async getAllByCriteria(criteria: IMapCriteria): Promise<AxiosResponse<Pageable<IMap>>> {
		return api.get<Pageable<IMap>>(`/maps`, {
			params: criteria
		});
	}

	static async getPlacesByMapId(id: string, criteria: IPlaceCriteria): Promise<AxiosResponse<Pageable<IPlace>>> {
		return api.get<Pageable<IPlace>>(`/maps/${id}/places`, {
			params: {
				criteria
			}
		});
	}

	static async update(map: IMap): Promise<AxiosResponse<IMap | IMessage>> {
		return api.put<IMap>(`/maps`, map);
	}

	static async create(map: IMap): Promise<AxiosResponse<IMap | IMessage>> {
		return api.post<IMap>(`/maps`, map);
	}

	static async delete(id: string) {
		return api.delete(`/maps/${id}`);
	}
}
