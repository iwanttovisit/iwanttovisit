import AuthService from "./auth.service";
import {makeAutoObservable} from "mobx";
import {authStore} from "./auth.store";
import {ErrorCallback} from "../model/common";
import {IUser} from "../model/user";
import UserService from "./user.service";

export default class Store {
	private user: IUser = {} as IUser;
	private isAuth: boolean = false;

	constructor() {
		makeAutoObservable(this);
	}

	setAuth(auth: boolean) {
		this.isAuth = auth;
	}

	getAuth() {
		return this.isAuth;
	}

	setUser(user: IUser) {
		this.user = user;
	}

	getUser(): IUser {
		return this.user;
	}

	async login(request: IAuthRequest, onError: ErrorCallback) {
		const modifiedCallback: ErrorCallback = async (success, error?, response?: IAuthResponse) => {
			if (success) {
				localStorage.setItem("token", response!.token);
				localStorage.setItem("refreshToken", response!.refreshToken);
				await this.setUserFromDataBase(response!.userId);
				this.setAuth(true);
			}
			onError(success, error);
		};
		await authStore.login(request, modifiedCallback);
	}

	async logout() {
		localStorage.removeItem("token");
		localStorage.removeItem("refreshToken");
		this.setUser({} as IUser);
		this.setAuth(false);
	}

	async checkAuth() {
		const refreshToken = localStorage.getItem("refreshToken");
		if (refreshToken) {
			const response = await AuthService.refresh(refreshToken);
			localStorage.setItem("token", response.data.token);
			localStorage.setItem("refreshToken", response.data.refreshToken);
			if (response.data.token) {
				await this.setUserFromDataBase(response.data.userId);
				this.setAuth(true);
			} else {
				localStorage.removeItem("token");
				localStorage.removeItem("refreshToken");
			}
		}
	}

	private async setUserFromDataBase(userId: string) {
		const response = await UserService.get(userId);
		if (response) {
			this.setUser(response.data);
		}
	}
}
