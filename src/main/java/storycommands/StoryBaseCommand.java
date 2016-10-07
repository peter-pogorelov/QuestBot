package storycommands;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;

import java.util.List;

/**
 * Created by Petr on 03.10.2016.
 */
public abstract class StoryBaseCommand extends BotCommand {
    public StoryBaseCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
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
            availableVariants.append("/").append(i).append(" - ").append(variant.get(i).toString()).append("\n");
        }

        reply(availableVariants.toString(), absSender, user, chat);
    }

    protected static String concatArguments(String[] strings) {
        StringBuilder builder = new StringBuilder("");
        for(final String str : strings)
            builder.append(str).append(" ");

        return builder.toString();
    }
}
