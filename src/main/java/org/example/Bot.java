package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {
    private Map<Long, UserState> userStates = new HashMap<>();

    @Override
    public String getBotUsername() {
        return "MaslyakBot";
    }

    @Override
    public String getBotToken() {
        return "7465968812:AAEHmc8N96J-UvbCJSMak1N9XvE9K6jU608";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long idFromUser = update.getMessage().getFrom().getId();
            String text = update.getMessage().getText();

            if (text.equals("restart")) {
                ActionsBot.clearAllData();
                setUserState(idFromUser, new ActionsBot.StartState());
                sendText(idFromUser, "\uD83E\uDEE1Рестарт Бота Пу-Пу-Пу\uD83E\uDEE1 \n Введи что угодно, чтобы я обновился \uD83E\uDEE8");
                return;
            }

            // Продолжение обычной обработки
            UserState state = userStates.getOrDefault(idFromUser, new ActionsBot.StartState());
            System.out.println("Current State: " + state.getClass().getSimpleName());
            state.handle(this, update);
        } else {
            System.out.println("No message or text found in update");
        }
    }

    public void setUserState(long userId, UserState state) {
        userStates.put(userId, state);
    }

    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
