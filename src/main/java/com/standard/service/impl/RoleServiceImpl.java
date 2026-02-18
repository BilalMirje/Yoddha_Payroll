package com.standard.service.impl;

import com.standard.entity.Permission;
import com.standard.entity.Privilege;
import com.standard.entity.Role;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.entity.dtos.roleDtos.PermissionRequest;
import com.standard.entity.dtos.roleDtos.PrivilegeRequest;
import com.standard.entity.dtos.roleDtos.RoleRequest;
import com.standard.exceptions.handlers.DuplicateRecordException;
import com.standard.exceptions.handlers.ResourceInUseException;
import com.standard.exceptions.handlers.ResourceNotFoundException;
import com.standard.mappers.RoleMapper;
import com.standard.payload.ApiResponse;
import com.standard.queryHelper.QueryResultHandler;
import com.standard.repository.AppUserRepository;
import com.standard.repository.RoleRepository;
import com.standard.service.RoleService;
import com.standard.payload.ApiResponseUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final AppUserRepository appUserRepository;
    private final QueryResultHandler queryResultHandler;

    @Override
    @Transactional
    public ApiResponse<?> createRole(RoleRequest request){
        if(roleRepository.existsByRoleName(request.getRoleName())){
            throw new DuplicateRecordException("Role name already exist.");
        }

        Role role=roleMapper.mapToRoleEntity(request);
        Role savedRole=roleRepository.save(role);
        return ApiResponseUtil.created(roleMapper.mapToRoleResponse(savedRole));
    }

    @Override
    public ApiResponse<?> getAllRoles(PageRequest pageRequest){
        return queryResultHandler.fetchAndRespond(
                pageRequest,
                pageable -> roleRepository.findAll(pageable),
                sort -> roleRepository.findAll(sort),
                () -> roleRepository.findAll(),
                roleMapper::mapToRoleResponse,
                "Roles not found"
        );
    }

    @Override
    public ApiResponse<?> getRoleById(UUID id){
        Role role=roleRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Role not found with ID :"+id));

        return ApiResponseUtil.fetched(roleMapper.mapToRoleResponse(role));
    }

    @Override
    public ApiResponse<?> getRolesByName(String name,PageRequest pageRequest){
        return queryResultHandler.fetchAndRespond(
                pageRequest,
                pageable -> roleRepository.findByRoleNameContainingIgnoreCase(name, pageable),
                sort -> roleRepository.findByRoleNameContainingIgnoreCase(name, sort),
                () -> roleRepository.findByRoleNameContainingIgnoreCase(name),
                roleMapper::mapToRoleResponse,
                "Roles not found"
        );
    }

    @Override
    @Transactional
    public ApiResponse<?> updateRole(UUID id, RoleRequest request) {
        if(roleRepository.existsByRoleNameAndIdNot(request.getRoleName(),id)){
            throw new DuplicateRecordException("Role name already exist.");
        }

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID : " + id));

        // update role basic fields
        role.setRoleName(request.getRoleName());
        role.setRoleDescription(request.getRoleDescription());

        // existing permissions in DB
        List<Permission> existingPermissions = role.getPermissions();

        // map existing permissions by ID
        Map<UUID, Permission> permissionMap = existingPermissions.stream()
                .collect(Collectors.toMap(Permission::getId, p -> p));

        List<Permission> updatedPermissions = new ArrayList<>();

        for (PermissionRequest pr : request.getPermissions()) {

            // UPDATE EXISTING PERMISSION
            if (pr.getId() != null && permissionMap.containsKey(pr.getId())) {

                Permission permission = permissionMap.get(pr.getId());
                permission.setUserPermission(pr.getUserPermission());

                updatePrivilege(permission.getPrivilege(), pr.getPrivilege());

                updatedPermissions.add(permission);
                permissionMap.remove(pr.getId());
            }
            else {
                //new permission
                Permission newPermission = roleMapper.mapToPermissionEntity(pr, role);
                updatedPermissions.add(newPermission);
            }
        }

        // DELETE REMAINING (not present in request)
        permissionMap.values().forEach(p -> role.getPermissions().remove(p));

        role.getPermissions().clear();
        role.getPermissions().addAll(updatedPermissions);
        Role savedRole = roleRepository.save(role);

        return ApiResponseUtil.updated(roleMapper.mapToRoleResponse(savedRole));
    }


    @Override
    @Transactional
    public  ApiResponse<?> deleteRole(UUID id){
        Role role=roleRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Role not found with ID :"+id));

        if(appUserRepository.existsByRole_Id(role.getId())){
            throw new ResourceInUseException("Resource in use, can't delete.");
        }

        roleRepository.delete(role);
        return ApiResponseUtil.deleted();
    }

    //===private helper methods===
    private void updatePrivilege(Privilege existing, PrivilegeRequest request) {

        if (request == null) return;

        if (request.getReadPermission() != null)
            existing.setReadPermission(request.getReadPermission());

        if (request.getWritePermission() != null)
            existing.setWritePermission(request.getWritePermission());

        if (request.getUpdatePermission() != null)
            existing.setUpdatePermission(request.getUpdatePermission());

        if (request.getDeletePermission() != null)
            existing.setDeletePermission(request.getDeletePermission());
    }

}
