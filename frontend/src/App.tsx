import {lazy, Suspense, useContext, useEffect, useState} from "react";
import {Context} from ".";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import WithLoader from "./components/WithLoader";
import Loader from "./components/Loader";
import Layout, {Content} from "antd/es/layout/layout";
import ScrollToTop from "./components/ScrollToTop";
import Menu from "./components/layout/Menu";
import {LOGIN_URL, PROFILE_URL, REGISTER_CONFIRM_URL, REGISTER_URL, RESTORE_URL} from "./utils/urls";
import {observer} from "mobx-react-lite";
import LandingPage from "./pages/LandingPage";

const Header = lazy(() => import("./components/layout/Header"));
const Login = lazy(() => import("./pages/auth/LoginPage"));
const Register = lazy(() => import("./pages/auth/RegisterPage"));
const RegisterConfirm = lazy(() => import("./pages/auth/RegisterConfirm"));
const PasswordRestore = lazy(() => import("./pages/auth/PasswordRestore"));
const NotFoundPage = lazy(() => import("./pages/404"));
const Profile = lazy(() => import("./pages/profile/ProfilePage"));

function App() {
	const {store} = useContext(Context);
	const [loading, setLoading] = useState<boolean>(true);

	useEffect(() => {
		async function f() {
			setLoading(true);
			await store.checkAuth();
			setLoading(false);
		}

		f();
	}, [store]);

	return (
		<Layout className="layout">
			<WithLoader
				loading={loading}
				delayed={false}
				data={
					<BrowserRouter>
						<Header />
						<Menu />
						<Suspense fallback={<Loader />}>
							<Content>
								<ScrollToTop />
								<Routes>
									<Route
										path={LOGIN_URL}
										element={store.getAuth() ? <Profile /> : <Login />}
									/>
									<Route
										path={REGISTER_URL}
										element={store.getAuth() ? <Profile /> : <Register />}
									/>
									<Route
										path={REGISTER_CONFIRM_URL}
										element={store.getAuth() ? <Profile /> : <RegisterConfirm />}
									/>
									<Route
										path={RESTORE_URL}
										element={store.getAuth() ? <Profile /> : <PasswordRestore />}
									/>
									<Route
										path={PROFILE_URL}
										element={store.getAuth() ? <Profile /> : <Login />}
									/>
									<Route
										path="/"
										element={<LandingPage />}
									/>
									<Route
										path="/404"
										element={<NotFoundPage />}
									/>
								</Routes>
							</Content>
						</Suspense>
					</BrowserRouter>
				}
			/>
		</Layout>
	);
}

export default observer(App);
