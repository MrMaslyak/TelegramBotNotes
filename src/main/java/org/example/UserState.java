package org.example;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UserState {
    void handle(Bot bot, Update update);
}
