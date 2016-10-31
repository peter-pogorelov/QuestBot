package questcommands.infocommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import questcommands.QuestBaseCommand;
import questengine.ActiveSession;
import questengine.QuestEngine;
import questutils.QuestLoader;
import questutils.Translator;
import utils.BotLogging;

import java.util.List;

/**
 * Created by Petr on 02.10.2016.
 */

public class CommandQuests extends QuestBaseCommand {
    public CommandQuests(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description, engine);
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try {
            ActiveSession activeSession = engine.getActiveSession(user.getId().toString());
            String listQuests = Translator.getInstance().getTranslation("list_quests", activeSession.getLocale());
            String details = Translator.getInstance().getTranslation("details", activeSession.getLocale());

            StringBuilder builder = new StringBuilder();
            builder.append(listQuests).append("\n");
            List<String> stories = QuestLoader.getInstance().getQuestNames();
            for (int i = 0; i < stories.size(); i++) {
                builder.append(String.valueOf(i + 1)).append(". ").append(stories.get(i)).append("\n");
            }

            builder.append("\n").append(details);

            reply(builder.toString(), absSender, user, chat);
        } catch (Translator.UnknownTranslationException e) {
            BotLogging.getLogger().fatal(e);
        }
    }
}
