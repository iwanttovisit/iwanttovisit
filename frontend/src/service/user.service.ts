import api from "../http";
import {AxiosResponse} from "axios";
import {IUser} from "../model/user";
import {IMessage, Pageable} from "../model/common";
import {IMap, IMapCriteria} from "../model/map";

export default class UserService {
	static async get(id: string): Promise<AxiosResponse<IUser>> {
		return api.get<IUser>(`/users/${id}`);
	}

	static async update(user: IUser): Promise<AxiosResponse<IUser | IMessage>> {
		return api.put<IUser>(`/users`, user);
	}

	static async getMapsByUserId(id: string, criteria: IMapCriteria): Promise<AxiosResponse<Pageable<IMap>>> {
		return api.get<Pageable<IMap>>(`/users/${id}`, {
			params: criteria
		});
	}

	static async delete(id: string) {
		await api.delete(`/users/${id}`);
	}
}
