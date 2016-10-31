package questcommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import questengine.ActiveSession;
import questengine.QuestEngine;
import questutils.Translator;
import utils.BotLogging;

/**
 * Created by Petr on 01.10.2016.
 */


public class CommandStartGame extends QuestBaseCommand {
    public CommandStartGame(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description, engine);
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try {
            String userName = user.getId().toString(); //TODO change "username" usage to "userid" usage
            ActiveSession activeSession = engine.getActiveSession(userName); //get active session with client
            String startCommandUsage = Translator.getInstance().getTranslation("start_command_usage", activeSession.getLocale());
            if (strings.length != 0) {
                engine.setActiveSession(userName, concatArguments(strings)); //create session with client
                activeSession = engine.getActiveSession(userName); //update with new active session
                reply(activeSession.getCurrentQuestion(), absSender, user, chat); //reply chat message
                if (!activeSession.isQuestEnd() && activeSession.getCurrentAnswers() != null && activeSession.getCurrentAnswers().size() > 0) {
                    replyVariant(activeSession.getCurrentAnswers(), absSender, user, chat); //print available list of answers
                }
            } else {
                reply(startCommandUsage, absSender, user, chat);
            }
        } catch (QuestEngine.QuestException e) {
            reply(e.getMessage(), absSender, user, chat);
        } catch (Translator.UnknownTranslationException e) {
            BotLogging.getLogger().fatal(e);
        }
    }
}
