package com.override.service;

import com.override.exception.SmsRuException;
import com.override.feign.SmsRuFeign;
import dto.CodeCallResponseDTO;
import dto.PhoneDTO;
import dto.SmsResponseDTO;
import dto.SmsRuBalanceResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsRuServiceImpl implements SmsRuService {
    @Value("${sms.api.id}")
    private String apiID;

    @Autowired
    private SmsRuFeign smsRuFeign;

    @Override
    public String verifyNumber(String clientPhoneNumber) {
        CodeCallResponseDTO codeCallResponseDTO = smsRuFeign.verifyPhone(clientPhoneNumber, apiID);
        return codeCallResponseDTO.getCode();
    }

    @Override
    public double getBalance() {
        SmsRuBalanceResponseDTO smsRuBalanceResponseDTO = smsRuFeign.getBalance(apiID);
        return smsRuBalanceResponseDTO.getBalance();
    }

    @Override
    public void sendSms(String phoneNumber, String message) {
        SmsResponseDTO smsResponseDTO = smsRuFeign.sendSms(phoneNumber, message, apiID);
        PhoneDTO currentPhone = smsResponseDTO.getSms().getPhoneDTOMap().get(phoneNumber);
        String status = currentPhone.getStatus();

        if (!status.equals("OK")) {
            throw new SmsRuException(currentPhone.getStatusText());
        }
        log.info("Сообщение отправлено по номеру \"{}\"", phoneNumber);
    }
}
