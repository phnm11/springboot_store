package com.nam.storespring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nam.storespring.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUserName(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUserName(String username);
}
