export type ErrorCallback = (success: boolean, error?: string, data?: any) => void;

export interface Pageable<T> {
	content: T[];
	totalPages: number;
	totalElements: number;
	numberOfElements: number;
}

export interface IMessage {
	message: string;
	errors: Record<string, string>;
}

export interface IBaseEntity {
	id: string;
	status: IStatus;
	created: Date;
	updated: Date;
}

export enum IStatus {
	ACTIVE = "Active",
	NOT_ACTIVE = "Not active",
	DELETED = "Deleted"
}
