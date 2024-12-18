import { memo } from 'react';
import './style.scss';
import img from '../../assets/image/check-box.png';

const aboutUs = () => {
    return (
        <>
            <div className='instruction-container'>
                <div className="breadcrumb">
                    <a href="/" className="breadcrumb-link">Trang chủ</a>
                    <span className="arrow"> &gt; </span>
                    <a href="/Instruction" className="breadcrumb-link">
                        <span className="current">About Us</span>
                    </a>
                    <hr className="divider" />
                </div>

                <div className='intruct'>
                    <div className='guid'>
                        <h1>Về chúng tôi</h1>
                        <p style={{ width: "70%" }}> Hyperfume là trang thương mại điện tử chuyên cung cấp nước hoa chính hãng từ các thương hiệu nổi tiếng trên thế giới như Chanel, Dior, Tom Ford, Clive Christian, Hermès, và nhiều thương hiệu cao cấp khác. Chúng tôi tự hào mang đến cho khách hàng những sản phẩm chất lượng, đa dạng và phù hợp với mọi phong cách, từ nước hoa nam mạnh mẽ, nước hoa nữ quyến rũ đến các dòng unisex độc đáo.
                            <br /><br />Với giao diện website thân thiện, Hyperfume không chỉ mang đến trải nghiệm mua sắm dễ dàng, tiện lợi mà còn giúp khách hàng tìm kiếm và lựa chọn sản phẩm một cách nhanh chóng. Đội ngũ chuyên gia của chúng tôi luôn sẵn sàng tư vấn, hỗ trợ tận tâm, đảm bảo mỗi khách hàng đều có thể tìm được mùi hương yêu thích và phù hợp nhất.
                            Sứ mệnh của Hyperfume là kết nối bạn với thế giới của những mùi hương đẳng cấp, giúp tôn lên cá tính và phong cách riêng. Hyperfume cam kết mang đến những sản phẩm chính hãng với mức giá cạnh tranh cùng chính sách mua hàng minh bạch và dịch vụ hỗ trợ 24/7.
                            Hãy để Hyperfume đồng hành cùng bạn trên hành trình khám phá những mùi hương tinh tế và đẳng cấp!</p>
                    </div>

                </div >
            </div >
        </>
    );
};

export default memo(aboutUs);

