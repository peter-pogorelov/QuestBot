package questcommands.infocommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import questcommands.QuestBaseCommand;
import questengine.ActiveSession;
import questengine.QuestEngine;
import questpojo.Quest;
import questutils.QuestLoader;
import questutils.Translator;
import utils.BotLogging;

/**
 * Created by Petr on 02.10.2016.
 */
public class CommandQuestInfo extends QuestBaseCommand {
    private static class QuestInfo {
        private Quest quest;
        public QuestInfo(Quest quest){
            this.quest = quest;
        }

        public String getInfo(QuestEngine engine, User user){
            StringBuilder builder = new StringBuilder();
            try {
                ActiveSession activeSession = engine.getActiveSession(user.getId().toString());
                String auth = Translator.getInstance().getTranslation("author", activeSession.getLocale());
                String name = Translator.getInstance().getTranslation("name_quest", activeSession.getLocale());
                String desc = Translator.getInstance().getTranslation("description_quest", activeSession.getLocale());

                builder.append(auth).append("\n").append(quest.getAuthor()).append("\n")
                        .append(name).append("\n").append(quest.getName()).append("\"\n")
                        .append(desc).append("\n").append(quest.getDescription()).append("\"\n");
            } catch (Translator.UnknownTranslationException e) {
                BotLogging.getLogger().fatal(e.getMessage());
            }

            return builder.toString();
        }
    }

    public CommandQuestInfo(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description, engine);
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){
        try {
            ActiveSession activeSession = engine.getActiveSession(user.getId().toString());
            String questNotRegistered = Translator.getInstance().getTranslation("quest_missing", activeSession.getLocale());
            String infoUsage          = Translator.getInstance().getTranslation("info_command_usage", activeSession.getLocale());

            if (strings.length != 0) {
                Quest find = QuestLoader.getInstance().getQuestByName(concatArguments(strings));
                if (find != null)
                    reply(new QuestInfo(find).getInfo(engine, user), absSender, user, chat);
                else
                    reply(questNotRegistered, absSender, user, chat);
            } else {
                reply(infoUsage, absSender, user, chat);
            }
        } catch (Translator.UnknownTranslationException e) {
            BotLogging.getLogger().fatal(e.getMessage());
        }
    }
}
