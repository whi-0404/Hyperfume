import { memo } from 'react';
import './style.scss';
import img from '../../assets/image/check-box.png';

const intruct = () => {
    return (
        <>
            <div className='container'>
                <div className='intruct'>
                    <div className='guid'>
                        <h1>Hướng dẫn mua hàng</h1>
                        <h2>Các bước mua hàng</h2>
                        <ul>
                            <li><b>B1:</b> Đầu tiên, nhấn vào sản phẩm bạn muốn mua.</li>
                            <li><b>B2:</b> Nếu muốn mua nhiều sản phẩm: nhấn vào nút “<b>Thêm vào giỏ hàng</b>”,
                                sau đó sản phẩm sẽ được thêm vào giỏ hàng của  bạn. Hoặc nhấn nút “<b>Mua ngay</b>” nếu chỉ muốn mua sản phẩm đó.</li>
                            <li><b>B3:</b> Nếu nhấn nút “<b>Mua ngay</b>” thì có thể bỏ qua bước này. Trong phần giỏ hàng của bạn sẽ hiển thị các thông tin: Tên và ảnh sản phẩm, đơn giá, số lượng, khuyến mãi của từng sản phẩm và thành tiền.
                                Bên tay phải của phần sản phẩm sẽ có các ô vuông như này <img src={img} alt="" />,
                                tick vào ô những sản phẩm mà bạn muốn mua. Sau khi chọn xong, nhấn vào nút “<b>Thanh toán</b>” để đi đến bước thanh toán.</li>
                            <li><b>B4:</b> Khi đến bước thanh toán, bạn điền các thông tin cá nhân như họ tên, địa chỉ nhận hàng, số điện thoại, mã giảm giá (nếu có). Sau đó chọn phương thức thanh toán bạn mong muốn. Cuối cùng là nhấn đặt hàng.</li>
                        </ul>
                    </div>

                    <div className='policy'>
                        <h1>Chính sách</h1>

                        <div className='Warranty'>
                            <h2>Chính sách bảo hành</h2>
                            <ul>
                                <li>Cùng với cam kết bán hàng chính hãng, Hyperfume cam kết hoàn tiền và bồi thường nếu KH chứng minh Hyperfume bán hàng giả.</li>
                                <li>Sản phẩm nước hoa sẽ được bảo hành mùi hương trong vòng 15 ngày.</li>
                            </ul>
                        </div>

                        <div className='warrantyCondition'>
                            <h2>Điều kiện bảo hành:</h2>
                            <ul>
                                <li>Có Phiếu Thu Kiêm Phiếu Bảo Hành trong thời hạn 15 ngày tính từ ngày in trên phiếu.</li>
                                <li>Sản phẩm còn nguyên vẹn không bể, nứt, trầy xước, không hao hụt quá 5% nước trong chai,
                                    không bị tác động can thiệp bên ngoài, sản phẩm còn tem chống giả, còn hộp nguyên vẹn không móp, rách, trầy xước.</li>
                                <li>Khách hàng đã sử dụng và bảo quản đúng theo hướng dẫn.</li>
                                <li>Sản phẩm là nước hoa có vòi xịt cố định trên chai.</li>
                            </ul>
                        </div>

                        <hr />

                        <div className='return'>
                            <h2>Chính sách đổi trả</h2>
                            <p><b>Thời hạn đổi trả:</b> 90 ngày kể từ ngày mua hàng ở bất kì cửa hàng nào của hệ thống Hyperfume.
                                (Lưu ý: Chỉ đổi sản phẩm 1 lần, vui lòng đổi hàng sau 12h trưa).</p>

                            <ol>
                                <li>Điều kiện đổi hàng:
                                    <ul>
                                        <li>Có Phiếu Thu Kiêm Phiếu Bảo Hành trong thời hạn 90 ngày tính từ ngày in trên phiếu.</li>
                                        <li>Sản phẩm còn nguyên vẹn không bể, nứt, trầy xước, không bị tác động can thiệp bên ngoài, sản phẩm còn tem chống giả,
                                            còn hộp nguyên vẹn (bao gồm hộp bên ngoài, hộp chống sốc bên trong và bao kiếng bên ngoài nếu có) không móp, rách, trầy xước.</li>
                                        <li>Khách hàng đã sử dụng và bảo quản đúng theo hướng dẫn.</li>
                                        <li>Sản phẩm có vòi xịt cố định trên chai.</li>
                                    </ul>
                                </li>

                                <li>Phí đổi hàng:
                                    <ul>
                                        <li>Thu phí 20% trên giá mua đối với hàng đã mở nắp và sử dụng.</li>
                                        <li>Không thu phí đối với trường hợp khách hàng chưa sử dụng còn nguyên vẹn.</li>
                                    </ul>
                                </li>

                                <li>Sản phẩm đổi lại là sản phẩm có giá trị bằng hoặc cao hơn giá trị thu hồi sản phẩm đã mua.
                                    Trường hợp sản phẩm đổi lại có giá trị cao hơn giá trị thu hồi sản phẩm đã mua thì khách hàng có trách nhiệm bù số tiền chênh lệch.</li>
                            </ol>
                        </div>

                        <div className='returnProduct'>
                            <h2>Trả hàng</h2>
                            <p style={{ marginTop: 0 }}>Sản phẩm mua rồi, khách hàng vui lòng không trả hàng nếu không phải lỗi của công ty.</p>
                            <p style={{ marginBottom: 0 }}>Các trường hợp từ chối bảo hành/đổi trả hàng:</p>

                            <ul>
                                <li>Sản phẩm không còn nguyên vẹn (chai và hộp) và đã sử dụng quá 5% nước trong chai,
                                    không còn tem bảo hành hoặc tem bảo hành bị rách, biến dạng,  không còn phiếu thu kiêm phiếu bảo hành</li>
                                <li>Quá thời hạn bảo hành quy định.</li>
                                <li>Không sử dụng và bảo quản nước hoa đúng theo hướng dẫn.</li>
                                <li>Lỗi chai/vòi xịt do nhà sản xuất</li>
                                <li>Không bảo hành đối với sản phẩm nước hoa dạng lăn, không có vòi xịt cố định trên chai và xịt toàn thân, lăn khử mùi, sữa tắm, dưỡng thể, và nước hoa xe hơi …</li>
                            </ul>
                        </div>

                        <hr />

                        <div className='payment'>
                            <h2>Chính sách thanh toán</h2>
                            <ul>
                                <li>Thánh toán trực tuyến</li>
                                <li>Quý khách có thể thực hiện thanh toán bằng cách chuyển khoản vào tài khoản sau đây: <br />
                                    - Chủ tài khoản: Công ty TNHH Hyperfume Việt Nam <br />
                                    - Số tài khoản: XXXXXX <br />
                                    - Ngân hàng: Ngan hang XX <br />
                                    - Nội dung: Mã số đơn hàng + SL (VD: CHRISTMAS BOX 123)</li>
                                <li>Thanh toán tiền mặt khi nhận hàng (COD).</li>
                                <li>Đối với một số khu vực theo quy định, Quý khách có thể thực hiện thanh toán bằng tiền mặt tại điểm giao nhận hàng.
                                    Quý khách vui lòng thanh toán cho bên giao hàng toàn bộ hoặc một phần (nếu đã đặt cọc) giá trị hàng hóa đã mua + phí ship (nếu có).</li>
                            </ul>
                        </div>
                    </div>
                </div >
            </div >
        </>
    );
};

export default memo(intruct);