package com.Hyperfume.Backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Hyperfume.Backend.dto.request.RoleRequest;
import com.Hyperfume.Backend.dto.response.RoleResponse;
import com.Hyperfume.Backend.mapper.RoleMapper;
import com.Hyperfume.Backend.repository.RoleRepository;
import com.Hyperfume.Backend.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(Integer role) {
        roleRepository.deleteById(role);
    }
}
