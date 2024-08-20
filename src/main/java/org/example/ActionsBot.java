package org.example;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;


public class ActionsBot {
    private static ArrayList<Integer> days = new ArrayList<>();
    private static ArrayList<Integer> months = new ArrayList<>();
    private static ArrayList<Integer> years = new ArrayList<>();
    private static ArrayList<Integer> actions = new ArrayList<>();

    static class StartState implements UserState {
        @Override
        public void handle(Bot bot, Update update) {
            long idFromUser = update.getMessage().getFrom().getId();
            String textUser = update.getMessage().getText();
            bot.sendText(idFromUser, "\uD83D\uDC4B Привет! Я Maslyak — бот для создания временных заметок, который поможет тебе организовать и отслеживать важные моменты в течение дня. Вот что я умею:\n" +
                    "\n" +
                    "⏰ Установка заметок по времени: Ты можешь создать заметку, указав время, когда тебе нужно выполнить определенное действие.\n" +
                    "\n" +
                    "\uD83D\uDCC5 Типы заметок:\n" +
                    "\n" +
                    "\uD83D\uDCA4 Сон: Напомню, когда пора ложиться спать или проснуться.\n" +
                    "\uD83D\uDCAA Тренировка: Помогу не пропустить занятия спортом.\n" +
                    "\uD83C\uDF74 Еда: Напомню о приеме пищи или перекусе.\n" +
                    "\uD83C\uDFAF Привычки: Помогу развивать полезные привычки, будь то медитация, чтение или что-то другое.\n" +
                    "Просто введи время и выбери тип заметки, а я позабочусь о том, чтобы ты не забыл о важных делах лошара!\n" +
                    "А кст, если захочешь рестартнуть меня, пропиши restart, только не сделай ошибок, а то я знаю таких");
                bot.setUserState(idFromUser, new DayState());
                bot.sendText(idFromUser, "Введи число, на которое записать действие");

        }
    }

    static class DayState implements UserState {
        @Override
        public void handle(Bot bot, Update update) {
            long idFromUser = update.getMessage().getFrom().getId();
            String textUser = update.getMessage().getText();
            try {
                int day = Integer.parseInt(textUser);

                    if (day <= 31 && day > 0) {
                        bot.sendText(idFromUser, "Введи месяц, на которое записать действие");
                        days.add(day);
                        bot.setUserState(idFromUser, new MonthState());

                    } else {
                        bot.sendText(idFromUser, "Введи сумасшедший корректный день! Какой же ты идиот");
                    }

            } catch (NumberFormatException e) {
                bot.sendText(idFromUser, "Введи корректный день!");
                return;
            }
        }

    }


    static class MonthState implements UserState {
        @Override
        public void handle(Bot bot, Update update) {
            long idFromUser = update.getMessage().getFrom().getId();
            String textUser = update.getMessage().getText();
            try {
                int month = Integer.parseInt(textUser);

                    if (month <= 12 && month > 0) {
                        bot.sendText(idFromUser, "Введи год в котором ты выполнишь это дейсвие");
                        months.add(month);
                        bot.setUserState(idFromUser, new YearState());
                    } else {
                        bot.sendText(idFromUser, "Введи корректный месяц! Ты на ошибках учишся?");
                    }

            } catch (NumberFormatException e) {
                bot.sendText(idFromUser, "Введи корректный месяц!");
            }
        }
    }

    static class YearState implements UserState {
        @Override
        public void handle(Bot bot, Update update) {
            long idFromUser = update.getMessage().getFrom().getId();
            String textUser = update.getMessage().getText();
            try {
                int year = Integer.parseInt(textUser);

                    if (year <= 2030 && year >= 2024) {
                        bot.sendText(idFromUser, "Красава!\uD83D\uDC8B");
                        bot.sendText(idFromUser, "Теперь выбери тип напоминания, которое хочешь создать. У тебя есть четыре варианта:\n" +
                                "\n" +
                                "1\uFE0F⃣ Сон\n" +
                                "2\uFE0F⃣ Тренировка\n" +
                                "3\uFE0F⃣ Еда\n" +
                                "4\uFE0F⃣ Привычки\n" +
                                "\n" +
                                "Просто введи номер нужного варианта, и я создам для тебя это напоминание");
                        years.add(year);
                        bot.setUserState(idFromUser, new ActionState());
                    } else {
                        bot.sendText(idFromUser, "Ты совсем дебил?, я работаю с 2024 до 2030, потом увольняюсь. Жалко тебя");
                    }

            } catch (NumberFormatException e) {
                bot.sendText(idFromUser, "Введи корректный год!");
            }
        }
    }

    static class ActionState implements UserState {
        @Override
        public void handle(Bot bot, Update update) {
            long idFromUser = update.getMessage().getFrom().getId();
            String textUser = update.getMessage().getText();
            try {
                int year = Integer.parseInt(textUser);
                    if (year <= 4 && year > 0) {
                        bot.sendText(idFromUser, "Теперь введи время. Вот так 12:00.\uD83E\uDDE0");
                        actions.add(year);
                        bot.setUserState(idFromUser, new TimeState());
                    } else {
                        bot.sendText(idFromUser, "Ну кому я сказал что не больше 4 и не меньше 1. Я думал ты хоть числа знаешь \uD83D\uDE35");
                    }
            } catch (NumberFormatException e) {
                bot.sendText(idFromUser, "Введи корректный тип напоминания!");
            }
        }
    }

    static class TimeState implements UserState {
        @Override
        public void handle(Bot bot, Update update) {
            long idFromUser = update.getMessage().getFrom().getId();
            String textUser = update.getMessage().getText();
            TimeValidator validator = new TimeValidator();
            DataBaseBot database = new DataBaseBot(bot, idFromUser);

            if (validator.validate(textUser)) {
                int day = days.get(days.size() - 1);
                int month = months.get(months.size() - 1);
                int year = years.get(years.size() - 1);
                LocalDate enteredDate;
                try {
                    enteredDate = LocalDate.of(year, month, day);
                } catch (DateTimeException e) {
                    bot.sendText(idFromUser, "Введи корректную дату!");
                    return;
                }
                LocalDate today = LocalDate.now();
                if (enteredDate.isBefore(today)) {
                    bot.sendText(idFromUser, "Ты думаешь я не знаю какой сейчас время,дата,год в жизни?\uD83D\uDC7A\nВведи корректно время, а не в прошлом умник \uD83E\uDD21\uD83E\uDD21 ");
                    bot.setUserState(idFromUser, new DayState());
                    bot.sendText(idFromUser, "Введи день, на которое записать действие, только умник теперь не в прошлом \uD83E\uDD21");
                    return;
                }
                UserAction action = new UserAction(day, month, year, textUser, actions.get(actions.size() - 1));
                database.addUserAction(action);
                bot.sendText(idFromUser, "Готово! \nЯ записал твоё напоминание. Не переживай, я сообщу тебе, когда придёт время выполнить действие \nУдачи! \uD83D\uDCAA");
                bot.setUserState(idFromUser, new DayState());
                bot.sendText(idFromUser, "Введи день, на которое записать действие");
            } else {
                bot.sendText(idFromUser, "Введи корректное время в формате HH:MM!");
            }
        }
    }
    public static void clearAllData() {
        days.clear();
        months.clear();
        years.clear();
        actions.clear();
    }


}
