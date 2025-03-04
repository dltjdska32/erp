package com.example.erp.domain.sendmail.repository;

import com.example.erp.domain.mail.entity.Mail;
import com.example.erp.domain.sendmail.entity.SendMail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SendMailRepository extends JpaRepository<SendMail, Long>, SendMailRepositoryCustom {
    Optional<SendMail> findByMail(Mail mail);
}
