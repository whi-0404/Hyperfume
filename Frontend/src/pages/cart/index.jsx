import React, { memo, useEffect, useState } from "react";
import Cart from "../../components/cart/cart";

import greenIrishTweed from "../../assets/productImages/creed/green-irish-tweed.png";
import diorHommeSport from "../../assets/productImages/dior-homme-sport.png";

const cartData = [
  { id: 1, name: "Creed Green Irish Tweed", price: 7000000, size: "100ml", quantity: 1, sale: 10, image: greenIrishTweed },
  { id: 2, name: "Dior Homme Sport", price: 250000, size: "10ml", quantity: 2, sale: 15, image: diorHommeSport },
  { id: 3, name: "Product 3", price: 200000, size: "10ml", quantity: 1, sale: 12, image: "https://via.placeholder.com/100" },
  { id: 4, name: "Product 4", price: 500000, size: "10ml", quantity: 1, sale: 10, image: "https://via.placeholder.com/100" },
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
