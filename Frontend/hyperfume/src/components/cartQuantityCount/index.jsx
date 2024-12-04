import React from "react";

const TotalQuantity = ({ items }) => {
    if (!items || items.length === 0) {
        return <span>0 sản phẩm</span>; // Xử lý khi không có sản phẩm
    }

    const calculateTotalQuantity = (items) => {
        return items.reduce((total, item) => total + item.quantity, 0);
    };

    const totalQuantity = calculateTotalQuantity(items);

    return (
        <span>
            {totalQuantity}
        </span>
    );
};

export default TotalQuantity;
