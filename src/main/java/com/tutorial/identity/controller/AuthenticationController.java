package com.tutorial.identity.controller;

import com.tutorial.identity.dto.request.AuthenticationRequest;
import com.tutorial.identity.dto.request.IntrospectRequest;
import com.tutorial.identity.dto.response.ApiResponse;
import com.tutorial.identity.dto.response.AuthenticationResponse;
import com.tutorial.identity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/log-in")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        var result = authenticationService.isAuthenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

//    @PostMapping("/introspect")
//    public ApiResponse<AuthenticationResponse> login(@RequestBody IntrospectRequest request){
//        //var result = authenticationService.isAuthenticate(request);
//        return ApiResponse.<AuthenticationResponse>builder()
//                .result(result)
//                .build();
//    }
}
