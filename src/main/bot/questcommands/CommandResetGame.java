package questcommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import questengine.ActiveSession;
import questengine.QuestEngine;
import questpojo.Quest;
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
            Quest quest = activeSession.getQuest();

            if(quest != null) {
                String resetProgress = Translator.getInstance().getTranslation("reset_progress", activeSession.getLocale());
                engine.getActiveSession(user.getId().toString()).resetProgress(); //TODO watch TODO in CommandStartGame
                engine.saveProgressToGameSession(activeSession.getUserName());

                reply(resetProgress, absSender, user, chat);
            } else {
                String notPlaying = Translator.getInstance().getTranslation("not_playing", activeSession.getLocale());
                reply(notPlaying, absSender, user, chat);
            }
        } catch (Translator.UnknownTranslationException e) {
            BotLogging.getLogger().fatal(e);
        } catch (QuestEngine.QuestException e) {
            BotLogging.getLogger().fatal(e);
        }
    }
}
