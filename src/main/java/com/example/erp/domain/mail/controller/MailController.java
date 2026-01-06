package com.example.erp.domain.mail.controller;

import com.example.erp.domain.mail.dto.CreateMailDto;
import com.example.erp.domain.mail.dto.MailDetialDto;
import com.example.erp.domain.mail.service.MailFacadeService;

import com.example.erp.domain.receivedmail.dto.ReceivedMailDto;
import com.example.erp.domain.receivedmail.dto.req.DeleteReceivedMailReqDto;
import com.example.erp.domain.receivedmail.dto.resp.DeleteReceivedMailRespDto;
import com.example.erp.domain.sendmail.dto.SendMailDto;
import com.example.erp.domain.sendmail.dto.req.DeleteSendMailReqDto;
import com.example.erp.domain.sendmail.dto.resp.DeleteSendMailRespDto;
import com.example.erp.domain.user.dto.UserInfo;
import com.example.erp.domain.user.entity.User;
import com.example.erp.global.config.argumentresolver.Login;
import com.example.erp.global.responseDto.ResponseFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.erp.domain.user.entity.QUser.user;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MailController {

    private final MailFacadeService mailFacadeService;

    @GetMapping("/user/received-mail")
    public String userReceivedMail(@PageableDefault Pageable pageable, @Login UserInfo userInfo, Model model) {


        Page<ReceivedMailDto> receivedMails = mailFacadeService.getReceivedMails(pageable, userInfo.getUserNum());
        model.addAttribute("receivedMails", receivedMails);
        model.addAttribute("userInfo", userInfo);
        return "user/mail";
    }

    @GetMapping("/admin/received-mail")
    public String adminReceivedMail(@PageableDefault Pageable pageable, @Login UserInfo userInfo, Model model) {


        Page<ReceivedMailDto> receivedMails = mailFacadeService.getReceivedMails(pageable, userInfo.getUserNum());
        model.addAttribute("receivedMails", receivedMails);
        model.addAttribute("userInfo", userInfo);
        return "admin/mail";
    }

    @GetMapping("/user/received-mail/search")
    public String findUserReceivedMailByTitle(@RequestParam("title") String title,
                                              @PageableDefault Pageable pageable,
                                              @Login UserInfo userInfo,
                                              Model model) {


        Page<ReceivedMailDto> receivedMails = mailFacadeService
                .getReceivedMailsByTitle(pageable, userInfo.getUserNum(), title);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("receivedMails", receivedMails);
        return "user/mail";
    }

    @GetMapping("/admin/received-mail/search")
    public String findAdminReceivedMailByTitle(@RequestParam("title") String title,
                                              @PageableDefault Pageable pageable,
                                              @Login UserInfo userInfo,
                                              Model model) {


        Page<ReceivedMailDto> receivedMails = mailFacadeService
                .getReceivedMailsByTitle(pageable, userInfo.getUserNum(), title);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("receivedMails", receivedMails);
        return "admin/mail";
    }

    //페일폼반환
    @GetMapping("/user/mail/forms")
    public String mailForms(@Login UserInfo userInfo, Model model) {
        model.addAttribute("userInfo", userInfo);
        return "user/send-mail";
    }

    //페일폼반환
    @GetMapping("/admin/mail/forms")
    public String adminMailForms(@Login UserInfo userInfo, Model model) {
        model.addAttribute("userInfo", userInfo);
        return "admin/send-mail";
    }

    //유저의 메일 생성
    @PostMapping(value = "/user/mail/new")
    public String createUserMail(@ModelAttribute CreateMailDto createMailDto,
                             @Login UserInfo userInfo) {
        mailFacadeService.saveMail(userInfo.getUserNum(), createMailDto);
        return "redirect:/user/received-mail";
    }


    //어드민의 메일 생성
    @PostMapping("/admin/mail/new")
    public String createAdminMail(@ModelAttribute CreateMailDto createMailDto,
                             @Login UserInfo userInfo) {
        mailFacadeService.saveMail(userInfo.getUserNum(), createMailDto);
        return "redirect:/admin/received-mail";
    }

    // 메일 제거
    @DeleteMapping("/user/mail/delete")
    @ResponseBody
    public ResponseEntity<ResponseFormat<DeleteReceivedMailRespDto>> deleteUserMail(@RequestBody DeleteReceivedMailReqDto deleteReceivedMailReqDto,
                                                            @Login UserInfo userInfo) {

        DeleteReceivedMailRespDto deleteReceivedMailRespDto = mailFacadeService.deleteReceivedMail(deleteReceivedMailReqDto.ids(), userInfo.getUserNum());

        return ResponseEntity.ok().body(ResponseFormat.of("받은 메일 제거", deleteReceivedMailRespDto));

    }

    // 메일 제거
    @DeleteMapping("/admin/mail/delete")
    @ResponseBody
    public ResponseEntity<ResponseFormat<DeleteReceivedMailRespDto>> deleteAdminMail(@RequestBody DeleteReceivedMailReqDto deleteReceivedMailReqDto,
                                                                                     @Login UserInfo userInfo) {

        DeleteReceivedMailRespDto deleteReceivedMailRespDto = mailFacadeService.deleteReceivedMail(deleteReceivedMailReqDto.ids(), userInfo.getUserNum());

        return ResponseEntity.ok().body(ResponseFormat.of("받은 메일 제거", deleteReceivedMailRespDto));

    }


    // 보낸 메일 제거
    @DeleteMapping("/user/send-mail/delete")
    @ResponseBody
    public ResponseEntity<ResponseFormat<DeleteSendMailRespDto>> deleteUserSendMail(@Login UserInfo userInfo,
                                                                                    @RequestBody DeleteSendMailReqDto deleteSendMailReqDto) {
        DeleteSendMailRespDto deleteSendMailRespDto = mailFacadeService.deleteSendMails(deleteSendMailReqDto.ids(), userInfo.getUserNum());

        return ResponseEntity.ok().body(ResponseFormat.of("보낸메일 제거", deleteSendMailRespDto));
    }

    // 보낸 메일 제거
    @DeleteMapping("/admin/send-mail/delete")
    @ResponseBody
    public ResponseEntity<ResponseFormat<DeleteSendMailRespDto>> deleteAdminSendMail(@Login UserInfo userInfo,
                                                                                    @RequestBody DeleteSendMailReqDto deleteSendMailReqDto) {
        DeleteSendMailRespDto deleteSendMailRespDto = mailFacadeService.deleteSendMails(deleteSendMailReqDto.ids(), userInfo.getUserNum());

        return ResponseEntity.ok().body(ResponseFormat.of("보낸메일 제거", deleteSendMailRespDto));
    }

    // 받은 메일 정보
    @GetMapping("/user/mail/details")
    public String userMailDetails(Model model,
                                  @RequestParam("no") Long no,
                                  @Login UserInfo userInfo) {

        MailDetialDto mail = mailFacadeService.findMail(no);
        model.addAttribute("mail", mail);
        model.addAttribute("userInfo", userInfo);
        return "user/mail-details";
    }

    // 받은 메일 정보
    @GetMapping("/admin/mail/details")
    public String adminMailDetails(Model model,
                                  @RequestParam("no") Long no,
                                  @Login UserInfo userInfo) {

        MailDetialDto mail = mailFacadeService.findMail(no);
        model.addAttribute("mail", mail);
        model.addAttribute("userInfo", userInfo);
        return "admin/mail-details";
    }

    @GetMapping("/admin/send-mail/details")
    public String adminSendMailDetails(Model model,
                                       @Login UserInfo userInfo,
                                       @RequestParam("no") Long no) {
        model.addAttribute("userInfo", userInfo);
        MailDetialDto mail = mailFacadeService.findSendMailDetail(no);
        model.addAttribute("mail", mail);
        return "admin/send-mail-details";
    }

    @GetMapping("/user/send-mail/details")
    public String userSendMailDetails(Model model,
                                       @Login UserInfo userInfo,
                                       @RequestParam("no") Long no) {
        model.addAttribute("userInfo", userInfo);
        MailDetialDto mail = mailFacadeService.findSendMailDetail(no);
        model.addAttribute("mail", mail);
        return "admin/send-mail-details";
    }


    // 보낸메일함
    @GetMapping("/user/send-mail")
    public String userSendMails(@PageableDefault Pageable pageable,
                                @Login UserInfo userInfo,
                                Model model) {
        model.addAttribute("userInfo", userInfo);
        Page<SendMailDto> sendMails = mailFacadeService.findSendMails(pageable, userInfo.getUserNum());
        model.addAttribute("sendMails", sendMails);
        return "user/send-mail-list";
    }

    // 보낸메일함
    @GetMapping("/admin/send-mail")
    public String adminSendMails(@PageableDefault Pageable pageable,
                                @Login UserInfo userInfo,
                                Model model) {
        model.addAttribute("userInfo", userInfo);
        Page<SendMailDto> sendMails = mailFacadeService.findSendMails(pageable, userInfo.getUserNum());
        model.addAttribute("sendMails", sendMails);
        return "admin/send-mail-list";
    }


    @GetMapping("/user/send-mail/search")
    public String userSendMailByTitle(@RequestParam("title") String title,
                                      @PageableDefault Pageable pageable,
                                      @Login UserInfo userInfo,
                                      Model model) {
        model.addAttribute("userInfo", userInfo);
        Page<SendMailDto> receivedMails = mailFacadeService.getSendMailsByTitle(pageable, userInfo.getUserNum(), title);
        model.addAttribute("receivedMails", receivedMails);
        return "user/send-mail-list";
    }

    @GetMapping("/admin/send-mail/search")
    public String adminSendMailByTitle(@RequestParam("title") String title,
                                      @PageableDefault Pageable pageable,
                                      @Login UserInfo userInfo,
                                      Model model) {
        model.addAttribute("userInfo", userInfo);
        Page<SendMailDto> receivedMails = mailFacadeService.getSendMailsByTitle(pageable, userInfo.getUserNum(), title);
        model.addAttribute("receivedMails", receivedMails);
        return "admin/send-mail-list";
    }
}
