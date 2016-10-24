package questcommands.infocommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;
import questcommands.QuestBaseCommand;

import java.util.Collection;

/**
 * Created by Petr on 01.10.2016.
 */
public class CommandHelp extends QuestBaseCommand {

    public CommandHelp(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        Collection<BotCommand> cmds = ((ICommandRegistry)absSender).getRegisteredCommands();
        StringBuilder builder = new StringBuilder();
        builder.append("Welcome to Telegram Game!\n").append("We really like to see you here!\n\n");

        for(final BotCommand cmd : cmds){
            if(cmd instanceof QuestBaseCommand && ((QuestBaseCommand)cmd).isVisible()) {
                builder.append("/").append(cmd.getCommandIdentifier()).append(" - ").append(cmd.getDescription()).append("\n");
            }
        }

        reply(builder.toString(), absSender, user, chat);
    }
}
