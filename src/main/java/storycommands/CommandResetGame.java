package storycommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Created by Petr on 04.10.2016.
 */
public class CommandResetGame extends StoryBaseCommand {
    public CommandResetGame(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

    }
}
