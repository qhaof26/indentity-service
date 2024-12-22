package com.tutorial.identity.service;

import com.tutorial.identity.dto.request.StaffCreationRequest;
import com.tutorial.identity.entity.Staff;
import com.tutorial.identity.exception.AppException;
import com.tutorial.identity.exception.Errorcode;
import com.tutorial.identity.mapper.StaffMapper;
import com.tutorial.identity.repository.StaffRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;

    private final StaffMapper staffMapper;

    public List<Staff> getAllStaff(){
        return staffRepository.findAll();
    }

    public Staff getStaffById(String id){
        if(!staffRepository.existsStaffById(id)){
            throw new AppException(Errorcode.USER_NOTFOUND);
        }
        return staffRepository.getStaffById(id);
    }

    public Staff handleGetStaffByUserName(String username){
        return staffRepository.findStaffByUserName(username).orElseThrow(() -> new AppException(Errorcode.USER_NOTFOUND));
    }

    @Transactional
    public Staff createStaff(StaffCreationRequest staffCreationRequest){
        if(staffRepository.existsStaffByUserName(staffCreationRequest.getUserName())){
            throw new AppException(Errorcode.USER_EXISTED);
        }
        Staff staff = staffMapper.toStaff(staffCreationRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        staff.setPassWord(passwordEncoder.encode(staffCreationRequest.getPassWord()));
        return staffRepository.save(staff);
    }
    @Transactional
    public Staff updateStaff(String id, StaffCreationRequest staffCreationRequest){
        if(!staffRepository.existsStaffById(id)){
            throw new AppException(Errorcode.USER_NOTFOUND);
        }
        Staff staff = staffRepository.getStaffById(id);
        staffMapper.updateStaff(staff, staffCreationRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        staff.setPassWord(passwordEncoder.encode(staffCreationRequest.getPassWord()));
        return staffRepository.save(staff);
    }
    @Transactional
    public void deleteStaff(String id){
        if(!staffRepository.existsStaffById(id)){
            throw new AppException(Errorcode.USER_NOTFOUND);
        }
        staffRepository.deleteStaffById(id);
    }
}
