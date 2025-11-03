import React, {lazy, Suspense} from "react";
import Loader from "./Loader";

const Icon = lazy(() => import("@iconify/react").then(module => ({default: module.Icon})));

interface IconProps {
	name: string;
	width?: string;
}

const LazyIcon = (props: IconProps) => {
	return (
		<Suspense fallback={<Loader />}>
			<Icon
				icon={props.name}
				width={props.width}
			/>
		</Suspense>
	);
};

export default LazyIcon;
