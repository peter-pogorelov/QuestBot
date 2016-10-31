package questcommands.infocommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;
import questcommands.QuestBaseCommand;
import questengine.ActiveSession;
import questengine.QuestEngine;
import questutils.Translator;
import utils.BotLogging;

import java.util.Collection;

/**
 * Created by Petr on 01.10.2016.
 */
public class CommandHelp extends QuestBaseCommand {

    public CommandHelp(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description, engine);
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try {
            Collection<BotCommand> cmds = ((ICommandRegistry) absSender).getRegisteredCommands();
            StringBuilder builder = new StringBuilder();

            ActiveSession activeSession = engine.getActiveSession(user.getId().toString());
            String welcome = Translator.getInstance().getTranslation("welcome_game", activeSession.getLocale());
            String salute = Translator.getInstance().getTranslation("salute", activeSession.getLocale());
            builder.append(welcome + "\n").append(salute + "\n\n");

            for (final BotCommand cmd : cmds) {
                if (cmd instanceof QuestBaseCommand && ((QuestBaseCommand) cmd).isVisible()) {
                    builder.append("/").append(cmd.getCommandIdentifier()).append(" - ");
                    builder.append(((QuestBaseCommand)cmd).getLocalizedDescription(activeSession.getLocale())).append("\n");
                }
            }

            reply(builder.toString(), absSender, user, chat);
        } catch (Exception e) {
            BotLogging.getLogger().fatal(e);
        }
    }
}
