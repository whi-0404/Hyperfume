import { memo } from "react";
import "./style.scss";

const ProductCard = ({ img, brandName, name, price1, price2 }) => {
    const formatCurrency = (value) => {
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND',
        }).format(value);
    };

    return (
        <>
            <div className="product-card">
                <div className="product-image">
                    <img
                        src={img}
                        alt={name}
                    />
                </div>
                <div className="product-info">
                    <h3 className="product-brand">{brandName}</h3>
                    <p className="product-name">{name}</p>
                    <p className="product-price">{formatCurrency(price1)} - {formatCurrency(price2)}</p>
                </div>
            </div>
        </>
    );
};

export default memo(ProductCard);
