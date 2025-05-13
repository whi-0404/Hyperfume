package com.Hyperfume.Backend.repository;

import java.util.Arrays;
import java.util.Optional;

import com.Hyperfume.Backend.entity.Perfume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Hyperfume.Backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String name);

    Optional<User> findByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.active = false WHERE u.id = :userId")
    void deactivateUser(@Param("userId") Integer userId);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.active = true")
    Optional<User> findActiveUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.favoritePerfumes WHERE u.username = :username")
    Optional<User> findByNameWithFavorites(@Param("username") String username);

    @Query("SELECT p FROM User u JOIN u.favoritePerfumes p WHERE u.username = :username")
    Page<Perfume> findFavoritePerfumesByUsername(@Param("username") String username, Pageable pageable);

    @Query("SELECT COUNT(p) > 0 FROM User u JOIN u.favoritePerfumes p WHERE u.username = :username " +
            "AND p.id = :perfumeId")
    boolean checkFavoritePerfumeByUsername(@Param("username") String username, @Param("perfumeId") Integer perfumeId);

    Optional<User> findByRoleId(int roleId);
}
