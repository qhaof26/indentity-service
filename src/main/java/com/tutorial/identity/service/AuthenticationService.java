package com.tutorial.identity.service;

import com.tutorial.identity.dto.request.AuthenticationRequest;
import com.tutorial.identity.exception.AppException;
import com.tutorial.identity.exception.Errorcode;
import com.tutorial.identity.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final StaffRepository staffRepository;

    public boolean isAuthenticate(AuthenticationRequest request){
        var staff = staffRepository.findStaffByUserName(request.getUserName())
                .orElseThrow(()-> new AppException(Errorcode.USER_NOTFOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(request.getPassWord(), staff.getPassWord());
    }
}
