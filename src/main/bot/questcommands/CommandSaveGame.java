package questcommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import questengine.ActiveSession;
import questengine.QuestEngine;
import questutils.Translator;
import utils.BotLogging;

/**
 * Created by Petr on 07.10.2016.
 */
public class CommandSaveGame extends QuestBaseCommand {

    public CommandSaveGame(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description, engine);
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try {
            ActiveSession activeSession = engine.getActiveSession(user.getId().toString());
            String resetProgress = Translator.getInstance().getTranslation("reset_progress", activeSession.getLocale());

            engine.saveClientSession(user.getId().toString()); //TODO see todo in CommandStartGame
            reply(resetProgress, absSender, user, chat);
        } catch (QuestEngine.QuestException e) {
            BotLogging.getLogger().fatal(e.getMessage());
        } catch (Translator.UnknownTranslationException e) {
            BotLogging.getLogger().fatal(e.getMessage());
        }
    }
}
