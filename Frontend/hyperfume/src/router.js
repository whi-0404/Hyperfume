import { ROUTERS } from "./utils/router";
import HomePage from "./pages/homePage";
import { Routes, Route } from "react-router-dom";
import MasterLayout from "./pages/theme/masterLayout";
import NuochoaNam from "./pages/nuochoaNam";

const renderUserRouter = () => {
    const userRouters = [
        {
            path: ROUTERS.USER.HOME,
            component: <HomePage />
        },
        {
            path: ROUTERS.USER.NUOCHOANAM,
            component: <NuochoaNam />
        },
    ]

    return (
        <MasterLayout>
            <Routes>
                {
                    userRouters.map((item, key) => (
                        <Route key={key} path={item.path} element={item.component} />
                ))}
            </Routes>
        </MasterLayout>
    )
}

const RouterCustom = () => {
    return renderUserRouter();
};

export default RouterCustom;

