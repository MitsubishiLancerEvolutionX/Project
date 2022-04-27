package com.override.mappers;

import dtos.MailDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import java.util.LinkedList;
import java.util.List;

@Component
public class EmailMapper {
    @Value("${spring.mail.username}")
    private String mailUserName;

    public List<SimpleMailMessage> dtoToListOfSimpleMails(MailDTO mailDTO) {
        List<SimpleMailMessage> messageList = new LinkedList<>();
        SimpleMailMessage message;
        for (String to: mailDTO.getTo()) {
            message = new SimpleMailMessage();
            message.setFrom(mailUserName);
            message.setTo(to);
            message.setSubject(mailDTO.getSubject());
            message.setText(mailDTO.getText());
            messageList.add(message);
        }
        return messageList;
    }
}
