package com.override.util;

import com.override.models.Recipient;
import com.override.service.TelegramService;
import dtos.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TelegramMessage implements MessageStrategy {

    @Autowired
    private TelegramService telegramService;

    @Override
    public ResponseEntity<HttpStatus> sendMessage(Recipient recipient, String message) {
        return telegramService.sendMessage(MessageDTO.builder()
                .chatId(recipient.getTelegramId())
                .message(message)
                .build());
    }
}
