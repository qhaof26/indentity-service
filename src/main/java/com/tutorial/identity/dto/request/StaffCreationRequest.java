package com.tutorial.identity.dto.request;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class StaffCreationRequest {
    private String fullName;
    private LocalDate dob;
    @Size(min = 5, message = "Username must be at least 5 character !")
    private String userName;
    @Size(min = 4, message = "Password must be at least 4 character !")
    private String passWord;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
