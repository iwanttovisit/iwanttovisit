import React from "react";

interface PageProps {
	children: React.ReactNode;
	row?: boolean;
}

class Page extends React.Component<PageProps> {
	render() {
		const {children, row} = this.props;

		return <div className={`page ${row ? "row" : ""}`}>{children}</div>;
	}
}

export default Page;
