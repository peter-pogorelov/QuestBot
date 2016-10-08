package storycommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import storyengine.QuestEngine;

/**
 * Created by Petr on 04.10.2016.
 */
public class CommandResetGame extends QuestBaseCommand {
    QuestEngine engine;

    public CommandResetGame(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description);

        this.engine = engine;
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        engine.getActiveSession(user.getId().toString()).resetProgress(); //TODO watch TODO in CommandStartGame
        engine.getManager().saveSessions();

        reply("Your progress has been reset", absSender, user, chat);
    }
}
