package com.override.controller.rest;

import com.override.feigns.NotificatorFeign;
import dtos.BalanceResponseFromNotificationControllerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notification")
public class NotificationRestController {

    @Autowired
    NotificatorFeign notificatorFeign;

    @Value("${sms.url.replenish-balance}")
    private String urlToReplenishBalance;

    @GetMapping("/balance")
    public BalanceResponseFromNotificationControllerDTO getBalanceDTO() {
        return new BalanceResponseFromNotificationControllerDTO(notificatorFeign.getBalance(), urlToReplenishBalance);
    }
}
