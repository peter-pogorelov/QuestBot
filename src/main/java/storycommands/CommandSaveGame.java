package storycommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import storyengine.ActiveSession;
import storyengine.QuestEngine;

/**
 * Created by Petr on 07.10.2016.
 */
public class CommandSaveGame extends QuestBaseCommand {
    QuestEngine engine;

    public CommandSaveGame(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description);

        this.engine = engine;
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try {
            engine.saveClientSession(user.getId().toString()); //TODO see todo in CommandStartGame
            reply("Your progress has been saved", absSender, user, chat);
        } catch (QuestEngine.QuestException e) {
            reply(e.getMessage(), absSender, user, chat);
        }
    }
}
