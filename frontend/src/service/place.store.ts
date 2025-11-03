import {ErrorCallback} from "../model/common";
import {handleErrorOrReturn} from "../utils/storeUtils";
import {IPlace} from "../model/map";
import PlaceService from "./place.service";

class PlaceStore {
	async get(id: string): Promise<IPlace> {
		const response = await PlaceService.get(id);
		return response.data;
	}

	async update(place: IPlace, onError: ErrorCallback) {
		const response = await PlaceService.update(place);
		handleErrorOrReturn(response, onError);
	}

	async create(place: IPlace, onError: ErrorCallback) {
		const response = await PlaceService.create(place);
		handleErrorOrReturn(response, onError);
	}

	async delete(id: string, onError: ErrorCallback) {
		const response = await PlaceService.delete(id);
		return handleErrorOrReturn(response, onError);
	}
}

export const placeStore = new PlaceStore();
