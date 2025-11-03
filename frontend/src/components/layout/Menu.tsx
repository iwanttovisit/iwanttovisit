import React, {useContext} from "react";
import {useLocation} from "react-router-dom";
import AccountRowMenu from "../layout/AccountRowMenu";
import MainRowMenu from "../layout/MainRowMenu";
import {Context} from "../../index";
import {PROFILE_URL} from "../../utils/urls";

const Menu = () => {
	const {store} = useContext(Context);
	const location = useLocation();

	if (location.pathname.startsWith(PROFILE_URL) && store.getAuth()) {
		return <AccountRowMenu />;
	}

	return <MainRowMenu />;
};

export default Menu;
