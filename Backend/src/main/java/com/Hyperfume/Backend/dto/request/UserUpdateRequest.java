package com.Hyperfume.Backend.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String email;
    String fullname;
    String phone;
    String address;
    LocalDate dob;
}