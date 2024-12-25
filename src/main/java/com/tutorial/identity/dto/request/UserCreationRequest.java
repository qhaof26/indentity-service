package com.tutorial.identity.dto.request;

import com.tutorial.identity.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 6,message = "USERNAME_INVALID")
    String username;
    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;
    @NotBlank(message = "INVALID_DATA")
    String firstName;
    @NotBlank(message = "INVALID_DATA")
    String lastName;
    @DobConstraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;
}