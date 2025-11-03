import React from "react";

class Title extends React.Component<{children: React.ReactNode}> {
	render() {
		const {children} = this.props;

		return <h1 className="page-header">{children}</h1>;
	}
}

export default Title;
