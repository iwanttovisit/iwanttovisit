import {handleErrorOrReturn} from "../utils/storeUtils";
import AuthService from "./auth.service";
import {ErrorCallback} from "../model/common";
import {IUser} from "../model/user";

class AuthStore {
	async login(request: IAuthRequest, onError: ErrorCallback): Promise<IAuthResponse> {
		const response = await AuthService.login(request);
		return handleErrorOrReturn(response, onError);
	}

	async register(user: IUser, onError: ErrorCallback) {
		const response = await AuthService.register(user);
		handleErrorOrReturn(response, onError);
	}

	async resetPassword(reset: IPasswordReset, onError: ErrorCallback) {
		const response = await AuthService.resetPassword(reset);
		handleErrorOrReturn(response, onError);
	}

	async activate(token: string, onError: ErrorCallback) {
		const response = await AuthService.activate(token);
		handleErrorOrReturn(response, onError);
	}

	async sendResetPasswordEmail(email: string) {
		await AuthService.sendResetPasswordEmail(email);
	}
}

export const authStore = new AuthStore();
