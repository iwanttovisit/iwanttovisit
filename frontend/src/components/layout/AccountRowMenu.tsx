import React, {useContext} from "react";
import Row from "antd/es/row";
import Col from "antd/es/col";
import {Link, useNavigate} from "react-router-dom";
import {LOGIN_URL, PROFILE_URL} from "../../utils/urls";
import {Context} from "../../index";
import Icon from "../Icon";

const AccountRowMenu = () => {
	const {store} = useContext(Context);
	const navigate = useNavigate();

	const logout = async () => {
		await store.logout();
		navigate(LOGIN_URL);
	};

	return (
		<Row gutter={[5, 10]}>
			<Col>
				<Link
					className="row-menu-item"
					to={PROFILE_URL}
				>
					<Icon
						name="flowbite:user-outline"
						width="30px"
					/>
					Профиль
				</Link>
			</Col>
			<Col>
				<p
					className="row-menu-item"
					onClick={logout}
				>
					<span>Выйти</span>
				</p>
			</Col>
		</Row>
	);
};

export default AccountRowMenu;
