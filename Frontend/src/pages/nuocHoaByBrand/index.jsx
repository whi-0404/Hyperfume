import { memo, useState, useEffect, Fragment } from "react";
import { useLocation } from 'react-router-dom';
import './style.scss';
import { listProducts } from "../../services/AllProduct";
import ProductCard from "../../components/productCard";
import { brands } from "../../services/getBrand";
import { screntFamilies } from "../../services/getScrentFamily";

const ListNuocHoa = () => {
    // Get initial page from URL query parameters
    const getInitialPage = () => {
        const params = new URLSearchParams(window.location.search);
        const pageParam = params.get("page");
        return pageParam ? parseInt(pageParam, 10) : 1;
    };

    // Get initial sort and filter values from URL
    const getInitialParams = () => {
        const params = new URLSearchParams(window.location.search);
        const brandIdParam = params.get("brandId");

        return {
            sort: params.get("sort") || "latest",
            gender: params.get("gender") || "",
            longevity: params.get("longevity") || "",
            brandId: brandIdParam ? parseInt(brandIdParam, 10) : "",  // Đổi brand thành brandId
            concentration: params.get("concentration") || "",
            screntFamilyId: params.get("screntFamilyId") || "",  // Đổi screntFamilies thành screntFamilyId
            maxPrice: params.get("maxPrice") || ""
        };
    };

    const initialParams = getInitialParams();
    
    const [products, setProducts] = useState({ result: { data: [], totalPages: 0, totalElements: 0, pageSize: 15 } });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [brandList, setBrandList] = useState([]);
    const [screntFamiliesList, setScrentList] = useState([]);


    const location = useLocation();
    const brandIdFromNav = location.state?.brandId || "";
    const brandNameFromNav = location.state?.brandName || "";
    const [brandId, setBrandId] = useState(brandIdFromNav);
    const [brandName, setBrandName] = useState(brandNameFromNav);

    const [sortOption, setSortOption] = useState(initialParams.sort);
    const [filters, setFilters] = useState({
        gender: initialParams.gender,
        longevity: initialParams.longevity,
        brandId: initialParams.brandId|| brandIdFromNav,
        concentration: initialParams.concentration,
        screntFamilyId: initialParams.screntFamilyId,
        maxPrice: initialParams.maxPrice
    });
    const [currentPage, setCurrentPage] = useState(getInitialPage());

    useEffect(() => {
        if (location.state?.brandId) {
            setBrandId(location.state.brandId);
            setBrandName(location.state.brandName);
            setFilters(prev => ({ ...prev, brandId: location.state.brandId }));
        }
    }, [location.state?.brandId, location.state?.brandName]);   

    // // Load brands from sessionStorage or fetch from API
    // useEffect(() => {
    //     const loadBrands = async () => {
    //         const cachedBrands = sessionStorage.getItem("brands");
            
    //         if (cachedBrands) {
    //             try {
    //                 const parsedBrands = JSON.parse(cachedBrands);
    //                 setBrandList(parsedBrands);
    //             } catch (error) {
    //                 console.error("Error parsing cached brands:", error);
    //                 await fetchBrands();
    //             }
    //         } else {
    //             await fetchBrands();
    //         }
    //     };
        
    //     loadBrands();
    // }, []);

    useEffect(() => {
        const loadScrentFamilies = async () => {
            const cachedScrentFamilies = sessionStorage.getItem("screntFamilies");
            
            if (cachedScrentFamilies) {
                try {
                    const parsedScrentFamilies = JSON.parse(cachedScrentFamilies);
                    setScrentList(parsedScrentFamilies);
                } catch (error) {
                    console.error("Error parsing cached ScrentFamilies:", error);
                    await fetchScrentFamilies();
                }
            } else {
                await fetchScrentFamilies();
            }
        };
        
        loadScrentFamilies();
    }, []);

    // const fetchBrands = async () => {
    //     try {
    //         const response = await brands();
            
    //         if (response.data && response.data.code === 1000) {
    //             const fetchedBrands = response.data.result;
    //             sessionStorage.setItem("brands", JSON.stringify(fetchedBrands));
    //             setBrandList(fetchedBrands);
    //         } else {
    //             console.error("Invalid brand response format");
    //         }
    //     } catch (error) {
    //         console.error("Failed to fetch brands:", error);
    //     }
    // };

    const fetchScrentFamilies = async () => {
        const cachedscrentFamilies = sessionStorage.getItem("screntFamilies");
    
        if (cachedscrentFamilies) {
            return JSON.parse(cachedscrentFamilies);
        } else {
            try {
                setLoading(true); // Bắt đầu load dữ liệu
                const response = await screntFamilies(); // Gọi API
    
                if (response.data && response.data.code === 1000) {
                    const screntFamiliesList = response.data.result;
                    
                    // Lưu vào sessionStorage
                    sessionStorage.setItem("screntFamilies", JSON.stringify(screntFamiliesList));
    
                    setLoading(false); // Hoàn tất load dữ liệu
                    return screntFamiliesList;
                } else {
                    setError("Invalid response format");
                    setLoading(false);
                    return [];
                }
            } catch (error) {
                console.error(error);
                setError("Failed to fetch screntFamilies");
                setLoading(false);
                return [];
            }
        }
      };

    // Update URL when parameters change
    const updateUrlParams = (params) => {
        const url = new URL(window.location.href);
        
        // Trước tiên xóa các param filter cũ
        url.searchParams.delete('page');
        url.searchParams.delete('sort');
        url.searchParams.delete('longevity');
        url.searchParams.delete('gender');
        url.searchParams.delete('concentration');
        url.searchParams.delete('screntFamilyId');
        url.searchParams.delete('maxPrice');
        
        // Thêm page và sort
        if (params.page && params.page !==1) url.searchParams.set('page', params.page);
        if (params.sort && params.sort !=="latest") url.searchParams.set('sort', params.sort);
        
        // Thêm các filter nếu có giá trị
        if (params.gender) url.searchParams.set('gender', params.gender);
        if (params.longevity) url.searchParams.set('longevity', params.longevity);
        if (params.concentration) url.searchParams.set('concentration', params.concentration);
        if (params.screntFamilyId) url.searchParams.set('screntFamilyId', params.screntFamilyId);
        if (params.maxPrice) url.searchParams.set('maxPrice', params.maxPrice);
        
        window.history.pushState({}, '', url.toString());
    };

    const fetchProducts = () => {
        setLoading(true);
        // Xây dựng queryParams chỉ bao gồm những trường có giá trị thực
        const queryParams = {
            page: currentPage,
            sort: sortOption,
        };
        
        if(filters.gender) queryParams.gender = filters.gender;
        if (filters.longevity) queryParams.longevity = filters.longevity;
        if (filters.brandId) queryParams.brandId = filters.brandId;
        if (filters.concentration) queryParams.concentration = filters.concentration;
        if (filters.screntFamilyId) queryParams.screntFamilyId = filters.screntFamilyId;
        if (filters.maxPrice) queryParams.maxPrice = filters.maxPrice;
        
        listProducts(queryParams)
            .then((response) => {
                if (response.data && response.data.code === 1000) {
                    setProducts({
                        result: {
                            data: response.data.result.data || [],
                            totalPages: response.data.result.totalPages || 0,
                            totalElements: response.data.result.totalElements || 0,
                            pageSize: response.data.result.pageSize || 15
                        }
                    });
                } else {
                    setError('Invalid response format');
                }
                setLoading(false);
            })
            .catch((error) => {
                console.error(error);
                setError('Failed to fetch products');
                setLoading(false);
            });
    };
    

    // Listen for URL changes using popstate event
    useEffect(() => {
        const handlePopState = () => {
            const newPage = getInitialPage();
            const newParams = getInitialParams();
            
            setCurrentPage(newPage);
            setSortOption(newParams.sort);
            setFilters({
                longevity: newParams.longevity,
                concentration: newParams.concentration,
                screntFamilyId: newParams.screntFamilyId,
                maxPrice: newParams.maxPrice
            });
        };

        window.addEventListener('popstate', handlePopState);
        return () => window.removeEventListener('popstate', handlePopState);
    }, []);

    useEffect(() => {
        fetchProducts();
        // Note: Not including fetchProducts in the dependency array to avoid infinite loops
    }, [currentPage, sortOption, filters]);

    // Update URL whenever filter/sort/page parameters change
    useEffect(() => {
        updateUrlParams({
            page: currentPage,
            sort: sortOption,
            ...filters
        });
    }, [currentPage, sortOption, filters]);

    if (loading) return <div className="loading-container">Loading...</div>;
    if (error) return <div className="error-container">Error: {error}</div>;
    
    // Check if products data exists and has the expected structure
    if (!products.result || !products.result.data) {
        return <div>No products available</div>;
    }

    const handleFilterChange = (e) => {
        const { name, value } = e.target;
        setFilters(prevFilters => ({ ...prevFilters, [name]: value }));
        setCurrentPage(1); // Reset to first page when filters change
    };

    const handleSortChange = (e) => {
        const newSortOption = e.target.value;
        setSortOption(newSortOption);
        setCurrentPage(1); // Reset to first page when sort changes
    };

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    // Get total pages from API response or calculate if not provided
    const totalPages = products.result.totalPages || 
                      Math.ceil(products.result.totalElements / products.result.pageSize) || 
                      1;

    // Get displayed products directly from API response
    const displayedProducts = products.result.data;

    return (
        <div className="nuoc-hoa">
            <div className="breadcrumb">
                <a href="/" className="breadcrumb-link">Trang chủ</a>
                <span className="arrow"> &gt; </span>
                <a href="/nuoc-hoa" className="breadcrumb-link">Sản phẩm</a>
                <span className="arrow"> &gt; </span>
                <span className="current">Nước hoa {brandName}</span>
                <hr className="divider" />
            </div>

            <h1 className="product-title">Nước hoa {brandName}</h1>

            <div className="filter-section">
                <div className="filter-group">
                    <label>Độ lưu hương</label>
                    <select name="longevity" onChange={handleFilterChange} value={filters.longevity}>
                        <option value="">All</option>
                        <option value="Trung bình - 4h đến 7h">Trung bình 4 - 7 giờ</option>
                        <option value="Trung bình - 6 đến 8h">Trung bình 6 - 8 giờ</option>
                        <option value="Lâu - 7h đến 12h">Lâu 7 - 12 giờ</option>
                        <option value="Rất lâu - Trên 12h">Rất lâu &gt; 12 giờ</option>
                    </select>
                </div>

                {/* <div className="filter-group"> 
                    <label>Thương hiệu</label>
                    <select name="brandId" onChange={handleFilterChange} value={filters.brandId}>
                        <option value="">All</option>
                        {brandList && brandList.length > 0 ? (
                            brandList.map((brand) => (
                                <option key={brand.id} value={brand.id}>
                                    {brand.name}
                                </option>
                            ))
                        ) : (
                            <option value="" disabled>Đang tải thương hiệu...</option>
                        )}
                    </select>
                </div> */}

                <div className="filter-group">
                    <label>Nồng độ</label>
                    <select name="concentration" onChange={handleFilterChange} value={filters.concentration}>
                        <option value="">All</option>
                        <option value="Eau de Parfum">Eau de Parfum</option>
                        <option value="Eau de Toilette">Eau de Toilette</option>
                        <option value="Parfum">Parfum</option>
                    </select>
                </div>

                <div className="filter-group">
                    <label>Nhóm hương</label>
                    <select name="screntFamilyId" onChange={handleFilterChange} value={filters.screntFamilyId}>
                        <option value="">All</option>
                        {screntFamiliesList && screntFamiliesList.length > 0 ? (
                            screntFamiliesList.map((screntFamilies) => (
                                <option key={screntFamilies.id} value={screntFamilies.id}>
                                    {screntFamilies.name}
                                </option>
                            ))
                        ) : (
                            <option value="" disabled>Đang tải Nhóm hương...</option>
                        )}
                    </select>
                </div>

                <div className="filter-group">
                    <label>Khoảng giá</label>
                    <select name="maxPrice" onChange={handleFilterChange} value={filters.maxPrice}>
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

                <div className="filter-group">
                    <label>Giới tính</label>
                    <select name="gender" onChange={handleFilterChange} value={filters.gender}>
                        <option value="">All</option>
                        <option value="Nam">Nước hoa Nam</option>
                        <option value="Nữ">Nước hoa Nữ</option>
                        <option value="Unisex">Nước hoa Unisex</option>
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
                {displayedProducts.length > 0 ? (
                    displayedProducts.map((product) => (
                        <ProductCard
                            key={product.id}
                            id={product.id}
                            img={product.thumbnailImageUrl}
                            brandName={product.brandName}
                            name={product.name}
                            price1={product.min_price}
                            price2={product.max_price}
                        />
                    ))
                ) : (
                    <div className="no-products">Không tìm thấy sản phẩm nào</div>
                )}
            </div>

            {totalPages > 1 && (
                <div className="pagination">
                    {/* Previous page button */}
                    <button 
                        onClick={() => handlePageChange(currentPage - 1)}
                        disabled={currentPage === 1}
                        className="pagination-nav"
                    >
                        &laquo;
                    </button>
                    
                    {/* Show limited page numbers with ellipsis for large page counts */}
                    {Array.from({ length: totalPages }, (_, i) => i + 1)
                        .filter(page => {
                            // Show first page, last page, current page, and pages around current page
                            return page === 1 || 
                                   page === totalPages || 
                                   (page >= currentPage - 1 && page <= currentPage + 1);
                        })
                        .map((page, index, array) => (
                            <Fragment key={page}>
                                <button
                                    onClick={() => handlePageChange(page)}
                                    className={currentPage === page ? 'active' : ''}
                                >
                                    {page}
                                </button>
                                
                                {/* Add ellipsis if there's a gap */}
                                {index < array.length - 1 && array[index + 1] - page > 1 && (
                                    <span className="ellipsis">...</span>
                                )}
                            </Fragment>
                        ))}
                    
                    {/* Next page button */}
                    <button 
                        onClick={() => handlePageChange(currentPage + 1)}
                        disabled={currentPage === totalPages}
                        className="pagination-nav"
                    >
                        &raquo;
                    </button>
                </div>
            )}
        </div>
    );
};

export default memo(ListNuocHoa);