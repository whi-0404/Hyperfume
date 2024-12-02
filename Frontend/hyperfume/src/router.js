import { ROUTERS } from "./utils/router";
import HomePage from "./pages/homePage";
import { Routes, Route } from "react-router-dom";
import MasterLayout from "./pages/theme/masterLayout";
import NuochoaNam from "./pages/nuochoaNam";
import NuochoaNu from "./pages/nuochoaNu";
import NuochoaUnisex from "./pages/nuochoaUnisex";
import FlashSale from "./pages/flash_Sale";
import ThanhToan from "./pages/thanhToan";





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
        {
            path: ROUTERS.USER.NUOCHOANU,
            component: <NuochoaNu />
        },
        {
            path: ROUTERS.USER.NUOCHOAUNISEX,
            component: <NuochoaUnisex />
        },
        {
            path: ROUTERS.USER.FLASHSALE,
            component: <FlashSale />
        },
        {
            path: ROUTERS.USER.THANHTOAN,
            component: <ThanhToan />
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

