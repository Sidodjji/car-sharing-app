package org.carsharingapp.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramNotificationService implements NotificationService {
    private static final String HTML_PARSE_MODE = "HTML";

    private final RestClient restClient = RestClient.create();
    private final Environment environment;

    @Override
    public void sendMessage(String message) {
        String telegramApiUrl = environment.getRequiredProperty("telegram.api.url");
        String botToken = environment.getRequiredProperty("telegram.bot.token");
        String chatId = environment.getRequiredProperty("telegram.chat.id");
        String url = telegramApiUrl + "/bot" + botToken + "/sendMessage";
        TelegramMessageRequest request = new TelegramMessageRequest(
                chatId,
                message,
                HTML_PARSE_MODE
        );
        try {
            restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RuntimeException e) {
            log.warn("Can't send Telegram notification", e);
        }
    }

    private record TelegramMessageRequest(
            String chat_id,
            String text,
            String parse_mode
    ){
    }
}
