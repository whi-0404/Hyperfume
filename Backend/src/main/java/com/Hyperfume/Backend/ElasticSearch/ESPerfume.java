package com.Hyperfume.Backend.ElasticSearch;

import java.time.LocalDate;

import jakarta.persistence.Id;

import org.springframework.data.elasticsearch.annotations.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Document(indexName = "perfumes")
@Setting(settingPath = "elasticsearch-settings.json")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ESPerfume {
    @Id
    String id;

    @Field(type = FieldType.Integer)
    Integer perfumeId;

    @Field(type = FieldType.Text, analyzer = "english", searchAnalyzer = "autocomplete_search")
    String name;

    @Field(type = FieldType.Text)
    String brandName;

    @Field(type = FieldType.Text, analyzer = "vietnamese")
    String perfumeDescription;

    @Field(type = FieldType.Keyword)
    String perfumeGender;

    @Field(type = FieldType.Keyword)
    String concentration;

    @Field(type = FieldType.Text)
    String screntFamilyName;

    @Field(type = FieldType.Text, analyzer = "vietnamese")
    String mainNotes;

    @Field(type = FieldType.Text)
    String countryName;

    @Field(type = FieldType.Text)
    String longevity;

    Boolean sale;

    Boolean flashSale;

    @Field(type = FieldType.Integer)
    Integer sold;

    @Field(type = FieldType.Double)
    Double discount;

    @Field(type = FieldType.Date, format = DateFormat.date)
    LocalDate createdAt;

    //    @Field(type = FieldType.Nested)
    //    List<ESPerfumeVariant> variants;

    @Field(type = FieldType.Text)
    String thumbnailImageUrl;

    Boolean isAllowedToOrder;

    @Field(type = FieldType.Double)
    Double minPrice;

    @Field(type = FieldType.Double)
    Double maxPrice;
}
