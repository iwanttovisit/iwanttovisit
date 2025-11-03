import {ErrorCallback} from "../model/common";

export function handleErrorOrReturn(response: any, onError: ErrorCallback): any | null {
	if (response.data && "message" in response.data) {
		const errorMessage = response.data.message;
		const errorsMessages = response.data.errors ? `\n${Object.values(response.data.errors).join("\n")}` : "";
		onError(false, errorMessage + errorsMessages);
		return null;
	}
	onError(true, undefined, response.data);
	return response.data;
}
