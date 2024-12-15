package com.Hyperfume.Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    int id;
    @Column(name = "user_name", nullable = false, unique = true)
    String username;
    @Column(nullable = false)
    String password;
    @Column(nullable = false, unique = true)
    String email;
    String fullname;
    @Column(nullable = false)
    String phone;
     String address;
    @Column(name = "created_at")
    LocalDate createdAt;
    @Column(name = "updated_at")
    LocalDate updatedAt;
    @Column(name = "is_active", nullable = true)
     boolean active;
    @Column(name="day_of_birth")
     LocalDate dob;
    @Column(name="facebook_acc_id", unique = true)
     int fbId;
    @Column(name="gmail_acc_id", unique = true)
     int gmId;
    @Column(name="role_id")
     int roleId;

}