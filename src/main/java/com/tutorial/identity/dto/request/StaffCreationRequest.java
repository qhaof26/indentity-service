package com.tutorial.identity.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StaffCreationRequest {
    private String fullName;
    private LocalDate dob;
    @Size(min = 5, message = "Username must be at least 5 character !")
    private String userName;
    @Size(min = 4, message = "Password must be at least 4 character !")
    private String passWord;
}
