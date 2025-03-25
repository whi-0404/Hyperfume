package com.Hyperfume.Backend.dto.request;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String email;
    String fullname;
    String phone;
    String address;
    LocalDate dob;
}
