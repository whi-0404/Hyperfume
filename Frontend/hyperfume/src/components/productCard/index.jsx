// import { memo } from "react";
// import "./style.scss";

// const ProductCard = () => {
//     return (
//         <>
//             <div className="container_product_card">
//                 <div className="product_card">

//                 </div>
//             </div>
//         </>
//     );
// };

// export default memo(ProductCard);

import { memo } from "react";
import "./style.scss";

const ProductCard = ({ src, brandName, name, price }) => {
    return (
        <div className="product-card">
            <div className="product-image">
                <img
                    src={src}
                    alt={name}
                />
            </div>
            <div className="product-info">
                <h3 className="product-brand">{brandName}</h3>
                <p className="product-name">{name}</p>
                <p className="product-price">{price}</p>
            </div>
        </div>
    );
};

export default memo(ProductCard);
