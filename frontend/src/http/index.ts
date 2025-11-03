import axios from "axios";
import AuthService from "../service/auth.service";

export const API_URL = import.meta.env.VITE_API_URL;

const api = axios.create({
	baseURL: API_URL
});

api.interceptors.request.use(config => {
	config.headers!.Authorization = localStorage.getItem("token") ? `Bearer ${localStorage.getItem("token")}` : "";
	return config;
});

api.interceptors.response.use(
	config => {
		return config;
	},
	async error => {
		const originalRequest = error.config;
		if (error.response.status === 403 && error.config && !error.config._isRetry) {
			originalRequest._isRetry = true;
			const refreshToken = localStorage.getItem("refreshToken");
			if (refreshToken) {
				const response = await AuthService.refresh(refreshToken);
				if (response.status === 200) {
					localStorage.setItem("refreshToken", response.data.refreshToken);
					localStorage.setItem("token", response.data.token);
				} else {
					localStorage.removeItem("refreshToken");
					localStorage.removeItem("token");
				}
				return api.request(originalRequest);
			}
		}
		if (error.response.status === 404) {
			window.location.href = "/404";
		}
		if (error.response.status === 429) {
			window.location.href = "/429";
		}
		if (error.response.status === 400) {
			return error.response;
		}
	}
);

export default api;
