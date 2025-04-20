package com.Hyperfume.Backend.ElasticSearch;

import co.elastic.clients.elasticsearch.core.search.CompletionSuggestOption;
import co.elastic.clients.json.JsonData;
import com.Hyperfume.Backend.dto.response.PageResponse;
import com.Hyperfume.Backend.dto.response.PerfumeGetAllResponse;
import com.Hyperfume.Backend.entity.Perfume;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.NativeQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ESPerfumeService {

    private final ElasticsearchClient client;
    private final ESPerfumeMapper esPerfumeMapper;
    private final ESPerfumeRepository esPerfumeRepository;

    public void indexPerfume(Perfume perfume){
        ESPerfume esPerfume = esPerfumeMapper.toDocument(perfume);
        esPerfumeRepository.save(esPerfume);
        log.info("Indexed perfume with ID: {}", perfume.getId());
    }

    public void indexPerfumes(List<Perfume> perfumes) {
        List<ESPerfume> esPerfumes = perfumes.stream()
                .map(esPerfumeMapper::toDocument)
                .toList();
        esPerfumeRepository.saveAll(esPerfumes);
        log.info("Indexed {} perfumes", perfumes.size());
    }

    public void deletePerfume(int id) {
        esPerfumeRepository.deleteById(id);
        log.info("Deleted perfume with ID: {}", id);
    }

    public PageResponse<PerfumeGetAllResponse> advancedSearch(
            int page,
            int size,
            String sortOption,
            String gender,
            String longevity,
            String countryName,
            String brandName,
            String concentration,
            String screntFamilyName,
            Double maxPrice,
            String searchTerm) throws IOException {

        BoolQuery.Builder boolQuery = new BoolQuery.Builder();

        if(searchTerm != null && !searchTerm.isBlank()){
            List<Query> shouldQueries = new ArrayList<>();

            shouldQueries.add(Query.of(q -> q.match(m -> m
                    .field("name")
                    .query(searchTerm)
                    .fuzziness("AUTO")
                    .boost(3.0f))));

            shouldQueries.add(Query.of(q -> q.match(m -> m
                    .field("brandName")
                    .query(searchTerm)
                    .fuzziness("AUTO")
                    .boost(2.0f))));

            shouldQueries.add(Query.of(q -> q.match(m -> m
                    .field("perfumeDescription")
                    .query(searchTerm)
                    .fuzziness("AUTO")
                    .boost(1.0f))));

            shouldQueries.add(Query.of(q -> q.match(m -> m
                    .field("mainNotes")
                    .query(searchTerm)
                    .fuzziness("AUTO")
                    .boost(1.5f))));

            boolQuery.should(shouldQueries);
        }

        if (gender != null && !gender.isBlank()) {
            boolQuery.filter(Query.of(q -> q.term(t -> t
                    .field("perfumeGender.keyword")
                    .value(gender))));
        }
        if (longevity != null && !longevity.isBlank()) {
            boolQuery.filter(Query.of(q -> q.term(t -> t
                    .field("longevity.keyword")
                    .value(longevity))));
        }
        if (countryName != null) {
            boolQuery.filter(Query.of(q -> q.term(t -> t
                    .field("countryName.keyword")
                    .value(countryName))));
        }
        if (brandName != null) {
            boolQuery.filter(Query.of(q -> q.term(t -> t
                    .field("brandName.keyword")
                    .value(brandName))));
        }
        if (concentration != null && !concentration.isBlank()) {
            boolQuery.filter(Query.of(q -> q.term(t -> t
                    .field("concentration.keyword")
                    .value(concentration))));
        }
        if (screntFamilyName != null) {
            boolQuery.filter(Query.of(q -> q.term(t -> t
                    .field("screntFamilyName.keyword")
                    .value(screntFamilyName))));
        }

        if (maxPrice != null) {
            boolQuery.filter(Query.of(q -> q.range(r -> r
                    .number(v-> v
                            .field("minPrice")
                            .lte(maxPrice)))));
        }

        SearchRequest searchRequest = SearchRequest.of(r -> r
                .index("perfumes")
                .query(q -> q.bool(boolQuery.build()))
                .from((page - 1) * size)
                .size(size)
                .sort(buildSortOptions(sortOption)));


        SearchResponse<ESPerfume> searchResponse = client.search(
                searchRequest,
                ESPerfume.class
        );

        List<PerfumeGetAllResponse> responses = searchResponse.hits().hits().stream()
                .map(hit -> {
                    ESPerfume esPerfume = hit.source();
                    return esPerfumeMapper.toGetAllResponse(esPerfume);
                })
                .toList();

        long totalElements = searchResponse.hits().total().value();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return PageResponse.<PerfumeGetAllResponse>builder()
                .pageSize(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .Data(responses)
                .build();
    }

    public PageResponse<PerfumeGetAllResponse> searchByNameAndDescription(String searchTerm, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ESPerfume> searchResults = esPerfumeRepository.searchByNameAndDescription(searchTerm, pageable);

        List<PerfumeGetAllResponse> responses = searchResults.getContent().stream()
                .map(esPerfumeMapper::toGetAllResponse)
                .toList();


        return PageResponse.<PerfumeGetAllResponse>builder()
                .pageSize(searchResults.getSize())
                .totalPages(searchResults.getTotalPages())
                .totalElements(searchResults.getTotalElements())
                .Data(responses)
                .build();
    }

    public List<String> autoCompletePerfumeName(String keyword, int maxSuggestions) {
        try {
            BoolQuery.Builder boolQuery = new BoolQuery.Builder();

            boolQuery.must(Query.of(q -> q
                    .matchPhrasePrefix(m -> m
                            .field("name")
                            .query(keyword))));

            SearchRequest searchRequest = SearchRequest.of(r -> r
                    .index("perfumes")
                    .query(q -> q.bool(boolQuery.build()))
                    .size(maxSuggestions)
                    .source(s -> s.filter(f -> f.includes("name"))));

            SearchResponse<ESPerfume> searchResponse = client.search(
                    searchRequest,
                    ESPerfume.class
            );

            return searchResponse.hits().hits().stream()
                    .map(hit -> hit.source().getName())
                    .toList();

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

//    public List<String> getSuggestions(String query, int maxSuggestions) throws IOException {
//        if (query == null || query.trim().isEmpty()) {
//            return new ArrayList<>();
//        }
//
//        SearchRequest suggestionRequest = SearchRequest.of(r -> r
//                .index("perfumes")
////                .size(0)
//                .suggest(s -> s
//                        .suggesters("perfume_suggestion", sg -> sg
//                                .prefix(query)
//                                .completion(c -> c
//                                        .field("suggest")
//                                        .skipDuplicates(true)
//                                        .size(maxSuggestions)
//                                        .fuzzy(f -> f
//                                                .fuzziness("AUTO")
//                                                .minLength(2))
//                                )
//                        )
//                )
//
//        );

//        try {
//            SearchResponse<ESPerfume> searchResponse = client.search(suggestionRequest, ESPerfume.class);
//
//            if (searchResponse.suggest() == null || !searchResponse.suggest().containsKey("perfume_suggestion")) {
//                return new ArrayList<>();
//            }
//
//            return searchResponse.suggest()
//                    .get("perfume_suggestion")
//                    .stream()
//                    .flatMap(suggestion -> suggestion.completion().options().stream())
//                    .map(CompletionSuggestOption::text)
//                    .distinct()
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            log.error("Error getting suggestions: {}", e.getMessage(), e);
//            return new ArrayList<>();
//        }
//    }

//    private BoolQuery.Builder commonBoolQueryBuilder(){
//        BoolQuery.Builder boolBuilder = new BoolQuery.Builder();
//
//
//    }

    private List<SortOptions> buildSortOptions(String sortOption) {
        if (sortOption != null) {
            return switch (sortOption) {
                case "lowToHigh" -> List.of(SortOptions.of(s -> s.field(f -> f
                        .field("minPrice")
                        .order(SortOrder.Asc))));
                case "HighToLow" -> List.of(SortOptions.of(s -> s.field(f -> f
                        .field("maxPrice")
                        .order(SortOrder.Desc))));
                case "latest" -> List.of(SortOptions.of(s -> s.field(f -> f
                        .field("createdAt")
                        .order(SortOrder.Desc))));
                case "popularity" -> List.of(SortOptions.of(s -> s.field(f -> f
                        .field("sold")
                        .order(SortOrder.Desc))));
                default -> List.of(SortOptions.of(s -> s.score(sc -> sc.order(SortOrder.Desc))));
            };
        }
        return List.of(SortOptions.of(s -> s.score(sc -> sc.order(SortOrder.Desc))));
    }
}
