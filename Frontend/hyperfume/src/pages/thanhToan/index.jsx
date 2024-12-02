import { memo } from "react";
import './style.scss';

const ThanhToan = () => {
    return (
      
      <div className="breadcrumb">
        <span>Trang chủ</span>
      <span className="arrow"> &gt; </span>
        <span>Sản phẩm</span>
      <span className="arrow"> &gt; </span>
        <span className="current">Thanh toán</span>
      <hr className="divider" />
    </div>



      
    );
};

export default memo(ThanhToan);