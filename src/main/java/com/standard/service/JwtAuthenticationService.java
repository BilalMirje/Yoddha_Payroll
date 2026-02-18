package com.standard.service;

import com.google.zxing.WriterException;
import com.standard.entity.dtos.auth.*;


import com.standard.payload.ApiResponse;

import java.io.IOException;

public interface JwtAuthenticationService {
    ApiResponse<?> loginUser(JwtRequest request);
    ApiResponse<?> logoutUser(String username);
    ApiResponse<?> sendForgetPasswordLink(ForgotPasswordRequest request);
    ApiResponse<?> resetPassword(ResetPasswordRequest request);
    ApiResponse<?> loginWithOtp(LoginOTP loginOTP);
    ApiResponse<?> enableTwoFactorAuthentication(String username) throws WriterException, IOException;
    ApiResponse<?> disableTwoFactorAuthentication(String username, VerifyOTP verifyOTP);
    ApiResponse<?> verifyOtp(String authHeader,VerifyOTP verifyOTP);
}
