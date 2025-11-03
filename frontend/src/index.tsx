import {createContext, lazy, Suspense} from "react";
import "./index.css";
import Store from "./service/store";
import {createRoot} from "react-dom/client";

const store = new Store();

const App = lazy(() => import("./App"));

export const Context = createContext({
	store
});

const app = (
	<Suspense fallback={<div />}>
		<Context.Provider value={{store}}>
			<App />
		</Context.Provider>
	</Suspense>
);

createRoot(document.getElementById("root")!).render(app);
