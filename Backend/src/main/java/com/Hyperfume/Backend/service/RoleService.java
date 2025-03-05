package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.RoleRequest;
import com.Hyperfume.Backend.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);
    List<RoleResponse> getAll();

    void delete(Integer role);
}
