import { ROUTERS } from "./utils/router";
import { Routes, Route } from "react-router-dom";
import HomePage from "./pages/homePage";
import MasterLayout from "./pages/themes/masterLayout";
import SignIn from "./pages/signIn";
import SignUp from "./pages/signUp";
import Instruction from "./pages/instruction";
import Cart from "./pages/cart";
import ForgotPass from "./pages/forgotPass";
import ResetPass from "./pages/resetPass";
import ProductDetail from "./pages/productDetail";
import Nuochoa from "./pages/nuochoa";
import NuochoaNam from "./pages/nuochoaNam";
import NuochoaNu from "./pages/nuochoaNu";
import NuochoaUnisex from "./pages/nuochoaUnisex";
import FlashSale from "./pages/flash_Sale";
import ThanhToan from "./pages/thanhToan";
import ProductConsult from "./pages/productConsult";
import AboutUs from "./pages/aboutUs";
import BlogPage from "./pages/blog";
import LoadSearchProduct from "./pages/loadSearchProduct";
import Profile from "./pages/profile"

const renderUserRouter = () => {
    const userRouters = [
        {
            path: ROUTERS.USER.HOME,
            component: <HomePage />
        },
        {
            path: ROUTERS.USER.NUOCHOA,
            component: <Nuochoa />
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
        {
            path: ROUTERS.USER.SIGNIN,
            component: <SignIn></SignIn>
        },
        {
            path: ROUTERS.USER.SIGNUP,
            component: <SignUp></SignUp>
        },
        {
            path: ROUTERS.USER.CART,
            component: <Cart></Cart>
        },
        {
            path: ROUTERS.USER.INSTRUCTION,
            component: <Instruction></Instruction>
        },
        {
            path: ROUTERS.USER.FORGOTPASS,
            component: <ForgotPass></ForgotPass>
        },
        {
            path: ROUTERS.USER.RESETPASS,
            component: <ResetPass></ResetPass>
        },
        {
            path: ROUTERS.USER.PRODUCT_DETAIL,
            component: <ProductDetail></ProductDetail>
        },
        {
            path: ROUTERS.USER.CONSULT,
            component: <ProductConsult></ProductConsult>
        },
        {
            path: ROUTERS.USER.ABOUT,
            component: <AboutUs></AboutUs>
        },
        {
            path: ROUTERS.USER.BLOG,
            component: <BlogPage></BlogPage>
        },
        {
            path: ROUTERS.USER.SEARCH,
            component: <LoadSearchProduct></LoadSearchProduct>
        },
        {
            path: ROUTERS.USER.PROFILE,
            component: <Profile></Profile>
        }
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

