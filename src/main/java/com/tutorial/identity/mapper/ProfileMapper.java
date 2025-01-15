package com.tutorial.identity.mapper;

import com.tutorial.identity.dto.response.ProfileResponse;
import com.tutorial.identity.entity.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    public ProfileResponse toProfileResponse(Profile profile){
        ProfileResponse.User user = new ProfileResponse.User();
        user.setUserId(profile.getUser().getId());
        user.setUsername(profile.getUser().getUsername());
        return ProfileResponse.builder()
                .id(profile.getId())
                .description(profile.getDescription())
                .avatar(profile.getAvatar())
                .user(user)
                .build();
    }
}
