package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    private String id = "7465968812";
    private String token = "AAEHmc8N96J-UvbCJSMak1N9XvE9K6jU608";
    private static Bot bot = new Bot();

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
       botsApi.registerBot(new Bot());
    }
}
