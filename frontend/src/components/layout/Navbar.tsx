import React from "react";

interface NavbarProps {
	logo?: React.ReactNode;
	items?: React.ReactNode[];
}

class Navbar extends React.Component<NavbarProps> {
	render() {
		const {logo, items} = this.props;

		return (
			<nav className="navbar">
				<div className="navbar-logo">{logo}</div>
				<div className="navbar-menu">
					{items?.map((item: any, index: number) => (
						<div key={index}>{item}</div>
					))}
				</div>
			</nav>
		);
	}
}

export default Navbar;
