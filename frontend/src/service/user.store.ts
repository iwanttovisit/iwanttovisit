import {IUser} from "../model/user";
import UserService from "./user.service";
import {ErrorCallback, Pageable} from "../model/common";
import {handleErrorOrReturn} from "../utils/storeUtils";
import {IMap, IMapCriteria} from "../model/map";

class UserStore {
	async get(id: string): Promise<IUser> {
		const response = await UserService.get(id);
		return response.data;
	}

	async update(user: IUser, onError: ErrorCallback) {
		const response = await UserService.update(user);
		handleErrorOrReturn(response, onError);
	}

	async getMapsByUserId(id: string, criteria: IMapCriteria): Promise<Pageable<IMap>> {
		const response = await UserService.getMapsByUserId(id, criteria);
		return response.data;
	}

	async delete(id: string, onError: ErrorCallback) {
		const response = await UserService.delete(id);
		return handleErrorOrReturn(response, onError);
	}
}

export const userStore = new UserStore();
