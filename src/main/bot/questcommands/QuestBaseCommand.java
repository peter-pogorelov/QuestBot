package questcommands;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import questengine.QuestEngine;
import questpojo.Quest;

import java.util.List;

/**
 * Created by Petr on 03.10.2016.
 */
public abstract class QuestBaseCommand extends BotCommand {
    protected boolean visible = true;
    protected QuestEngine engine;

    public QuestBaseCommand(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description);
        this.engine = engine;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    protected void reply(String text, AbsSender absSender, User user, Chat chat){
        SendMessage msg = new SendMessage();

        try {
            msg.setChatId(chat.getId().toString());
            msg.enableMarkdown(true);
            msg.setText(text);

            absSender.sendMessage(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    protected void replyVariant(List<?> variant, AbsSender absSender, User user, Chat chat){
        StringBuilder availableVariants = new StringBuilder();

        for(int i = 0; i < variant.size(); ++i) {
            availableVariants.append("/").append(i+1).append(" - ").append(variant.get(i).toString()).append("\n");
        }

        reply(availableVariants.toString(), absSender, user, chat);
    }

    protected static String concatArguments(String[] strings) {
        StringBuilder builder = new StringBuilder("");
        for(int i = 0; i < strings.length - 1; i++) {
            builder.append(strings[i]).append(" ");
        }
        builder.append(strings[strings.length - 1]);

        return builder.toString();
    }
}
