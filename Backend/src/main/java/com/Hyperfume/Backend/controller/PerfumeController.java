package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.request.PerfumeRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.PerfumeGetAllResponse;
import com.Hyperfume.Backend.dto.response.PerfumeResponse;
import com.Hyperfume.Backend.service.PerfumeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfumes")
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PerfumeController {

    PerfumeService perfumeService;
    @GetMapping("/collections/type/{typeName}")
    public ApiResponse<List<PerfumeGetAllResponse>> getTypePerfume(@PathVariable String typeName){
        return  ApiResponse.<List<PerfumeGetAllResponse>>builder()
                .result(perfumeService.getTypePerfume(typeName))
                .build();
    }

    @GetMapping("/collections")
    public ApiResponse<List<PerfumeGetAllResponse>> getGenderPerfume(@RequestParam("gender") String gender){
        return  ApiResponse.<List<PerfumeGetAllResponse>>builder()
                .result(perfumeService.getGenderPerfume(gender))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<PerfumeGetAllResponse>> searchPerfumes(@RequestParam("name") String name){
        return  ApiResponse.<List<PerfumeGetAllResponse>>builder()
                .result(perfumeService.searchPerfumesByName(name))
                .build();
    }

    // Lấy danh sách nước hoa
    @GetMapping
    public ApiResponse<List<PerfumeGetAllResponse>> getAllPerfumes() {
        return ApiResponse.<List<PerfumeGetAllResponse>>builder()
                .result(perfumeService.getAllPerfumes())
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
    public ApiResponse<List<PerfumeGetAllResponse>> getFlashSalePerfumes(){
        return ApiResponse.<List<PerfumeGetAllResponse>>builder()
                .result(perfumeService.getFlashSalePerfumes())
                .build();
    }
}
