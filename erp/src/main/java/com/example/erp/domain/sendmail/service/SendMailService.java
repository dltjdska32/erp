package com.example.erp.domain.sendmail.service;

import com.example.erp.domain.mail.entity.Mail;
import com.example.erp.domain.sendmail.dto.SendMailDto;
import com.example.erp.domain.sendmail.entity.SendMail;
import com.example.erp.domain.sendmail.exception.SendMailException;
import com.example.erp.domain.sendmail.repository.SendMailRepository;
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
public class SendMailService {
    private final SendMailRepository sendMailRepository;

    @Transactional
    public void save(SendMail sendMail) {
        sendMailRepository.save(sendMail);
    }

    @Transactional
    public void deleteSendMail(List<Long> ids, User user) {
        //모든 메일을 순회하면서 deiete처리
        for (Long id : ids) {
            SendMail sendMail = sendMailRepository.findById(id).orElseThrow(() -> new CustomException(SendMailException.Send_MAIL_NOT_FOUND));
            sendMail.deleteMail(true);
        }
    }

    public SendMail findSendMailByMail(Mail mail) {
        return sendMailRepository.findByMail(mail).orElseThrow(() -> new CustomException(SendMailException.Send_MAIL_NOT_FOUND));
    }

    public SendMail findById(Long no) {
        return sendMailRepository.findById(no).orElseThrow(()->new CustomException(SendMailException.Send_MAIL_NOT_FOUND));
    }

    public Page<SendMailDto> findSendMailDtosByUserNum(Pageable pageable, Long userNum) {
        return sendMailRepository.findSendMailDtosByUserNum(pageable,userNum);
    }

    public Page<SendMailDto> findSendMailDtosByUserNumAndTitle(Pageable pageable, Long userNum, String title) {
        return sendMailRepository.findSendMailDtosByUserNumAndTitle(pageable, userNum, title);
    }
}
