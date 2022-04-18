package com.override.util;

import com.override.models.Recipient;
import enums.Communication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface CommunicationStrategy {
    HttpStatus sendMessage(Recipient recipient, String message);
    Recipient setCommunication(Recipient recipient, String value);
    Communication getType();
}
