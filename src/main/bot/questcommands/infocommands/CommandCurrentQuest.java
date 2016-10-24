package questcommands.infocommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import questcommands.QuestBaseCommand;
import questengine.ActiveSession;
import questengine.QuestEngine;

/**
 * Created by Petr on 04.10.2016.
 */
public class CommandCurrentQuest extends QuestBaseCommand {
    private QuestEngine engine;

    public CommandCurrentQuest(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description);
        this.engine = engine;
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        ActiveSession activeSession = engine.getActiveSession(user.getUserName());
        if(activeSession != null) {
            reply(activeSession.getQuest().getName(), absSender, user, chat);
        } else {
            reply("You are not playing anything yet", absSender, user, chat);
        }
    }
}
