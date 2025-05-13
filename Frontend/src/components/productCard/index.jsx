import { memo } from "react";
import "./style.scss";

const ProductCard = ({ id, img, brandName, name, price1, price2 }) => {
    const formatCurrency = (value) => {
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND',
        }).format(value);
    };

    return (
        <a href={`/Product-detail/${id}`} key={id} style={{ textDecoration: "none" }}>
            <div className="product-card">
                <div className="product-image">
                    <img
                        src={require(`../../assets/productImages/${img}`)}  
                        alt={name}
                    />
                </div>
                <div className="product-info">
                    <h3 className="product-brand">{brandName}</h3>
                    <p className="product-name">{name}</p>
                    <p className="product-price">
                        {formatCurrency(price1)} {price2 > price1 ? `- ${formatCurrency(price2)}` : ''}
                    </p>
                </div>
            </div>
        </a>
    );
};

export default memo(ProductCard);