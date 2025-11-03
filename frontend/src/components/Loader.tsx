import React, {lazy, Suspense} from "react";

const Spin = lazy(() => import("antd/es/spin"));

const Loader = () => {
	return (
		<Suspense fallback={<div></div>}>
			<Spin />
		</Suspense>
	);
};

export default Loader;
