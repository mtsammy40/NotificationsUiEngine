package com.vsms.portal.data.repositories;

import com.vsms.portal.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmailOrPhoneLike(String email, String phone);
    Optional<User> findByEmail(String email);
}
