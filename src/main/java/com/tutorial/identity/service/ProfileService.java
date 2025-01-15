package com.tutorial.identity.service;

import com.tutorial.identity.dto.request.ProfileCreationRequest;
import com.tutorial.identity.dto.response.ProfileResponse;
import com.tutorial.identity.entity.Profile;
import com.tutorial.identity.entity.User;
import com.tutorial.identity.mapper.ProfileMapper;
import com.tutorial.identity.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProfileService {
    ProfileRepository profileRepository;
    UploadService uploadService;
    ProfileMapper profileMapper;

    public ProfileResponse createProfile(ProfileCreationRequest request, MultipartFile avatar) throws IOException {
        User user = request.getUser();
        Profile profile = Profile.builder()
                .avatar(uploadService.uploadImage(avatar))
                .description(request.getDescription())
                .user(user)
                .build();
        profileRepository.save(profile);
        return profileMapper.toProfileResponse(profile);
    }
}
