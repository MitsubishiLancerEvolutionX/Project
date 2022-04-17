package com.override.controller;

import com.override.service.RecipientService;
import dtos.RecipientDTO;
import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipientController {

    @Autowired
    private RecipientService recipientService;

    @PostMapping("/recipients/save")
    void saveRecipient(@RequestBody RecipientDTO recipientDTO) {
        recipientService.save(recipientDTO);
    }

    @PostMapping("/recipients/setCommunication")
    void setCommunication(
            @RequestParam("login") String login,
            @RequestParam("value") String value,
            @RequestParam("type") Communication type) {
        recipientService.updateCommunication(login, value, type);
    }

    @PostMapping("/recipients/delete")
    void deleteRecipient(@RequestBody RecipientDTO recipientDTO) {
        recipientService.delete(recipientDTO);
    }
}
