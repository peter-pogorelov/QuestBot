package questcommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import questengine.ActiveSession;
import questengine.QuestEngine;
import questutils.Translator;
import utils.BotLogging;

/**
 * Created by Petr on 04.10.2016.
 */
public class CommandResetGame extends QuestBaseCommand {

    public CommandResetGame(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description, engine);
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try {
            ActiveSession activeSession = engine.getActiveSession(user.getId().toString());
            String resetProgress = Translator.getInstance().getTranslation("reset_progress", activeSession.getLocale());

            engine.getActiveSession(user.getId().toString()).resetProgress(); //TODO watch TODO in CommandStartGame
            engine.getManager().saveSessions();

            reply(resetProgress, absSender, user, chat);
        } catch (Translator.UnknownTranslationException e) {
            BotLogging.getLogger().fatal(e.getMessage());
        }
    }
}
