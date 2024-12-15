package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.PerfumeRequest;
import com.Hyperfume.Backend.dto.response.PerfumeResponse;
import com.Hyperfume.Backend.entity.Brand;
import com.Hyperfume.Backend.entity.Country;
import com.Hyperfume.Backend.entity.Perfume;
import com.Hyperfume.Backend.entity.ScrentFamily;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.PerfumeMapper;
import com.Hyperfume.Backend.repository.BrandRepository;
import com.Hyperfume.Backend.repository.CountryRepository;
import com.Hyperfume.Backend.repository.PerfumeRepository;
import com.Hyperfume.Backend.repository.ScrentFamilyRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class PerfumeService {

    PerfumeRepository perfumeRepository;
    BrandRepository brandRepository;
    ScrentFamilyRepository screntFamilyRepository;
    CountryRepository countryRepository;

    PerfumeMapper perfumeMapper;

    // Lấy danh sách nước hoa
    public List<PerfumeResponse> getAllPerfumes() {
        List<Perfume> perfumes = perfumeRepository.findAll();
        return perfumes.stream()
                .map(perfumeMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Lấy thông tin nước hoa theo ID
    public PerfumeResponse getPerfumeById(int id) {
        Perfume perfume = perfumeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERFUME_NOT_EXISTED));
        return perfumeMapper.toResponse(perfume);
    }

    // Tạo mới nước hoa
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public PerfumeResponse createPerfume(PerfumeRequest request) {
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        ScrentFamily screntFamily = screntFamilyRepository.findById(request.getScrentFamilyId())
                .orElseThrow(() -> new AppException(ErrorCode.SCRENT_FAMILY_NOT_EXISTED));
        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new AppException(ErrorCode.COUNTRY_NOT_EXISTED));

        Perfume perfume = perfumeMapper.toEntity(request, brand, screntFamily, country);
        perfume = perfumeRepository.save(perfume);
        return perfumeMapper.toResponse(perfume);
    }

    // Cập nhật nước hoa
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public PerfumeResponse updatePerfume(int id, PerfumeRequest request) {
        Perfume perfume = perfumeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERFUME_NOT_EXISTED));

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        ScrentFamily screntFamily = screntFamilyRepository.findById(request.getScrentFamilyId())
                .orElseThrow(() -> new AppException(ErrorCode.SCRENT_FAMILY_NOT_EXISTED));
        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new AppException(ErrorCode.COUNTRY_NOT_EXISTED));

        perfumeMapper.updateEntity(perfume, request, brand, screntFamily, country);
        perfume = perfumeRepository.save(perfume);
        return perfumeMapper.toResponse(perfume);
    }

    // Xóa nước hoa
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deletePerfume(int id) {
        if (!perfumeRepository.existsById(id)) {
            throw new AppException(ErrorCode.PERFUME_NOT_EXISTED);
        }
        perfumeRepository.deleteById(id);
    }

    //Flash sale
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void toggleFlashSale(Integer perfumeId, boolean isFlashSale){
        if (!perfumeRepository.existsById(perfumeId)) {
            throw new AppException(ErrorCode.PERFUME_NOT_EXISTED);
        }

        perfumeRepository.updateFlashSaleStatus(perfumeId, isFlashSale);
    }

    public List<PerfumeResponse> getFlashSalePerfumes(){

        return perfumeRepository.findAllFlashSaleProducts().stream()
                .map(perfumeMapper::toResponse)
                .collect(Collectors.toList());
    }
}
