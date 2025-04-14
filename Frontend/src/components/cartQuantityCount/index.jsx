import React from "react";

const CartQuantityCount = ({ items }) => {
    if (!items || items.length === 0) {
        return 0; // Return just the number for better integration with the parent component
    }

    const calculateTotalQuantity = (items) => {
        return items.reduce((total, item) => total + item.quantity, 0);
    };

    const totalQuantity = calculateTotalQuantity(items);

    return totalQuantity;
};

export default CartQuantityCount;