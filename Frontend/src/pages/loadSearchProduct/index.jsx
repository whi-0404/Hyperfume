import { memo, useState } from "react";
import { useLocation } from "react-router-dom";
import handleBase64Decode from "../../components/covertBase64ToImg"
import './style.scss';
import ProductCard from "../../components/productCard";

const LoadSearchProduct = () => {
    const location = useLocation();
    const products = location.state?.products || [];

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


    const handleFilterChange = (e) => {
        setFilters({ ...filters, [e.target.name]: e.target.value });
        setCurrentPage(1); // Reset to first page when filters change
    };

    // Filtering function
    // let maxPrice = Math.max(
    //     ...products.result.flatMap((perfume) =>
    //         perfume.perfumeVariantResponseList.map((variant) => variant.price)
    //     )
    // );
    // console.log(maxPrice)
    const filteredPerfumes = products.result.filter(perfume => {
        return (
            (filters.longevity ? perfume.longevity === filters.longevity : true) &&
            (filters.brand ? perfume.brandName === filters.brand : true) &&
            (filters.concentration ? perfume.concentration === filters.concentration : true) &&
            (filters.fragranceGroup ? perfume.screntFamilyName === filters.fragranceGroup : true) &&
            (filters.maxPrice ? Math.max(
                ...perfume.perfumeVariantResponseList.map(variant => variant.price)
            ) <= parseFloat(filters.maxPrice) : true) // Price filter
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
                <a href="/nuoc-hoa" className="breadcrumb-link">
                    <span className="current">Sản phẩm</span>
                </a>
                <hr className="divider" />
            </div>

            <h1 className="product-title">Nước hoa</h1>

            <div className="filter-section">
                <div className="filter-group">
                    <label>Độ lưu hương</label>
                    <select name="longevity" onChange={handleFilterChange}>
                        <option value="">All</option>
                        <option value="Trung bình - 4h đến 7h">Trung bình 4 - 7 giờ</option>
                        <option value="Lâu - 7h đến 12h">Lâu 7 - 12 giờ</option>
                        <option value="Rất lâu - Trên 12h">Rất lâu &gt; 12 giờ</option>
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
                        <option value="Hương cam chanh">Hương cam chanh</option>
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
                        id={products.id}
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

export default memo(LoadSearchProduct);