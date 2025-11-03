import {AxiosResponse} from "axios";
import api from "../http";
import {IMessage} from "../model/common";
import {IPlace} from "../model/map";

export default class PlaceService {
	static async get(id: string): Promise<AxiosResponse<IPlace>> {
		return api.get<IPlace>(`/places/${id}`);
	}

	static async update(place: IPlace): Promise<AxiosResponse<IPlace | IMessage>> {
		return api.put<IPlace>(`/places`, place);
	}

	static async create(place: IPlace): Promise<AxiosResponse<IPlace | IMessage>> {
		return api.post<IPlace>(`/places`, place);
	}

	static async delete(id: string) {
		return api.delete(`/places/${id}`);
	}
}
