package com.example.erp.domain.mail.service;


import com.example.erp.domain.mail.dto.MailDetialDto;
import com.example.erp.domain.mail.entity.Mail;
import com.example.erp.domain.mail.repository.MailRepository;
import com.example.erp.domain.receivedmail.entity.ReceivedMail;
import com.example.erp.domain.receivedmail.service.ReceivedMailService;
import com.example.erp.domain.sendmail.entity.SendMail;
import com.example.erp.domain.sendmail.service.SendMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MailService {
    private final MailRepository mailRepository;
    private final SendMailService sendMailService;
    private final ReceivedMailService receivedMailService;

    // 하나의 메일을 저장할때 메일 sendmail receivedMail 모두 하나의 트랜잭션안에 묶어줘야하기때문에
    // mailservice는 한번에실행
    @Transactional
    public void saveMail(Mail mail, ReceivedMail receivedMail, SendMail sendMail) {
        mailRepository.save(mail);
        receivedMailService.save(receivedMail);
        sendMailService.save(sendMail);
    }


}
