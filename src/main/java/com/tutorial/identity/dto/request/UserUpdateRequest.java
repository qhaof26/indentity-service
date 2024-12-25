package com.tutorial.identity.dto.request;

import com.tutorial.identity.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;
    @NotBlank(message = "INVALID_DATA")
    String firstName;
    @NotBlank(message = "INVALID_DATA")
    String lastName;
    @DobConstraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;
    List<String> roles;
}
