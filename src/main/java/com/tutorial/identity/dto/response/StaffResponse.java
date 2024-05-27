package com.tutorial.identity.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StaffResponse {
    private String id;
    private String fullName;
    private LocalDate dob;
    private String userName;
    private String passWord;
}
