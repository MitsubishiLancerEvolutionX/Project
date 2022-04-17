package com.override.controller;

import com.override.services.MessageService;
import dtos.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<HttpStatus> sendMessage(@RequestBody MessageDTO message){
       return messageService.sendMessage(message.getMessage(), message.getChatId());
    }
}
