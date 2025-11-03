import React, {Suspense} from "react";
import {observer} from "mobx-react-lite";
import Navbar from "./Navbar";
import HeaderLoader from "./HeaderLoader";
import Logo from "./Logo";

const Header = () => {
	return (
		<Suspense fallback={<HeaderLoader />}>
			<Navbar
				logo={
					<Logo
						width="120px"
						height="60px"
					/>
				}
			/>
		</Suspense>
	);
};

export default observer(Header);
