package com.Hyperfume.Backend.repository.specification;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.Hyperfume.Backend.entity.Perfume;
import com.Hyperfume.Backend.entity.PerfumeVariant;

@Component
public class PerfumeSpecification {
    public static Specification<Perfume> hasLongevity(String longevity) {
        return (root, query, cb) -> {
            if (longevity == null) return null;

            return cb.equal(root.get("longevity"), longevity);
        };
    }

    public static Specification<Perfume> hasBrand(Integer brandId) {
        return (root, query, cb) -> {
            if (brandId == null) return null;

            return cb.equal(root.get("brand").get("id"), brandId);
        };
    }

    public static Specification<Perfume> hasConcentration(String concentration) {
        return (root, query, cb) -> {
            if (concentration == null) return null;

            return cb.equal(root.get("concentration"), concentration);
        };
    }

    public static Specification<Perfume> hasScrentFamily(Integer screntFamilyId) {
        return (root, query, cb) -> {
            if (screntFamilyId == null) return null;

            return cb.equal(root.get("screntFamily").get("id"), screntFamilyId);
        };
    }

    public static Specification<Perfume> hasPriceLessThan(Long maxPrice) {
        return (root, query, cb) -> {
            if (maxPrice == null) return null;

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<PerfumeVariant> variantRoot = subquery.from(PerfumeVariant.class);
            subquery.select(cb.min(variantRoot.get("price"))).where(cb.equal(variantRoot.get("perfume"), root));

            return cb.lessThanOrEqualTo(subquery, maxPrice);
        };
    }

    public static Specification<Perfume> getSpecifications(
            String gender,
            String longevity,
            Integer countryId,
            Integer brandId,
            String concentration,
            Integer screntFamilyId,
            Long maxPrice,
            String sortOption) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // subquery max_price cho tung variants theo perfume_id
            Subquery<Long> maxPriceSubquery = query.subquery(Long.class);
            Root<PerfumeVariant> maxPriceVariantRoot = maxPriceSubquery.from(PerfumeVariant.class);
            maxPriceSubquery
                    .select(cb.max(maxPriceVariantRoot.get("price")))
                    .where(cb.equal(maxPriceVariantRoot.get("perfume").get("id"), root.get("id")));

            Subquery<Long> minPriceSubquery = query.subquery(Long.class);
            Root<PerfumeVariant> minPriceVariantRoot = minPriceSubquery.from(PerfumeVariant.class);
            minPriceSubquery
                    .select(cb.min(minPriceVariantRoot.get("price")))
                    .where(cb.equal(minPriceVariantRoot.get("perfume").get("id"), root.get("id")));

            // Add predicates only if they're not null
            if (gender != null) {
                predicates.add(cb.equal(root.get("perfume_gender"), gender));
            }

            if (longevity != null) {
                predicates.add(cb.equal(root.get("longevity"), longevity));
            }

            if (countryId != null) {
                predicates.add(cb.equal(root.get("country").get("id"), countryId));
            }

            if (brandId != null) {
                predicates.add(cb.equal(root.get("brand").get("id"), brandId));
            }

            if (concentration != null) {
                predicates.add(cb.equal(root.get("concentration"), concentration));
            }

            if (screntFamilyId != null) {
                predicates.add(cb.equal(root.get("screntFamily").get("id"), screntFamilyId));
            }

            if (maxPrice != null) {
                Predicate maxPricePredicate = hasPriceLessThan(maxPrice).toPredicate(root, query, cb);
                if (maxPricePredicate != null) {
                    predicates.add(maxPricePredicate);
                }
            }

            // Ensure distinct results
            query.distinct(true);

            switch (sortOption) {
                case "highToLow" -> query.orderBy(cb.desc(maxPriceSubquery));
                case "lowToHigh" -> query.orderBy(cb.asc(minPriceSubquery));
                case "bestSelling" -> query.orderBy(cb.desc(root.get("sold")));
                default -> query.orderBy(cb.desc(root.get("createdAt")));
            }

            //            query.multiselect(
            //                    root.get("id"),
            //                    root.get("name"),
            //                    root.get("longevity"),
            //                    root.get("concentration"),
            //                    minPriceSubquery.alias("minPrice"),
            //                    maxPriceSubquery.alias("maxPrice")
            //            );

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
