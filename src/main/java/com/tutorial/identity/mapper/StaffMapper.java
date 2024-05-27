package com.tutorial.identity.mapper;

import com.tutorial.identity.dto.request.StaffCreationRequest;
import com.tutorial.identity.dto.response.StaffResponse;
import com.tutorial.identity.entity.Staff;
import org.springframework.stereotype.Component;

@Component
public class StaffMapper {
    public StaffResponse toStaffResponse(Staff staff){
        StaffResponse staffResponse = new StaffResponse();
        staffResponse.setId(staff.getId());
        staffResponse.setFullName(staff.getFullName());
        staffResponse.setDob(staff.getDob());
        staffResponse.setUserName(staff.getUserName());
        staffResponse.setPassWord(staff.getPassWord());
        return staffResponse;
    }
    public Staff toStaff(StaffCreationRequest staffCreationRequest){
        Staff staff = new Staff();
        staff.setFullName(staffCreationRequest.getFullName());
        staff.setDob(staffCreationRequest.getDob());
        staff.setUserName(staffCreationRequest.getUserName());
        staff.setPassWord(staffCreationRequest.getPassWord());
        return staff;
    }
}
