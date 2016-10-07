package storycommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import sessionutils.PoolManager;
import storyengine.ActiveSession;
import storyengine.StoryEngine;
import storyutils.StoryLoader;

/**
 * Created by Petr on 01.10.2016.
 */


public class CommandStartGame extends StoryBaseCommand {
    private StoryEngine engine;

    public CommandStartGame(String commandIdentifier, String description, StoryEngine engine) {
        super(commandIdentifier, description);

        this.engine = engine;
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if(strings.length != 0) {
            try {
                String userName = user.getUserName();
                engine.setActiveSession(userName, concatArguments(strings)); //create session with client
                ActiveSession session = engine.getActiveSession(userName); //get active session with client

                reply(session.getCurrentQuestion(), absSender, user, chat); //reply chat message
                replyVariant(session.getCurrentAnswers(), absSender, user, chat); //print available list of answers
            } catch (Exception e) {
                reply(e.toString(), absSender, user, chat);
            }
        }
    }
}
