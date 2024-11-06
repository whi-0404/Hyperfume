package com.Hyperfume.Backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UserResponse {
    String username;
    String email;
    String fullname;
    String phone;
    String address;
    LocalDate dob;
    Set<String> roles;
}
