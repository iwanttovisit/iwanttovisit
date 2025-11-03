import React from "react";
import Navbar from "./Navbar";
import Skeleton from "antd/es/skeleton";

const HeaderLoader = () => {
	return (
		<Navbar
			logo={
				<Skeleton.Avatar
					active
					style={{width: "120px", height: "60px"}}
					shape="square"
				/>
			}
		/>
	);
};

export default HeaderLoader;
