package questcommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import questengine.ActiveSession;
import questengine.QuestEngine;
import questutils.SettingsLoader;
import questutils.Translator;
import utils.BotLogging;

/**
 * Created by Petr on 30.10.2016.
 */
public class CommandSetLocale extends QuestBaseCommand {

    public CommandSetLocale(String commandIdentifier, String description, QuestEngine engine) {
        super(commandIdentifier, description, engine);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        ActiveSession activeSession = engine.getActiveSession(user.getId().toString());
        String locale = strings[0].toLowerCase();

        try {
            if (SettingsLoader.getIntance().getSettings().getLocales().contains(locale)) {
                activeSession.setLocale(locale);
                engine.getManager().updateLocale(activeSession.getUserName(), locale);
                engine.getManager().saveSessions();
                String localeChanged = Translator.getInstance().getTranslation("locale_changed", locale);
                reply(localeChanged, absSender, user, chat);
            } else {
                String localeNotFound = Translator.getInstance().getTranslation("locale_not_found", locale);
                reply(localeNotFound, absSender, user, chat);
            }
        } catch (Translator.UnknownTranslationException e) {
            BotLogging.getLogger().fatal(e);
        } catch (Exception e){
            BotLogging.getLogger().fatal(e);
        }
    }
}
