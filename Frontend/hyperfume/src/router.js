import { ROUTERS } from "./utils/router";
import HomePage from './pages/homePage';
import { Routes, Route } from "react-router-dom";

const renderUserRouter = () => {
    const userRouters = [
        {
            path: ROUTERS.USER.HOME,
            component: <HomePage></HomePage>,
        },
        // {
        //     path: ROUTERS.USER.SIGNIN,
        //     component: <SignInPage></SignInPage>
        // },
        // {
        //     path: ROUTERS.USER.SIGNUP,
        //     component: <SignUpPage></SignUpPage>
        // }
    ]

    return (
        <Routes>
            {
                userRouters.map((item, key) => (
                    <Route key={key} path={item.path} element={item.component} />
                ))
            }
        </Routes>
    )
}

const RouterCustom = () => {
    return renderUserRouter();
};

export default RouterCustom;