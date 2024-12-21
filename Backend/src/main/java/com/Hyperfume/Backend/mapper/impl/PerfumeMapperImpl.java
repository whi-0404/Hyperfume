package com.Hyperfume.Backend.mapper.impl;

import com.Hyperfume.Backend.dto.request.PerfumeRequest;
import com.Hyperfume.Backend.dto.response.PerfumeImageResponse;
import com.Hyperfume.Backend.dto.response.PerfumeResponse;
import com.Hyperfume.Backend.dto.response.PerfumeVariantResponse;
import com.Hyperfume.Backend.entity.*;
import com.Hyperfume.Backend.mapper.PerfumeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PerfumeMapperImpl implements PerfumeMapper {
    PerfumeVariantMapperImpl perfumeVariantMapper;
    PerfumeImageMapperImpl perfumeImageMapper;

    public PerfumeResponse toResponse(Perfume perfume) {
        if (perfume == null) {
            return null;
        } else {
            PerfumeResponse.PerfumeResponseBuilder perfumeResponse = PerfumeResponse.builder();
            perfumeResponse.brandName(this.perfumeBrandName(perfume));
            perfumeResponse.screntFamilyName(this.perfumeScrentFamilyName(perfume));
            perfumeResponse.countryName(this.perfumeCountryName(perfume));
            perfumeResponse.id(perfume.getId());
            perfumeResponse.name(perfume.getName());
            perfumeResponse.sold(perfume.getSold());
            perfumeResponse.createdAt(perfume.getCreatedAt());
            perfumeResponse.updatedAt(perfume.getUpdatedAt());
            perfumeResponse.release_year(perfume.getRelease_year());
            perfumeResponse.type(perfume.getType());
            perfumeResponse.perfume_description(perfume.getPerfume_description());
            perfumeResponse.perfume_gender(perfume.getPerfume_gender());
            perfumeResponse.concentration(perfume.getConcentration());
            perfumeResponse.main_notes(perfume.getMain_notes());
            perfumeResponse.longevity(perfume.getLongevity());
            perfumeResponse.sillage(perfume.getSillage());
            perfumeResponse.style(perfume.getStyle());
            perfumeResponse.season_usage(perfume.getSeason_usage());
            perfumeResponse.top_notes(perfume.getTop_notes());
            perfumeResponse.middle_notes(perfume.getMiddle_notes());
            perfumeResponse.base_notes(perfume.getBase_notes());
            perfumeResponse.sale(perfume.isSale());
            perfumeResponse.flash_sale(perfume.isFlash_sale());
            perfumeResponse.perfumeVariantResponseList(perfumeVariantResponses(perfume));
            perfumeResponse.perfumeImageResponseList(perfumeImageResponses(perfume));
            return perfumeResponse.build();
        }
    }

    public Perfume toEntity(PerfumeRequest request, Brand brand, ScrentFamily screntFamily, Country country) {
        if (request == null && brand == null && screntFamily == null && country == null) {
            return null;
        } else {
            Perfume.PerfumeBuilder perfume = Perfume.builder();
            if (request != null) {
                perfume.name(request.getName());
                if (request.getRelease_year() != null) {
                    perfume.release_year(request.getRelease_year());
                }

                perfume.type(request.getType());
                perfume.perfume_description(request.getPerfume_description());
                perfume.perfume_gender(request.getPerfume_gender());
                perfume.concentration(request.getConcentration());
                perfume.main_notes(request.getMain_notes());
                perfume.longevity(request.getLongevity());
                perfume.sillage(request.getSillage());
                perfume.style(request.getStyle());
                perfume.season_usage(request.getSeason_usage());
                perfume.top_notes(request.getTop_notes());
                perfume.middle_notes(request.getMiddle_notes());
                perfume.base_notes(request.getBase_notes());
            }

            perfume.brand(brand);
            perfume.screntFamily(screntFamily);
            perfume.country(country);
            return perfume.build();
        }
    }

    public void updateEntity(Perfume perfume, PerfumeRequest request, Brand brand, ScrentFamily screntFamily, Country country) {
        if (request != null || brand != null || screntFamily != null || country != null) {
            if (request != null) {
                if (request.getName() != null) {
                    perfume.setName(request.getName());
                }

                if (request.getRelease_year() != null) {
                    perfume.setRelease_year(request.getRelease_year());
                }

                if (request.getType() != null) {
                    perfume.setType(request.getType());
                }

                if (request.getPerfume_description() != null) {
                    perfume.setPerfume_description(request.getPerfume_description());
                }

                if (request.getPerfume_gender() != null) {
                    perfume.setPerfume_gender(request.getPerfume_gender());
                }

                if (request.getConcentration() != null) {
                    perfume.setConcentration(request.getConcentration());
                }

                if (request.getMain_notes() != null) {
                    perfume.setMain_notes(request.getMain_notes());
                }

                if (request.getLongevity() != null) {
                    perfume.setLongevity(request.getLongevity());
                }

                if (request.getSillage() != null) {
                    perfume.setSillage(request.getSillage());
                }

                if (request.getStyle() != null) {
                    perfume.setStyle(request.getStyle());
                }

                if (request.getSeason_usage() != null) {
                    perfume.setSeason_usage(request.getSeason_usage());
                }

                if (request.getTop_notes() != null) {
                    perfume.setTop_notes(request.getTop_notes());
                }

                if (request.getMiddle_notes() != null) {
                    perfume.setMiddle_notes(request.getMiddle_notes());
                }

                if (request.getBase_notes() != null) {
                    perfume.setBase_notes(request.getBase_notes());
                }
            }

            if (brand != null) {
                perfume.setBrand(brand);
            }

            if (screntFamily != null) {
                perfume.setScrentFamily(screntFamily);
            }

            if (country != null) {
                perfume.setCountry(country);
            }

        }
    }

    private String perfumeBrandName(Perfume perfume) {
        if (perfume == null) {
            return null;
        } else {
            Brand brand = perfume.getBrand();
            if (brand == null) {
                return null;
            } else {
                String name = brand.getName();
                return name == null ? null : name;
            }
        }
    }

    private String perfumeScrentFamilyName(Perfume perfume) {
        if (perfume == null) {
            return null;
        } else {
            ScrentFamily screntFamily = perfume.getScrentFamily();
            if (screntFamily == null) {
                return null;
            } else {
                String name = screntFamily.getName();
                return name == null ? null : name;
            }
        }
    }

    private String perfumeCountryName(Perfume perfume) {
        if (perfume == null) {
            return null;
        } else {
            Country country = perfume.getCountry();
            if (country == null) {
                return null;
            } else {
                String name = country.getName();
                return name == null ? null : name;
            }
        }
    }

    private List<PerfumeVariantResponse> perfumeVariantResponses(Perfume perfume){
        if (perfume == null) {
            return null;
        } else {
            return perfume.getVariants().stream()
                    .map(perfumeVariantMapper::toResponse)
                    .collect(Collectors.toList());
        }
    }

    private List<PerfumeImageResponse> perfumeImageResponses(Perfume perfume){
        if (perfume == null) {
            return null;
        } else {
            return perfume.getImages().stream()
                    .map(perfumeImageMapper::toResponse)
                    .collect(Collectors.toList());
        }
    }
}