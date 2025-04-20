package com.Hyperfume.Backend.ElasticSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ESPerfumeRepository extends ElasticsearchRepository<ESPerfume, Integer> {
//
//    Page<ESPerfume> findByFlashSale(boolean flashSale, Pageable pageable);
//
//    Page<ESPerfume> findByType(String type, Pageable pageable);
//
//    Page<ESPerfume> findByPerfumeGender(String gender, Pageable pageable);
//
//    Page<ESPerfume> findByCountryId(Integer countryId, Pageable pageable);
//
//    Page<ESPerfume> findByBrandId(Integer brandId, Pageable pageable);
//
//    Page<ESPerfume> findByScrentFamilyId(Integer screntFamilyId, Pageable pageable);

    @Query("{\"bool\": {\"should\": [" +
            "{\"match\": {\"name\": {\"query\": \"?0\", \"boost\": 3.0, \"fuzziness\": \"AUTO\"}}}, " +
            "{\"match\": {\"brandName\": {\"query\": \"?0\", \"boost\": 2.0, \"fuzziness\": \"AUTO\"}}}, " +
            "{\"match\": {\"perfumeDescription\": {\"query\": \"?0\", \"boost\": 1.0, \"fuzziness\": \"AUTO\"}}}, " +
            "{\"match\": {\"mainNotes\": {\"query\": \"?0\", \"boost\": 1.5, \"fuzziness\": \"AUTO\"}}} " +
            "]}}")
    Page<ESPerfume> searchByNameAndDescription(String searchTerm, Pageable pageable);

    @Query("{\"bool\": {\"must\": ?0}}")
    Page<ESPerfume> advancedSearch(String query, Pageable pageable);
}
