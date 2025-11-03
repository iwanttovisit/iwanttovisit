import {IBaseEntity} from "./common";

export interface IUser extends IBaseEntity {
	username: string;
	name: string;
	password: string;
	lastSeen: Date;
}

export interface IPrivateUser extends IBaseEntity {
	name: string;
}
