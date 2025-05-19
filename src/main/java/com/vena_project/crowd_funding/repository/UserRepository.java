package com.vena_project.crowd_funding.repository;

import com.vena_project.crowd_funding.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Email(message = "provide a valid email address") @NotBlank String email);
}
