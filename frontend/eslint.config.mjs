import globals from "globals";
import pluginJs from "@eslint/js";
import tseslint from "typescript-eslint";
import prettier from "eslint-plugin-prettier";
import pluginReact from "eslint-plugin-react";

export default [
	pluginJs.configs.recommended,
	...tseslint.configs.recommended,
	pluginReact.configs.flat.recommended,
	{
		files: ["**/*.{js,mjs,cjs,ts,jsx,tsx}"],
		languageOptions: {
			globals: globals.browser
		},
		plugins: {
			tseslint,
			prettier
		},
		rules: {
			"prettier/prettier": "error",
			"@typescript-eslint/no-explicit-any": "off",
			"@typescript-eslint/no-empty-object-type": "off",
			"@typescript-eslint/no-unused-expressions": "off",
			"react/prop-types": "off",
			"react/no-unescaped-entities": "off",
			"react/display-name": "off",
		},
		settings: {
			react: {
				version: "detect"
			}
		}
	},
];