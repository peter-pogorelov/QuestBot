package questcommands.infocommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import questcommands.QuestBaseCommand;
import questengine.QuestEngine;
import questpojo.Quest;

/**
 * Created by Petr on 02.10.2016.
 */
public class CommandQuestInfo extends QuestBaseCommand {
    private QuestEngine engine;

    private static class QuestInfo {
        private Quest quest;
        public QuestInfo(Quest quest){
            this.quest = quest;
        }

        public String getInfo(){
            StringBuilder builder = new StringBuilder();

            builder.append("Author of quest: ").append(quest.getAuthor()).append("\n")
                    .append("Name of quest: \"").append(quest.getName()).append("\"\n")
                    .append("Description of quest: \"").append(quest.getDescription()).append("\"\n");

            return builder.toString();
        }
    }

    public CommandQuestInfo(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description);
        this.engine = engine;
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){
        if(strings.length != 0){
            Quest find = engine.getLoader().getStoryByName(concatArguments(strings));
            if(find != null)
                reply(new QuestInfo(find).getInfo(), absSender, user, chat);
            else
                reply("This quest is not in our database", absSender, user, chat);
        } else {
            reply("Command usage:  /questinfo <name of quest>", absSender, user, chat);
        }
    }
}
