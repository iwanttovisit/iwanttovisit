import {AxiosResponse} from "axios";
import {IMessage} from "../model/common";
import api from "../http";
import {IUser} from "../model/user";

export default class AuthService {
	static async login(request: IAuthRequest): Promise<AxiosResponse<IAuthResponse | IMessage>> {
		return api.post<IAuthResponse>("/auth/login", request);
	}

	static async refresh(refreshToken: string): Promise<AxiosResponse<IAuthResponse>> {
		return api.post<IAuthResponse>("/auth/refresh", {refreshToken});
	}

	static async register(user: IUser): Promise<AxiosResponse<IUser | IMessage>> {
		return api.post<IUser>("/auth/register", user);
	}

	static async activate(token: string): Promise<AxiosResponse<IMessage>> {
		return api.post<IMessage>("/auth/activate", token, {
			headers: {
				"Content-Type": "text/plain"
			}
		});
	}

	static async sendResetPasswordEmail(email: string) {
		return api.post("/auth/forget", email, {
			headers: {
				"Content-Type": "text/plain"
			}
		});
	}

	static async resetPassword(reset: IPasswordReset): Promise<AxiosResponse<IMessage>> {
		return api.post<IMessage>("/auth/password/restore", reset);
	}
}
