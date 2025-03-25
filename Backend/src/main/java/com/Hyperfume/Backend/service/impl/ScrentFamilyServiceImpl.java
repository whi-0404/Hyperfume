package com.Hyperfume.Backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.Hyperfume.Backend.dto.request.ScrentFamilyRequest;
import com.Hyperfume.Backend.dto.response.ScrentFamilyResponse;
import com.Hyperfume.Backend.entity.ScrentFamily;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.ScrentFamilyMapper;
import com.Hyperfume.Backend.repository.ScrentFamilyRepository;
import com.Hyperfume.Backend.service.ScrentFamilyService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScrentFamilyServiceImpl implements ScrentFamilyService {
    ScrentFamilyRepository screntFamilyRepository;
    ScrentFamilyMapper screntFamilyMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public ScrentFamilyResponse createScrentFamily(ScrentFamilyRequest request) {
        if (screntFamilyRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.SCRENT_FAMILY_EXISTED);

        ScrentFamily screntFamily = screntFamilyMapper.toScrentFamily(request);

        return screntFamilyMapper.toScrentFamilyResponse(screntFamilyRepository.save(screntFamily));
    }

    public List<ScrentFamilyResponse> getScrentFamilies() {

        return screntFamilyRepository.findAll().stream()
                .map(screntFamilyMapper::toScrentFamilyResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ScrentFamilyResponse getScrentFamily(Integer screntFamilyId) {
        return screntFamilyMapper.toScrentFamilyResponse(screntFamilyRepository
                .findById(screntFamilyId)
                .orElseThrow(() -> new AppException(ErrorCode.SCRENT_FAMILY_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ScrentFamilyResponse updateScrentFamily(Integer screntFamilyId, ScrentFamilyRequest request) {
        ScrentFamily screntFamily = screntFamilyRepository
                .findById(screntFamilyId)
                .orElseThrow(() -> new AppException(ErrorCode.SCRENT_FAMILY_NOT_EXISTED));
        screntFamilyMapper.updateScrentFamily(screntFamily, request);

        return screntFamilyMapper.toScrentFamilyResponse(screntFamilyRepository.save(screntFamily));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteScrentFamily(Integer screntFamilyId) {
        screntFamilyRepository.deleteById(screntFamilyId);
    }
}
