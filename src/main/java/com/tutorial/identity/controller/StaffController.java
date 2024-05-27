package com.tutorial.identity.controller;

import com.tutorial.identity.dto.request.StaffCreationRequest;
import com.tutorial.identity.dto.response.StaffResponse;
import com.tutorial.identity.entity.Staff;
import com.tutorial.identity.mapper.StaffMapper;
import com.tutorial.identity.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/staffs")
public class StaffController {
    private final StaffService staffService;
    private final StaffMapper staffMapper;

    @Autowired
    public StaffController(StaffService staffService, StaffMapper staffMapper){
        this.staffService = staffService;
        this.staffMapper = staffMapper;
    }
    // Read all staff
    @GetMapping
    public List<StaffResponse> getAllStaff(){
        List<StaffResponse> list = new ArrayList<>();
        staffService.getAllStaff().forEach(staff -> list.add(staffMapper.toStaffResponse(staff)));
        return list;
    }
    // Read 1 staff
    @GetMapping("/{id}")
    public StaffResponse getStaffById(@PathVariable String id){
        return staffMapper.toStaffResponse(staffService.getStaffById(id));
    }
    // Create staff
    @PostMapping("/add")
    public StaffResponse newStaff(@RequestBody StaffCreationRequest staffCreationRequest){
        return staffMapper.toStaffResponse(staffService.createStaff(staffCreationRequest));
    }
    // Update staff
    @PutMapping("/{id}")
    public StaffResponse updateStaff(@PathVariable String id, @RequestBody StaffCreationRequest staffCreationRequest){
        return staffMapper.toStaffResponse(staffService.updateStaff(id, staffCreationRequest));
    }
    // Delete staff
    @DeleteMapping("/{id}")
    public void deleteStaff(@PathVariable String id){
        staffService.deleteStaff(id);
    }
}
