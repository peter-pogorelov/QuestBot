package questcommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import questengine.ActiveSession;
import questengine.QuestEngine;

/**
 * Created by Petr on 01.10.2016.
 */


public class CommandStartGame extends QuestBaseCommand {
    private QuestEngine engine;

    public CommandStartGame(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description);

        this.engine = engine;
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if(strings.length != 0) {
            try {
                String userName = user.getId().toString(); //TODO change "username" usage to "userid" usage
                engine.setActiveSession(userName, concatArguments(strings)); //create session with client
                ActiveSession session = engine.getActiveSession(userName); //get active session with client

                reply(session.getCurrentQuestion(), absSender, user, chat); //reply chat message
                if(!session.isQuestEnd() && session.getCurrentAnswers() != null && session.getCurrentAnswers().size() > 0) {
                    replyVariant(session.getCurrentAnswers(), absSender, user, chat); //print available list of answers
                }
            } catch (QuestEngine.QuestException e) {
                reply(e.getMessage(), absSender, user, chat);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            reply("", absSender, user, chat);
        }
    }
}
