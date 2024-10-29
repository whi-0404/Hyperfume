package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String name);
    Optional<User> findByUsername(String username);
}
