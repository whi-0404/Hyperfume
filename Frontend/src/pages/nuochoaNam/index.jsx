import { memo, useState, useEffect } from "react";
import { toByteArray } from 'base64-js';
import './style.scss';
import { maleProducts } from "../../services/maleProducts";
import ProductCard from "../../components/productCard";

const handleBase64Decode = (base64String) => {
    try {
        // Loại bỏ tiền tố nếu có
        const base64 = base64String.split(',')[1] || base64String;

        // Decode Base64 thành Uint8Array
        const byteArray = toByteArray(base64);

        // Tạo Blob từ Uint8Array
        const blob = new Blob([byteArray], { type: 'image/png' });

        // Tạo URL từ Blob
        const imageUrl = URL.createObjectURL(blob);

        return imageUrl;
    } catch (error) {
        console.error('Error decoding Base64:', error.message);
    }
};

const NuocHoaNam = () => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [sortOption, setSortOption] = useState("latest");
    const [filters, setFilters] = useState({
        fragrance: '',
        brand: '',
        concentration: '',
        fragranceGroup: '',
        maxPrice: ''
    });
    const [currentPage, setCurrentPage] = useState(1);
    const productsPerPage = 12; // Show 12 products per page

    useEffect(() => {
        maleProducts()
            .then((response) => {
                setProducts(response.data);
                setLoading(false); // Kết thúc loading
            })
            .catch((error) => {
                console.error(error);
                setError('Failed to fetch products'); // Lưu lỗi vào state
                setLoading(false);
            });
    }, []);

    if (loading) return <div>Loading...</div>;

    // Hiển thị lỗi nếu có
    if (error) return <div>Error: {error}</div>;

    const handleFilterChange = (e) => {
        setFilters({ ...filters, [e.target.name]: e.target.value });
        setCurrentPage(1); // Reset to first page when filters change
    };

    // Filtering function
    const filteredPerfumes = products.result.filter(perfume => {
        let price = Math.max(
            ...perfume.perfumeVariantResponseList.map(variant => variant.price)
        )
        console.log(price)
        return (
            (filters.fragrance ? perfume.fragrance === filters.fragrance : true) &&
            (filters.brand ? perfume.brandName === filters.brand : true) &&
            (filters.concentration ? perfume.concentration === filters.concentration : true) &&
            (filters.fragranceGroup ? perfume.screntFamilyName === filters.fragranceGroup : true) &&
            (filters.maxPrice ? price <= parseFloat(filters.maxPrice) : true) // Price filter
        );
    });

    const sortedPerfumes = () => {
        switch (sortOption) {
            case "bestSelling":
                return filteredPerfumes; // Implement best selling logic here
            case "latest":
                return filteredPerfumes; // Implement latest logic here
            case "highToLow":
                return [...filteredPerfumes].sort((a, b) => b.price - a.price);
            case "lowToHigh":
                return [...filteredPerfumes].sort((a, b) => a.price - b.price);
            default:
                return filteredPerfumes;
        }
    };

    const handleSortChange = (e) => {
        setSortOption(e.target.value);
    };

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    // Calculate the products to display for the current page
    const currentProducts = sortedPerfumes();
    const indexOfLastProduct = currentPage * productsPerPage;
    const indexOfFirstProduct = indexOfLastProduct - productsPerPage;
    const displayedProducts = currentProducts.slice(indexOfFirstProduct, indexOfLastProduct);

    // Calculate total pages
    const totalPages = Math.ceil(currentProducts.length / productsPerPage);

    return (
        <div className="nuoc-hoa-nam">
            <div className="breadcrumb">
                <a href="/" className="breadcrumb-link">Trang chủ</a>
                <span className="arrow"> &gt; </span>
                <a href="/nuoc-hoa-nu" className="breadcrumb-link">Sản phẩm</a>
                <span className="arrow"> &gt; </span>
                <span className="current">Nước hoa Nam</span>
                <hr className="divider" />
            </div>

            <h1 className="product-title">Nước hoa nam</h1>

            <div className="filter-section">
                <div className="filter-group">
                    <label>Độ lưu hương</label>
                    <select name="fragrance" onChange={handleFilterChange}>
                        <option value="">All</option>
                        <option value="Tạm ổn 3 - 6 giờ">Tạm ổn 3 - 6 giờ</option>
                        <option value="Lâu 7 - 12 giờ">Lâu 7 - 12 giờ</option>
                        <option value="Rất lâu > 12 giờ">Rất lâu &gt; 12 giờ</option>
                    </select>
                </div>
                <div className="filter-group">
                    <label>Thương hiệu</label>
                    <select name="brand" onChange={handleFilterChange}>
                        <option value="">All</option>
                        <option value="Afnan">Afnan</option>
                        <option value="Armaf">Armaf</option>
                        <option value="Creed">Creed</option>
                        <option value="Chanel">Chanel</option>
                        <option value="Clive Christian">Clive Christian</option>
                        <option value="CreeGiorgiod">Giorgio</option>
                        <option value="Diesel">Diesel</option>
                        <option value="D&G">D&G</option>
                        <option value="Dior">Dior</option>
                        <option value="Marc Jacobs">Marc Jacobs</option>
                        <option value="Montblanc">Montblanc</option>
                        <option value="Narciso">Narciso</option>
                        <option value="Prada">Prada</option>
                        <option value="Gucci">Gucci</option>
                        <option value="Roja">Roja</option>
                        <option value="Lancome">Lancome</option>
                        <option value="Yves">Yves</option>
                        <option value="YSL">YSL</option>
                        <option value="Jean Paul">Jean Paul</option>
                        <option value="Versace">Versace</option>
                    </select>
                </div>

                <div className="filter-group">
                    <label>Nồng độ</label>
                    <select name="concentration" onChange={handleFilterChange}>
                        <option value="">All</option>
                        <option value="Eau de Parfum">Eau de Parfum</option>
                        <option value="Eau de Toilette">Eau de Toilette</option>
                        <option value="Parfum">Parfum</option>
                    </select>
                </div>

                <div className="filter-group">
                    <label>Nhóm hương</label>
                    <select name="fragranceGroup" onChange={handleFilterChange}>
                        <option value="">All</option>
                        <option value="Hương phương đông">Hương Phương Đông</option>
                        <option value="Hương gỗ">Hương gỗ</option>
                        <option value="Hương ngọt">Hương ngọt</option>
                        <option value="Hương thơm mát">Hương thơm mát</option>
                        <option value="Hương hoa cỏ">Hương hoa cỏ</option>
                    </select>
                </div>

                <div className="filter-group">
                    <label>Khoảng giá</label>
                    <select name="maxPrice" onChange={handleFilterChange}>
                        <option value="">All</option>
                        <option value="500000">Dưới 500 nghìn</option>
                        <option value="1000000">Dưới 1 triệu</option>
                        <option value="2000000">Dưới 2 triệu</option>
                        <option value="3000000">Dưới 3 triệu</option>
                        <option value="5000000">Dưới 5 triệu</option>
                        <option value="10000000">Dưới 10 triệu</option>
                        <option value="20000000">Dưới 20 triệu</option>
                    </select>
                </div>
            </div>

            <div className="sort-section">
                <h3>Sắp xếp theo</h3>
                <select onChange={handleSortChange} value={sortOption}>
                    <option value="latest">Mới nhất</option>
                    <option value="bestSelling">Bán chạy nhất</option>
                    <option value="highToLow">Giá từ cao - thấp</option>
                    <option value="lowToHigh">Giá từ thấp - cao</option>
                </select>
            </div>

            <div className="product-grid">
                {displayedProducts.map((products, index) => (
                    <ProductCard
                        img={handleBase64Decode(products.thumbnailImageData)}
                        name={products.name}
                        brandName={products.brandName}
                        price1={products.perfumeVariantResponseList[0].price}
                        price2={products.perfumeVariantResponseList[1].price}
                    />
                ))}

            </div>

            {totalPages > 1 && (
                <div className="pagination">
                    {Array.from({ length: totalPages }, (_, index) => (
                        <button
                            key={index}
                            onClick={() => handlePageChange(index + 1)}
                            disabled={currentPage === index + 1}
                            className={currentPage === index + 1 ? 'active' : ''}
                        >
                            {index + 1}
                        </button>
                    ))}
                </div>
            )}
        </div>
    );
};

export default memo(NuocHoaNam);