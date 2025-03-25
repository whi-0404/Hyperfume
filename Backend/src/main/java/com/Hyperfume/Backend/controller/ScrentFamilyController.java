package com.Hyperfume.Backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.Hyperfume.Backend.dto.request.ScrentFamilyRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.ScrentFamilyResponse;
import com.Hyperfume.Backend.service.ScrentFamilyService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/screntFamilies")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ScrentFamilyController {
    ScrentFamilyService screntFamilyService;

    @PostMapping
    ApiResponse<ScrentFamilyResponse> createScrentFamily(@RequestBody @Valid ScrentFamilyRequest request) {
        return ApiResponse.<ScrentFamilyResponse>builder()
                .result(screntFamilyService.createScrentFamily(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<ScrentFamilyResponse>> getScrentFamilies() {

        return ApiResponse.<List<ScrentFamilyResponse>>builder()
                .result(screntFamilyService.getScrentFamilies())
                .build();
    }

    @GetMapping("/{ScrentFamilyId}")
    ApiResponse<ScrentFamilyResponse> getScrentFamily(@PathVariable("ScrentFamilyId") Integer screntFamilyId) {
        return ApiResponse.<ScrentFamilyResponse>builder()
                .result(screntFamilyService.getScrentFamily(screntFamilyId))
                .build();
    }

    @PutMapping("/{ScrentFamilyId}")
    ApiResponse<ScrentFamilyResponse> updateScrentFamily(
            @PathVariable("ScrentFamilyId") Integer screntFamilyId, @RequestBody @Valid ScrentFamilyRequest request) {
        return ApiResponse.<ScrentFamilyResponse>builder()
                .result(screntFamilyService.updateScrentFamily(screntFamilyId, request))
                .build();
    }

    @DeleteMapping("/{ScrentFamilyId}")
    ApiResponse<String> deleteScrentFamily(@PathVariable Integer screntFamilyId) {
        screntFamilyService.deleteScrentFamily(screntFamilyId);
        return ApiResponse.<String>builder()
                .result("Scrent Family has been deleted")
                .build();
    }
}
