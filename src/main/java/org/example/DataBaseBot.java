package org.example;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataBaseBot {
    private ArrayList<UserAction> userActions = new ArrayList<>();
    private Bot bot;
    private long idFromUser;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public DataBaseBot(Bot bot, long idFromUser) {
        this.bot = bot;
        this.idFromUser = idFromUser;
    }


    public void addUserAction(UserAction action) {
        userActions.add(action);
        System.out.println("User action added: " + action);
        scheduleReminder(action);
    }

    private void scheduleReminder(UserAction action) {
        LocalDateTime actionTime = LocalDateTime.of(action.getYear(), action.getMonth(), action.getDay(),
                Integer.parseInt(action.getTime().split(":")[0]),
                Integer.parseInt(action.getTime().split(":")[1]));

        LocalDateTime now = LocalDateTime.now();

        long delay = ChronoUnit.MILLIS.between(now, actionTime);

        if (delay > 0) {
            Timer timer = new Timer(delay, () -> {
                if (bot == null) {
                    System.out.println("Bot is not initialized.");
                    return;
                }
                if (action.getActionType() == 1) {
                    bot.sendText(idFromUser, "**************************");
                    bot.sendText(idFromUser, "Напоминаю, что ты должен идти спать, ЖИВО ");
                    System.out.println("Напоминаю, что ты должен идти спать, ЖИВО");
                    bot.sendText(idFromUser, "**************************");
                    bot.setUserState(idFromUser, new ActionsBot.DayState());
                    bot.sendText(idFromUser, "Введи число, на которое записать действие");
                }
                if (action.getActionType() == 2) {
                    bot.sendText(idFromUser, "**************************");
                    bot.sendText(idFromUser, "Напоминаю, что ты идешь тренироваться, сейчас же! ");
                    System.out.println("Напоминаю, что ты идешь тренироваться, сейчас же!");
                    bot.sendText(idFromUser, "**************************");
                    bot.setUserState(idFromUser, new ActionsBot.DayState());
                    bot.sendText(idFromUser, "Введи число, на которое записать действие");
                }
                if (action.getActionType() == 3) {
                    bot.sendText(idFromUser, "**************************");
                    bot.sendText(idFromUser, "Напоминаю, что ты идешь кушать, быстро идти есть! ");
                    System.out.println("Напоминаю, что ты идешь кушать, быстро идти есть!");
                    bot.sendText(idFromUser, "**************************");
                    bot.setUserState(idFromUser, new ActionsBot.DayState());
                    bot.sendText(idFromUser, "Введи число, на которое записать действие");
                }
                if (action.getActionType() == 4) {
                    bot.sendText(idFromUser, "**************************");
                    bot.sendText(idFromUser, "Напоминаю, что сейчас же идешь делать свою привычку! Я шо знаю, какая у тебя привычка \n Живо иди делать!");
                    System.out.println("Напоминаю, что сейчас же идешь делать свою привычку! Я шо знаю, какая у тебя привычка \n Живо иди делать!");
                    bot.sendText(idFromUser, "**************************");
                    bot.setUserState(idFromUser, new ActionsBot.DayState());
                    bot.sendText(idFromUser, "Введи число, на которое записать действие");
                }
            });
            timer.start();
        } else {
            System.out.println("Запланированное время уже в прошлом. Невозможно запланировать действие.");
        }
    }


    static class Timer extends Thread {
        private long time;
        private Runnable action;

        public Timer(long time, Runnable action) {
            this.time = time;
            this.action = action;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(time);
                action.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void setUserId(long userId) {
        this.idFromUser = userId;
    }
}
