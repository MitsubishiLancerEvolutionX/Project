package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterUserRequestDTO {
    private String telegramUserName;
    private String chatId;
}