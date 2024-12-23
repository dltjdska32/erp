package test.erp_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import test.erp_project.domain.mail.Mail;

import test.erp_project.dto.mail_dto.MailDetialDto;

public interface MailRepository extends JpaRepository<Mail, Long> {




}
