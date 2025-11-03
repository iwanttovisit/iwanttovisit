import {useEffect, useState} from "react";

const useDelayedLoading = (loading: boolean, delay: number = 500) => {
	const [delayedLoading, setDelayedLoading] = useState<boolean>(false);
	const loadingTimeout = 300;

	useEffect(() => {
		let timer: NodeJS.Timeout;

		if (loading) {
			setDelayedLoading(true);
		} else {
			timer = setTimeout(() => {
				setDelayedLoading(false);
			}, loadingTimeout);
		}

		return () => {
			if (timer) clearTimeout(timer);
		};
	}, [loading, delay]);

	return delayedLoading;
};

export default useDelayedLoading;
