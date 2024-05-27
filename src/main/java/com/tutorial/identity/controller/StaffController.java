package com.tutorial.identity.controller;

import com.tutorial.identity.dto.request.StaffCreationRequest;
import com.tutorial.identity.dto.response.ApiResponse;
import com.tutorial.identity.dto.response.StaffResponse;
import com.tutorial.identity.mapper.StaffMapper;
import com.tutorial.identity.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/staffs")
public class StaffController {
    private final StaffService staffService;
    private final StaffMapper staffMapper;

    // Read all staff
    @GetMapping
    public List<StaffResponse> getAllStaff(){
        List<StaffResponse> list = new ArrayList<>();
        staffService.getAllStaff().forEach(staff -> list.add(staffMapper.toStaffResponse(staff)));
        return list;
    }
    // Read 1 staff
    @GetMapping("/{id}")
    public ApiResponse<StaffResponse> getStaffById(@PathVariable String id){
        return new ApiResponse<>(200, "Get staff success !", staffMapper.toStaffResponse(staffService.getStaffById(id)));
    }
    // Create staff
    @PostMapping("/add")
    public ApiResponse<StaffResponse> newStaff(@RequestBody @Valid StaffCreationRequest staffCreationRequest){
        return new ApiResponse<>(200, "Create staff success !", staffMapper.toStaffResponse(staffService.createStaff(staffCreationRequest)));
    }
    // Update staff
    @PutMapping("/{id}")
    public ApiResponse<StaffResponse> updateStaff(@PathVariable String id, @RequestBody @Valid StaffCreationRequest staffCreationRequest){
        return new ApiResponse<>(200, "Updated Staff !", staffMapper.toStaffResponse(staffService.updateStaff(id, staffCreationRequest)));
    }
    // Delete staff
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStaff(@PathVariable String id){
        staffService.deleteStaff(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(200);
        apiResponse.setMessage("Deleted Staff !");
        return ResponseEntity.ok().body(apiResponse);
    }
}
