import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import questcommands.*;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.bots.commands.BotCommand;
import questutils.SettingsLoader;
import questutils.Translator;
import sessionutils.PersistentGameSessionManager;
import sessionutils.GameSessionManager;
import questcommands.choosecommands.CommandChoose;
import questcommands.infocommands.CommandCurrentQuest;
import questcommands.infocommands.CommandHelp;
import questcommands.infocommands.CommandQuests;
import questcommands.infocommands.CommandQuestInfo;
import questengine.QuestEngine;
import questutils.QuestLoader;
import utils.BotLogging;

import java.io.IOException;

public class TelegramBot extends TelegramLongPollingCommandBot {
    public static final String START_CMD = "start";
    public static final String HELP_CMD = "help";
    public static final String STORIES_CMD = "quests";
    public static final String STORYINFO_CMD = "questinfo";
    public static final String RESET_CMD = "reset";
    public static final String SAVE_CMD = "save";
    public static final String CURRENT_CMD = "current";
    public static final String LOCALE_CMD = "setlocale";
    public static final int    MAX_ANSWERS = 10;

    private QuestEngine engine;

    public static void main(String[] args) {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            SettingsLoader.getIntance().loadSettings();
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            BotLogging.getLogger().fatal(e);
        } catch (IOException e) {
            BotLogging.getLogger().fatal(e);
        }
    }

    public void registerCommands() {
        register(new CommandStartGame(START_CMD, "start_command", engine));
        register(new CommandHelp(HELP_CMD, "help_command", engine));
        register(new CommandQuests(STORIES_CMD, "list_quests", engine));
        register(new CommandQuestInfo(STORYINFO_CMD, "questinfo_command", engine));
        register(new CommandResetGame(RESET_CMD, "reset_command", engine));
        register(new CommandCurrentQuest(CURRENT_CMD, "currentinfo_command", engine));
        register(new CommandSaveGame(SAVE_CMD, "savesession_command", engine));
        register(new CommandSetLocale(LOCALE_CMD, "locale_command", engine));

        for(int i = 0; i < MAX_ANSWERS; ++i) {
            register(new CommandChoose(String.valueOf(i), "", engine)); //invisible commands
        }
    }

    public TelegramBot(){
        //Order does matter
        try {
            Translator.getInstance().loadTranslations();
            QuestLoader.getInstance().loadQuests();

            GameSessionManager gameSessionManager = new PersistentGameSessionManager(System.getProperty("user.dir").concat("/sessions.json"));
            gameSessionManager.loadSessions();

            this.engine = new QuestEngine(gameSessionManager);

            registerCommands();
        } catch (Exception e) {
            BotLogging.getLogger().fatal(e);
        }
    }

    //@Override
    public String getBotUsername() {
        return SettingsLoader.getIntance().getSettings().getBotUserName();
    }

    //@Override
    public String getBotToken() {
        return SettingsLoader.getIntance().getSettings().getBotToken();
    }

    //In case of invalid unsupported command calling /help
    @Override
    public void processNonCommandUpdate(Update update) {
        if(update.hasMessage()) {
            Chat chat = update.getMessage().getChat();
            User usr = update.getMessage().getFrom();
            BotCommand helpCmd = getRegisteredCommand(HELP_CMD);
            if(helpCmd != null) {
                helpCmd.execute(this, usr, chat, null);
            } else {
                BotLogging.getLogger().fatal("Command " + HELP_CMD + " should be implemented!");
            }
        }
    }
}