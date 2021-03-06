package com.override.service;

import com.override.mapper.EmailMapper;
import dto.MailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    public EmailMapper emailMapper;

    @Override
    public void sendSimpleMail(MailDTO mailDTO) {
        List<SimpleMailMessage> messageList = emailMapper.dtoToListOfSimpleMails(mailDTO);
        try {
            for (SimpleMailMessage message : messageList) {
                emailSender.send(message);
            }
        } catch (MailSendException e) {
            throw e;
        }
        log.info("Message was sent to {}", mailDTO.getTo());
    }
}
