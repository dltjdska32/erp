package com.example.erp.domain.receivedmail.service;

import com.example.erp.domain.mail.entity.Mail;
import com.example.erp.domain.receivedmail.dto.ReceivedMailDto;
import com.example.erp.domain.receivedmail.entity.ReceivedMail;
import com.example.erp.domain.receivedmail.exception.ReceivedMailException;
import com.example.erp.domain.receivedmail.repository.ReceivedRepository;
import com.example.erp.domain.user.entity.User;
import com.example.erp.global.exception.custom.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReceivedMailService {
    private final ReceivedRepository receivedRepository;

    public Page<ReceivedMailDto> getReceivedMails(Pageable pageable, User user) {
        return receivedRepository.findReceivedMails(pageable, user);
    }

    public Page<ReceivedMailDto> getReceivedMailsByTitle(Pageable pageable, User user, String title) {
        return receivedRepository.findReceivedMailsByTitle(pageable, user, title);
    }

    @Transactional
    public void save(ReceivedMail receivedMail) {
        receivedRepository.save(receivedMail);
    }

    public List<ReceivedMail> findReceivedMailByUser(User user) {
        return receivedRepository.findAllByUser(user);
    }

    @Transactional
    public void deleteReceivedMails(User user, List<Long> ids) {
//        for (ReceivedMail receivedMail : findReceivedMailByUser(user)) {
//            for (Long id : ids) {
//                if(receivedMail.getReceivedNum().equals(id)){
//                    receivedMail.deleteMail(true);
//                }
//            }
//        }

        List<ReceivedMail> mailsToDelete = receivedRepository.findAllByUserAndReceivedNumIn(user, ids);
        for (ReceivedMail mail : mailsToDelete) {
            mail.deleteMail(true);
        }
    }

    public ReceivedMail findById(Long no) {
        return receivedRepository.findById(no).orElseThrow(() -> new CustomException(ReceivedMailException.RECEIVED_MAIL_NOT_FOUND));
    }

    public ReceivedMail findReceivedMailByMail(Mail mail) {
        return receivedRepository.findByMail(mail).orElseThrow(() -> new CustomException(ReceivedMailException.RECEIVED_MAIL_NOT_FOUND));
    }
}
