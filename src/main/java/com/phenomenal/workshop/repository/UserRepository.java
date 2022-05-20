package com.phenomenal.workshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.phenomenal.workshop.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	Optional<User>findByUsername(String username);
}
