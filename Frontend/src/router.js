import { ROUTERS } from "./utils/router";
import { Routes, Route } from "react-router-dom";
import MasterLayout from "./pages/themes/masterLayout";

import HomePage from './pages/homePage';
import SignIn from "./pages/signIn";
import SignUp from "./pages/signUp";
import Instruction from "./pages/instruction";
import Cart from "./pages/cart";
import ProductDetail from "./pages/productDetail";

const renderUserRouter = () => {
    const userRouters = [
        {
            path: ROUTERS.USER.HOME,
            component: <HomePage></HomePage>,
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
            path: ROUTERS.USER.PRODUCT_DETAIL,
            component: <ProductDetail></ProductDetail>
        }
    ]

    return (
        <MasterLayout>
            <Routes>
                {
                    userRouters.map((item, key) => (
                        <Route key={key} path={item.path} element={item.component} />
                    ))
                }
            </Routes>
        </MasterLayout>
    )
}

const RouterCustom = () => {
    return renderUserRouter();
};

export default RouterCustom;