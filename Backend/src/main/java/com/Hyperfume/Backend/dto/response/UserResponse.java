package com.Hyperfume.Backend.dto.response;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Integer id;
    String username;
    String email;
    String fullname;
    String gender;
    String phone;
    String address;
    LocalDate dob;
    RoleResponse role;
}
