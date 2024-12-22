import React, { memo, useEffect, useState } from "react";
import Cart from "../../components/cart/cart";

import greenIrishTweed from "../../assets/productImages/creed/green-irish-tweed.png";
import diorHommeSport from "../../assets/productImages/dior-homme-sport.png";
import getCart from "../../services/handleGetCartItem";
import handleBase64Decode from "../../components/covertBase64ToImg"

const cartData = [
  { id: 1, name: "Creed Green Irish Tweed", price: 7000000, size: "100ml", quantity: 1, sale: 10, image: greenIrishTweed },
  { id: 2, name: "Dior Homme Sport", price: 250000, size: "10ml", quantity: 2, sale: 15, image: diorHommeSport },
  { id: 3, name: "Product 3", price: 200000, size: "10ml", quantity: 1, sale: 12, image: "https://via.placeholder.com/100" },
  { id: 4, name: "Product 4", price: 500000, size: "10ml", quantity: 1, sale: 10, image: "https://via.placeholder.com/100" },
];

const App = () => {
  // const [products, setProducts] = useState([]);
  // const [loading, setLoading] = useState(true);
  // const [error, setError] = useState(null);

  // useEffect(() => {
  //   getCart()
  //     .then((response) => {
  //       setProducts(response.data);
  //       setLoading(false); // Kết thúc loading
  //     })
  //     .catch((error) => {
  //       console.error(error);
  //       setError('Failed to fetch products'); // Lưu lỗi vào state
  //       setLoading(false);
  //     });
  // }, []);

  // if (loading) return <div>Loading...</div>;

  // // Hiển thị lỗi nếu có
  // if (error) return <div>Error: {error}</div>;

  // console.log(products.result)

  return <Cart initialCartItems={cartData} />;
};

export default memo(App);
