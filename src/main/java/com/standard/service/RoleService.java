package com.standard.service;

import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.entity.dtos.roleDtos.RoleRequest;
import com.standard.payload.ApiResponse;

import java.util.UUID;

public interface RoleService {
    ApiResponse<?> createRole(RoleRequest request);
    ApiResponse<?> getAllRoles(PageRequest pageRequest);
    ApiResponse<?> getRoleById(UUID id);
    ApiResponse<?> getRolesByName(String name,PageRequest pageRequest);
    ApiResponse<?> updateRole(UUID id, RoleRequest request);
    ApiResponse<?> deleteRole(UUID id);
}
