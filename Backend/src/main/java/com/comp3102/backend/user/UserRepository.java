package com.comp3102.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByIdentityNumber(String identityNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
    long count();
}