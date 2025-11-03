import React from "react";
import Row from "antd/es/row";
import Col from "antd/es/col";
import {Link} from "react-router-dom";
import {PROFILE_URL} from "../../utils/urls";
import Icon from "../Icon";

const MainRowMenu = () => {
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
					Profile
				</Link>
			</Col>
		</Row>
	);
};

export default MainRowMenu;
