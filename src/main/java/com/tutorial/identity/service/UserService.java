package com.tutorial.identity.service;

import com.tutorial.identity.dto.request.UserCreationRequest;
import com.tutorial.identity.dto.request.UserUpdateRequest;
import com.tutorial.identity.dto.response.PageResponse;
import com.tutorial.identity.dto.response.UserResponse;
import com.tutorial.identity.entity.User;
import com.tutorial.identity.enums.Role;
import com.tutorial.identity.exception.AppException;
import com.tutorial.identity.exception.ErrorCode;
import com.tutorial.identity.mapper.UserMapper;
import com.tutorial.identity.repository.RoleRepository;
import com.tutorial.identity.repository.SearchRepository;
import com.tutorial.identity.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    SearchRepository searchRepository;

    @Transactional
    public UserResponse createUser(UserCreationRequest request){
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        //user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<Object> getUsers(int pageNo, int pageSize){
        log.info("In method get user");

        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        Page<User> pageUser = userRepository.findAll(pageable);
        List<UserResponse> result = pageUser.stream().map(userMapper::toUserResponse).toList();

        return PageResponse.builder()
                .page(pageable.getPageNumber()+1)
                .size(pageable.getPageSize())
                .totalPage(pageUser.getTotalPages())
                .totalElement(pageUser.getTotalElements())
                .items(result)
                .build();
    }

    public UserResponse getUser(String id){
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public boolean findUserByUserName(String username){
        return userRepository.existsByUsername(username);
    }

    public List<UserResponse> testCustomQuery(String firstName){
        return userRepository.findAllUserByFirstName(firstName)
                .stream().map(userMapper::toUserResponse).toList();
    }

    public PageResponse<Object> searchWithSpecifications(int pageNo, int pageSize, String firstName, String lastName, String username){
        Page<User> pageUser = searchRepository.findUsers(pageNo, pageSize, firstName, lastName, username);
        List<UserResponse> result = pageUser.stream().map(userMapper::toUserResponse).toList();

        return PageResponse.builder()
                .page(pageNo)
                .size(pageSize)
                .totalPage(pageUser.getTotalPages())
                .totalElement(pageUser.getTotalElements())
                .items(result)
                .build();
    }


}

