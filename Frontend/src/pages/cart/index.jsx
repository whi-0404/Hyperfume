/* eslint-disable no-unused-vars */
import React, { memo, useEffect, useState } from "react";
import Cart from "../../components/cart/cart";

import greenIrishTweed from "../../assets/productImages/creed/green-irish-tweed.png";
import diorHommeSport from "../../assets/productImages/dior-homme-sport.png";
import dior from "../../assets/productImages/dior_sauvage.png";

const cartData = [
  { id: 1, name: "Dior Savage EDP", price: 5100000, size: "100ml", quantity: 1, sale: 10, image: dior },
  { id: 2, name: "Dior Savage EDP", price: 5100000, size: "100ml", quantity: 2, sale: 10, image: dior },

];

const App = () => {
  const [cartItems, setCartItems] = useState([cartData]);
  const [loading, setLoading] = useState(true);

  if (cartItems.length === 0) {
    return <p>Giỏ hàng của bạn đang trống</p>
  }

  return <Cart initialCartItems={cartData} />;
};

export default memo(App);
