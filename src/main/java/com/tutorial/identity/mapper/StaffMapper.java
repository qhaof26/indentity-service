package com.tutorial.identity.mapper;

import com.tutorial.identity.dto.request.StaffCreationRequest;
import com.tutorial.identity.dto.response.StaffResponse;
import com.tutorial.identity.entity.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    public StaffResponse toStaffResponse(Staff staff);
    public Staff toStaff(StaffCreationRequest staffCreationRequest);
    public void updateStaff(@MappingTarget Staff staff, StaffCreationRequest request);
}
