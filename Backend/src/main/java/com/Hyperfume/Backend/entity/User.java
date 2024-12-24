package com.Hyperfume.Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(name = "created_at", updatable = false)
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

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    Role role;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDate.now();
    }

    @ManyToMany
    @JoinTable(
            name = "favor_perfumes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "perfume_id")
    )
    Set<Perfume> favoritePerfumes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    List<ShippingAddress> shippingAddresses;
}