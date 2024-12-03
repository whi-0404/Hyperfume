import React, { memo } from "react";
import Cart from "../../components/cart/cart";

import greenIrishTweed from "../../assets/productImages/green-irish-tweed.png";
import diorHommeSport from "../../assets/productImages/dior-homme-sport.png"

const App = () => {
    const cartData = [
        { id: 1, name: "Creed Green Irish Tweed", price: 100000, quantity: 1, image: greenIrishTweed },
        { id: 2, name: "Dior Homme Sport", price: 150000, quantity: 2, image: diorHommeSport },
        { id: 3, name: "Product 3", price: 200000, quantity: 1, image: "https://via.placeholder.com/100" },
        { id: 4, name: "Product 4", price: 500000, quantity: 1, image: "https://via.placeholder.com/100" },
    ];

    return <Cart initialCartItems={cartData} />;
};

export default memo(App);
