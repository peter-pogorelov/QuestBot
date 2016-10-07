package storycommands.infocommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import storycommands.StoryBaseCommand;
import storyengine.StoryEngine;
import storypojo.Story;
import storyutils.StoryInfo;

/**
 * Created by Petr on 02.10.2016.
 */
public class CommandStoryInfo extends StoryBaseCommand {
    private StoryEngine engine;

    public CommandStoryInfo(String commandIdentifier, String description, StoryEngine engine) {
        super(commandIdentifier, description);
        this.engine = engine;
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){
        if(strings.length != 0){
            Story find = engine.getLoader().getStoryByName(concatArguments(strings));
            if(find != null)
                reply(new StoryInfo(find).getInfo(), absSender, user, chat);
            else
                reply("This story is not in our database", absSender, user, chat);
        }
    }
}
