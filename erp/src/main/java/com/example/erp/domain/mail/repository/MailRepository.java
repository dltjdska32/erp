package com.example.erp.domain.mail.repository;

import com.example.erp.domain.mail.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail, Long>, MailRepositoryCustom {
}
