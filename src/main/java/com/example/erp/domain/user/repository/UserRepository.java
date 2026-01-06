package com.example.erp.domain.user.repository;

import com.example.erp.domain.mail.entity.Mail;
import com.example.erp.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(String userId);
}
