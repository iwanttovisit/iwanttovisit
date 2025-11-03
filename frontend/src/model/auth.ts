interface IAuthRequest {
	username: string;
	password: string;
}

interface IAuthResponse {
	userId: string;
	username: string;
	token: string;
	refreshToken: string;
}

interface IRefreshRequest {
	refreshToken: string;
}

interface IPasswordReset {
	token: string;
	newPassword: string;
}
