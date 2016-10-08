package storycommands.infocommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import storycommands.QuestBaseCommand;
import storyengine.QuestEngine;
import storypojo.Story;

/**
 * Created by Petr on 02.10.2016.
 */
public class CommandQuestInfo extends QuestBaseCommand {
    private QuestEngine engine;

    private static class QuestInfo {
        private Story story;
        public QuestInfo(Story story){
            this.story = story;
        }

        public String getInfo(){
            StringBuilder builder = new StringBuilder();

            builder.append("Author of quest: ").append(story.getAuthor()).append("\n")
                    .append("Name of quest: \"").append(story.getName()).append("\"\n")
                    .append("Description of quest: \"").append(story.getDescription()).append("\"\n");

            return builder.toString();
        }
    }

    public CommandQuestInfo(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description);
        this.engine = engine;
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){
        if(strings.length != 0){
            Story find = engine.getLoader().getStoryByName(concatArguments(strings));
            if(find != null)
                reply(new QuestInfo(find).getInfo(), absSender, user, chat);
            else
                reply("This story is not in our database", absSender, user, chat);
        } else {
            reply("Command usage:  /questinfo <name of quest>", absSender, user, chat);
        }
    }
}
