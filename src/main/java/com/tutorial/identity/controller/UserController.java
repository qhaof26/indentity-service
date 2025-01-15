package com.tutorial.identity.controller;

import com.github.javafaker.Faker;
import com.tutorial.identity.dto.request.UserCreationRequest;
import com.tutorial.identity.dto.request.UserUpdateRequest;
import com.tutorial.identity.dto.response.ApiResponse;
import com.tutorial.identity.dto.response.PageResponse;
import com.tutorial.identity.dto.response.UserResponse;
import com.tutorial.identity.service.UploadService;
import com.tutorial.identity.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "User controller")
public class UserController {
    UserService userService;
    UploadService uploadService;
    @Operation(summary = "Add user", description = "API create new user")
    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @Operation(summary = "Create profile", description = "API Create profile")
    @PostMapping("/create-profile")
    ApiResponse<String> uploadAvatar(@RequestParam("file")MultipartFile file) throws IOException {
        return ApiResponse.<String>builder()
                .result(uploadService.uploadImage(file))
                .build();
    }

    @Operation(summary = "Fetch all user", description = "API fetch all user")
    @GetMapping
    ApiResponse<PageResponse<?>> getAllUser(
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ){
        var authentication =  SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication);
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<PageResponse<?>>builder()
                .code(HttpStatus.OK.value())
                .result(userService.getUsers(pageNo, pageSize))
                .build();
    }

    @Operation(summary = "Get my information", description = "API my information")
    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @Operation(summary = "Fetch user by id", description = "API fetch user by id")
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable("userId") String userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @Operation(summary = "Update user detail by id", description = "API update user by id")
    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @Operation(summary = "Delete user by id", description = "API delete user by id")
    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .result("User has been deleted")
                .build();
    }

    @Operation(summary = "Fake data user", description = "API fake data user")
    @PostMapping("/fake-data")
    public ResponseEntity<String> fakeData(){
        Faker faker = new Faker();
        for(int i = 0; i < 1000; i++){
            String username = faker.internet().emailAddress();
            if(userService.findUserByUserName(username)){
                continue;
            }
            UserCreationRequest userCreationRequest = UserCreationRequest.builder()
                    .username(username)
                    .password("123456")
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .dob(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .build();
            userService.createUser(userCreationRequest);
        }
        return ResponseEntity.ok("Added");
    }

    @Operation(summary = "Search user by custom query", description = "API Search user by custom query")
    @GetMapping("/custom-query")
    ApiResponse<List<UserResponse>> testCustomQuery(
            @RequestParam(value = "firstName") String firstName
    ){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.testCustomQuery(firstName))
                .build();
    }

    @Operation(summary = "Search user by specification", description = "API Search user by specification")
    @GetMapping("/search-multiple")
    ApiResponse<PageResponse<?>> searchUserBySpecification(
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String username
    ){

        return ApiResponse.<PageResponse<?>>builder()
                .code(HttpStatus.OK.value())
                .result(userService.searchWithSpecifications(pageNo, pageSize, firstName, lastName, username))
                .build();
    }
}

