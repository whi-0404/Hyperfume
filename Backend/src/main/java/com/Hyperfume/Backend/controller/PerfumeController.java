package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.request.PerfumeRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.PageResponse;
import com.Hyperfume.Backend.dto.response.PerfumeGetAllResponse;
import com.Hyperfume.Backend.dto.response.PerfumeResponse;
import com.Hyperfume.Backend.service.PerfumeService;
import com.Hyperfume.Backend.service.impl.PerfumeServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/perfumes")
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PerfumeController {

    PerfumeService perfumeService;

    @GetMapping("/byTypeName/{typeName}")
    public ApiResponse<PageResponse<PerfumeGetAllResponse>> getPerfumesByTypeName(
            @PathVariable String typeName,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "15") int size){
        return  ApiResponse.<PageResponse<PerfumeGetAllResponse>>builder()
                .result(perfumeService.getPerfumesByTypeName(typeName, page, size))
                .build();
    }

    @GetMapping("/byGender")
    public ApiResponse<PageResponse<PerfumeGetAllResponse>> getPerfumesByGender(
            @RequestParam("gender") String gender,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "15") int size){
        return  ApiResponse.<PageResponse<PerfumeGetAllResponse>>builder()
                .result(perfumeService.getPerfumesByGender(gender, page, size))
                .build();
    }

//    @GetMapping("/byBrand/{brandId}")
//    public String redirectToPerfumesByBrand(
//            @PathVariable int brandId,
//            RedirectAttributes redirectAttributes){
//
//        redirectAttributes.addAttribute("brandId", brandId);
//        return "redirect:/perfumes";
//    }

    @GetMapping("/byCountry/{countryId}")
    public ApiResponse<PageResponse<PerfumeGetAllResponse>> getPerfumesByCountry(
            @PathVariable int countryId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "15") int size){
        return  ApiResponse.<PageResponse<PerfumeGetAllResponse>>builder()
                .result(perfumeService.getPerfumesByCountry(countryId, page, size))
                .build();
    }

    @GetMapping("/byScrentFamily/{screntId}")
    public ApiResponse<PageResponse<PerfumeGetAllResponse>> getPerfumesByScrentFamily(
            @PathVariable int ScrentId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "15") int size){
        return  ApiResponse.<PageResponse<PerfumeGetAllResponse>>builder()
                .result(perfumeService.getPerfumesByScrentFamily(ScrentId, page, size))
                .build();
    }



    @GetMapping("/search")
    public ApiResponse<PageResponse<PerfumeGetAllResponse>> searchPerfumes(
            @RequestParam("name") String name,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "15") int size){
        return  ApiResponse.<PageResponse<PerfumeGetAllResponse>>builder()
                .result(perfumeService.searchPerfumesByName(name, page, size))
                .build();
    }

    // Lấy danh sách nước hoa
    @GetMapping
    public ApiResponse<PageResponse<PerfumeGetAllResponse>> getAllPerfumes(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "15") int size,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "sort", defaultValue = "latest") String sortOption,
            @RequestParam(value = "longevity", required = false) String longevity,
            @RequestParam(value = "countryId", required = false) Integer countryId,
            @RequestParam(value = "brandId", required = false) Integer brandId,
            @RequestParam(value = "concentration", required = false) String concentration,
            @RequestParam(value = "screntFamilyId", required = false) Integer screntFamilyId,
            @RequestParam(value = "maxPrice", required = false) Long maxPrice) {
        return ApiResponse.<PageResponse<PerfumeGetAllResponse>>builder()
                .result(perfumeService.getAllPerfumes(page, size, sortOption, gender, longevity, countryId, brandId,
                        concentration, screntFamilyId, maxPrice))
                .build();
    }

    // Lấy thông tin nước hoa theo ID
    @GetMapping("/{perfumeId}")
    public ApiResponse<PerfumeResponse> getPerfumeById(@PathVariable int perfumeId) {
        return ApiResponse.<PerfumeResponse>builder()
                .result(perfumeService.getPerfumeById(perfumeId))
                .build();
    }

    // Tạo mới nước hoa
    @PostMapping
    public ApiResponse<PerfumeResponse> createPerfume(@RequestBody @Valid PerfumeRequest request) {
        return ApiResponse.<PerfumeResponse>builder()
                .result(perfumeService.createPerfume(request))
                .build();
    }

    // Cập nhật nước hoa
    @PutMapping("/{perfumeId}")
    public ApiResponse<PerfumeResponse> updatePerfume(@PathVariable int perfumeId,
                                                      @RequestBody PerfumeRequest request) {
        return ApiResponse.<PerfumeResponse>builder()
                .result(perfumeService.updatePerfume(perfumeId, request))
                .build();
    }

    // Xóa nước hoa
    @DeleteMapping("/{perfumeId}")
    public ApiResponse<String> deletePerfume(@PathVariable int perfumeId) {
        perfumeService.deletePerfume(perfumeId);
        return ApiResponse.<String>builder()
                .result("Perfume has been deleted")
                .build();
    }

    @PutMapping("/{perfumeId}/flash-sale")
    public ApiResponse<String> toggleFlashSale(@PathVariable Integer perfumeId, @RequestParam boolean isFlashSale){
        perfumeService.toggleFlashSale(perfumeId, isFlashSale);
        return ApiResponse.<String>builder()
                .result("Successful")
                .build();
    }

    @GetMapping("/flash-sale")
    public ApiResponse<PageResponse<PerfumeGetAllResponse>> getFlashSalePerfumes(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "15") int size){
        return ApiResponse.<PageResponse<PerfumeGetAllResponse>>builder()
                .result(perfumeService.getFlashSalePerfumes(page, size))
                .build();
    }
}
