package com.tutorial.identity.controller;

import com.nimbusds.jose.JOSEException;
import com.tutorial.identity.dto.request.AuthenticationRequest;
import com.tutorial.identity.dto.request.IntrospectRequest;
import com.tutorial.identity.dto.request.LogoutRequest;
import com.tutorial.identity.dto.request.RefreshToken;
import com.tutorial.identity.dto.response.ApiResponse;
import com.tutorial.identity.dto.response.AuthenticationResponse;
import com.tutorial.identity.dto.response.IntrospectResponse;
import com.tutorial.identity.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Login by username & password", description = "API login")
    @PostMapping("/log-in")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        var result = authenticationService.isAuthenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }

    @Operation(summary = "Logout", description = "API logout")
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @Operation(summary = "Refresh token", description = "API refresh token")
    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshToken request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .code(HttpStatus.OK.value())
                .build();
    }

    @Operation(summary = "Introspect token", description = "API introspect token")
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
