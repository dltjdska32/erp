package com.example.erp.domain.receivedmail.repository;

import com.example.erp.domain.mail.entity.Mail;
import com.example.erp.domain.receivedmail.entity.ReceivedMail;
import com.example.erp.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReceivedRepository extends JpaRepository<ReceivedMail, Long>, ReceivedRepositoryCustom {
    List<ReceivedMail> findAllByUser(User user);

    List<ReceivedMail> findAllByUserAndReceivedNumIn(User user, List<Long> ids);

    Optional<ReceivedMail> findByMail(Mail mail);
}
