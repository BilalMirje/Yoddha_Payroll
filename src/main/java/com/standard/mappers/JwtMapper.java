package com.standard.mappers;

import com.standard.entity.AppUser;
import com.standard.entity.dtos.auth.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtMapper {
    private final RoleMapper roleMapper;
    public JwtResponse mapToJwtResponse(AppUser appUser, String jwtToken){
        return JwtResponse.builder()
                .userId(appUser.getId())
                .username(appUser.getUsername())
                .jwtToken(jwtToken)
                .isUserLoggedIn(appUser.getIsUserLoggedIn())
                .imageUrl(appUser.getImageUrl())
                .multiFactor(appUser.getMultiFactor())
                .role(
                        roleMapper.mapToRoleResponse(appUser.getRole())
                )
                .build();
    }
}
