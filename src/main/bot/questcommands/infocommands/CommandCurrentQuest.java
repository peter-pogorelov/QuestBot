package questcommands.infocommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import questcommands.QuestBaseCommand;
import questengine.ActiveSession;
import questengine.QuestEngine;
import questpojo.Quest;
import questutils.Translator;
import utils.BotLogging;

/**
 * Created by Petr on 04.10.2016.
 */
public class CommandCurrentQuest extends QuestBaseCommand {
    public CommandCurrentQuest(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description, engine);
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        ActiveSession activeSession = engine.getActiveSession(user.getId().toString());
        try {
            if (activeSession != null) {
                Quest quest = activeSession.getQuest();
                if(quest != null) {
                    reply(activeSession.getQuest().getName(), absSender, user, chat);
                    return;
                }
            }
            String notPlaying = Translator.getInstance().getTranslation("not_playing", activeSession.getLocale());
            reply(notPlaying, absSender, user, chat);
        } catch (Translator.UnknownTranslationException e) {
            BotLogging.getLogger().fatal(e);
        }
    }
}
