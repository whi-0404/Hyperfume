package com.Hyperfume.Backend.ElasticSearch;

import com.Hyperfume.Backend.dto.response.PerfumeGetAllResponse;
import com.Hyperfume.Backend.entity.Perfume;
import com.Hyperfume.Backend.entity.PerfumeImage;
import com.Hyperfume.Backend.entity.PerfumeVariant;
import com.Hyperfume.Backend.repository.PerfumeRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ESPerfumeMapperImpl implements ESPerfumeMapper{

    @Override
    public ESPerfume toDocument(Perfume perfume) {
        String thumbnailImageUrl = null;
        List<PerfumeImage> perfumeImageList = perfume.getImages();

        if(perfumeImageList != null && !perfumeImageList.isEmpty()){
               for(PerfumeImage perfumeImage : perfumeImageList){
                   if(perfumeImage.isThumbnail()){
                       thumbnailImageUrl = perfumeImage.getImageUrl();
                   }
               }
        }

        Double minPrice = null;
        Double maxPrice = null;
        boolean isAllowedToOrder = false;
        List<PerfumeVariant> perfumeVariants = perfume.getVariants();

        if ( perfumeVariants != null && !perfumeVariants.isEmpty()) {

            for(PerfumeVariant variant : perfumeVariants){
                if (variant.getPerfume_stock_quantity() > 0) {
                    isAllowedToOrder = true;
                    break;
                }
            }

            minPrice = perfume.getVariants().stream()
                    .map(PerfumeVariant::getPrice)
                    .min(Comparator.naturalOrder())
                    .map(BigDecimal::doubleValue)
                    .orElse(null);

            maxPrice = perfume.getVariants().stream()
                    .map(PerfumeVariant::getPrice)
                    .max(Comparator.naturalOrder())
                    .map(BigDecimal::doubleValue)
                    .orElse(null);
        }

        return ESPerfume.builder()
                .perfumeId(perfume.getId())
                .name(perfume.getName())
                .brandName(perfume.getBrand() != null ? perfume.getBrand().getName() : null)
                .perfumeDescription(perfume.getPerfume_description())
                .perfumeGender(perfume.getPerfume_gender())
                .concentration(perfume.getConcentration())
                .screntFamilyName(perfume.getScrentFamily() != null ? perfume.getScrentFamily().getName() : null)
                .mainNotes(perfume.getMain_notes())
                .countryName(perfume.getCountry() != null ? perfume.getCountry().getName() : null)
                .sale(perfume.isSale())
                .flashSale(perfume.isFlash_sale())
                .sold(perfume.getSold())
                .discount(perfume.getDiscount())
                .longevity(perfume.getLongevity())
                .createdAt(perfume.getCreatedAt())
                .thumbnailImageUrl(thumbnailImageUrl)
                .isAllowedToOrder(isAllowedToOrder)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
    }

    public PerfumeGetAllResponse toGetAllResponse(ESPerfume esPerfume){
        return PerfumeGetAllResponse.builder()
                .id(esPerfume.getPerfumeId())
                .name(esPerfume.getName())
                .perfume_gender(esPerfume.getPerfumeGender())
                .concentration(esPerfume.getConcentration())
                .screntFamilyName(esPerfume.getScrentFamilyName())
                .countryName(esPerfume.getCountryName())
                .sale(esPerfume.getSale())
                .flash_sale(esPerfume.getFlashSale())
                .longevity(esPerfume.getLongevity())
                .sold(esPerfume.getSold())
                .discount(esPerfume.getDiscount())
                .ThumbnailImageUrl(esPerfume.getThumbnailImageUrl())
                .brandName(esPerfume.getBrandName())
                .max_price(esPerfume.getMaxPrice() != null ? BigDecimal.valueOf(esPerfume.getMaxPrice()): null)
                .min_price(esPerfume.getMinPrice() != null ? BigDecimal.valueOf(esPerfume.getMinPrice()): null)
                .build();
    }

//    private ESPerfumeVariant toVariantDocument(PerfumeVariant variant) {
//        return ESPerfumeVariant.builder()
//                .id(variant.getId())
//                .name(variant.getName())
//                .price(variant.getPrice().doubleValue())
//                .perfumeStockQuantity(variant.getPerfume_stock_quantity())
//                .build();
//    }
}
