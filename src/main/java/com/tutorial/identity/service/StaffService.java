package com.tutorial.identity.service;

import com.tutorial.identity.dto.request.StaffCreationRequest;
import com.tutorial.identity.entity.Staff;
import com.tutorial.identity.exception.AppException;
import com.tutorial.identity.exception.Errorcode;
import com.tutorial.identity.mapper.StaffMapper;
import com.tutorial.identity.repository.StaffRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    private final StaffRepository staffRepository;

    private final StaffMapper staffMapper;

    @Autowired
    public StaffService(StaffRepository staffRepository, StaffMapper staffMapper){
        this.staffRepository = staffRepository;
        this.staffMapper = staffMapper;
    }
    public List<Staff> getAllStaff(){
        return staffRepository.findAll();
    }
    public Staff getStaffById(String id){
        if(!staffRepository.existsStaffById(id)){
            throw new AppException(Errorcode.USER_NOTFOUND);
        }
        return staffRepository.getStaffById(id);
    }
    @Transactional
    public Staff createStaff(StaffCreationRequest staffCreationRequest){
        if(staffRepository.existsStaffByUserName(staffCreationRequest.getUserName())){
            throw new AppException(Errorcode.USER_EXISTED);
        }
        return staffRepository.save(staffMapper.toStaff(staffCreationRequest));
    }
    @Transactional
    public Staff updateStaff(String id, StaffCreationRequest staffCreationRequest){
        if(!staffRepository.existsStaffById(id)){
            throw new AppException(Errorcode.USER_NOTFOUND);
        }
        Staff staff = staffRepository.getStaffById(id);
        staff.setFullName(staffCreationRequest.getFullName());
        staff.setDob(staffCreationRequest.getDob());
        staff.setUserName(staffCreationRequest.getUserName());
        staff.setPassWord(staffCreationRequest.getPassWord());
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
