import {IBaseEntity, ICriteria} from "./common";
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

export enum IMapSortType {
	CREATED = "By created",
	UPDATED = "By updated",
	NAME = "By name"
}

export interface IMapCriteria extends ICriteria {
	ipPublic?: boolean;
	author?: string;
	sort?: IMapSortType;
}

export enum IPlaceSortType {
	CREATED = "By created",
	UPDATED = "By updated",
	NAME = "By name",
	RATING = "By rating"
}

export interface IPlaceCriteria extends ICriteria {
	isVisited?: boolean;
	category?: ICategory;
	author?: string;
	map?: string;
	latitude?: number;
	longitude?: number;
	bounds?: number[][];
	sort?: IPlaceSortType;
}
