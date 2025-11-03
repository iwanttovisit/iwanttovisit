import React, {ReactElement, ReactNode} from "react";
import useDelayedLoading from "../utils/loadingHook";
import Loader from "./Loader";

interface WithLoader {
	loading: boolean;
	data: ReactNode;
	loader?: ReactNode;
	delayed?: boolean;
}

const WithLoader: React.FC<WithLoader> = ({loading, data, loader = <Loader />, delayed = true}): ReactElement => {
	const delayedLoading = delayed ? useDelayedLoading(loading) : loading;

	if (delayedLoading) {
		return loader as ReactElement;
	}

	return data as ReactElement;
};

export default WithLoader;
