import {IBaseEntity} from "./common";
import {IPrivateUser} from "./user";

export interface IMap extends IBaseEntity {
	name: string;
	description: string;
	author: IPrivateUser;
	isPublic: boolean;
}

export interface IPlace extends IBaseEntity {
	name: string;
	description: string;
	coordinates: string;
	url: string;
	rating: number;
	category: ICategory;
	isVisited: boolean;
	map: IMap;
	author: IPrivateUser;
}

export enum ICategory {
	CAFE = "Cafe",
	HOBBY = "Hobby",
	SPORT = "Sport",
	ENTERTAINMENT = "Entertainment",
	NATURE = "Nature"
}
