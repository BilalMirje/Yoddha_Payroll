package com.standard.service;

import com.standard.entity.dtos.appUserDtos.AppUserRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.payload.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AppUserService {
    ApiResponse<?> createUser(AppUserRequest request, MultipartFile file);
    ApiResponse<?> getAllUsers(PageRequest pageRequest);
    ApiResponse<?> getUserById(UUID id);
    ApiResponse<?> getUserByUsername(String name);
    ApiResponse<?> updateUser(UUID id,AppUserRequest request,MultipartFile file);
    ApiResponse<?> deleteUser(UUID id);

}
