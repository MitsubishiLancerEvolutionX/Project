package utils;

import com.override.model.Recipient;
import com.override.service.strategy.CommunicationStrategy;
import com.override.service.strategy.EmailCommunication;
import com.override.service.strategy.SmsCommunication;
import com.override.service.strategy.TelegramCommunication;
import dto.RecipientDTO;
import enums.Communication;

import java.util.HashMap;
import java.util.Map;

public class TestFieldsUtil {

    public static Map<Communication, CommunicationStrategy> getSenderMap(
            TelegramCommunication telegramCommunication,
            EmailCommunication emailCommunication, SmsCommunication smsCommunication) {
        Map<Communication, CommunicationStrategy> senderMap = new HashMap<>();
        senderMap.put(Communication.EMAIL, emailCommunication);
        senderMap.put(Communication.TELEGRAM, telegramCommunication);
        senderMap.put(Communication.SMS, smsCommunication);
        return senderMap;
    }

    public static Recipient getRecipient() {
        return Recipient.builder()
                .login("admin")
                .telegramId("123")
                .email("123@mail.ru")
                .build();
    }

    public static RecipientDTO getRecipientDto() {
        return RecipientDTO.builder()
                .login("admin")
                .telegramId("123")
                .email("123@mail.ru")
                .build();
    }
}
