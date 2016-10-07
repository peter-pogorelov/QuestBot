package storycommands.infocommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import storycommands.StoryBaseCommand;
import storyengine.StoryEngine;

/**
 * Created by Petr on 02.10.2016.
 */

public class CommandStories extends StoryBaseCommand {
    private StoryEngine engine;

    public CommandStories(String commandIdentifier, String description, StoryEngine engine) {
        super(commandIdentifier, description);
        this.engine = engine;
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        StringBuilder builder = new StringBuilder();
        builder.append("List of available stories: \n\n");


        for(final String name : engine.getLoader().getStoryNames()){
            builder.append(name).append("\n");
        }

        builder.append("If you want more details, specify /storyinfo <name of storypojo>");

        reply(builder.toString(), absSender, user, chat);
    }
}
