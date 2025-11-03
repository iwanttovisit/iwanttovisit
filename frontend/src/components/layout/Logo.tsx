import React from "react";
import {Link} from "react-router-dom";

interface LogoProps {
	width: string;
	height: string;
}

enum Size {
	SMALL = "120px",
	MEDIUM = "200px"
}

const Logo = (props: LogoProps) => {
	if (props.width === Size.SMALL) {
		return (
			<Link
				to="/"
				aria-label="Главная"
			>
				<img
					alt=""
					src="/assets/images/logotype240x120.webp"
					width={props.width}
					height={props.height}
					style={{display: "block"}}
				/>
			</Link>
		);
	}
	if (props.width === Size.MEDIUM) {
		return (
			<Link
				to="/"
				aria-label="Главная"
			>
				<img
					alt=""
					src="/assets/images/logotype200x100.webp"
					width={props.width}
					height={props.height}
					style={{display: "block"}}
				/>
			</Link>
		);
	}
	return (
		<Link to="/">
			<img
				alt=""
				src="/assets/images/logotype.webp"
				width={props.width}
				loading="lazy"
			/>
		</Link>
	);
};

export default Logo;
