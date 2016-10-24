package questcommands.infocommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import questcommands.QuestBaseCommand;
import questengine.QuestEngine;
import questutils.QuestLoader;

import java.util.List;

/**
 * Created by Petr on 02.10.2016.
 */

public class CommandQuests extends QuestBaseCommand {
    private QuestEngine engine;

    public CommandQuests(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description);
        this.engine = engine;
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        StringBuilder builder = new StringBuilder();
        builder.append("List of available quests: \n");
        List<String> stories = QuestLoader.getInstance().getQuestNames();
        for(int i = 0; i < stories.size(); i++){
            builder.append(String.valueOf(i+1)).append(". ").append(stories.get(i)).append("\n");
        }

        builder.append("\nIf you want more details, specify /questinfo <name of quest>");

        reply(builder.toString(), absSender, user, chat);
    }
}
