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

public class TelegramBot extends TelegramLongPollingCommandBot {
    public static final String START_CMD = "start";
    public static final String HELP_CMD = "help";
    public static final String STORIES_CMD = "quests";
    public static final String STORYINFO_CMD = "questinfo";
    public static final String RESET_CMD = "reset";
    public static final String SAVE_CMD = "save";
    public static final String CURRENT_CMD = "current";
    public static final int    MAX_ANSWERS = 10;

    private QuestEngine engine;

    public static void main(String[] args) {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            BotLogging.getLogger().fatal(e.getMessage());
        }
    }

    public void registerCommands() {
        register(new CommandStartGame(START_CMD, "Start new game", engine));
        register(new CommandHelp(HELP_CMD, "List of questcommands available", engine));
        register(new CommandQuests(STORIES_CMD, "List of stories available", engine));
        register(new CommandQuestInfo(STORYINFO_CMD, "Describe specific story, use name after cmd", engine));
        register(new CommandResetGame(RESET_CMD, "Reset current story or specific, use name after cmd as optional parameter", engine));
        register(new CommandCurrentQuest(CURRENT_CMD, "Get information about current story", engine));
        register(new CommandSaveGame(SAVE_CMD, "Save current session", engine));

        for(int i = 0; i < MAX_ANSWERS; ++i) {
            register(new CommandChoose(String.valueOf(i), "", engine)); //invisible commands
        }
    }

    public TelegramBot(){
        //Order does matter
        try {
            SettingsLoader.getIntance().loadSettings();
            Translator.getInstance().loadTranslations();
            QuestLoader.getInstance().loadQuests();

            GameSessionManager gameSessionManager = new PersistentGameSessionManager(System.getProperty("user.dir").concat("/sessions.json"));
            gameSessionManager.loadSessions();

            this.engine = new QuestEngine(gameSessionManager);

            registerCommands();
        } catch (Exception e) {
            BotLogging.getLogger().fatal(e.getMessage());
        }
    }

    //@Override
    public String getBotUsername() {
        return "";
    }

    //@Override
    public String getBotToken() {
        return "";
    }

    //In case of invalid unsupported command calling /help
    @Override
    public void processNonCommandUpdate(Update update) {
        if(update.hasMessage()) {
            Message msg = update.getMessage();
            BotCommand helpCmd = getRegisteredCommand(HELP_CMD);
            if(helpCmd != null) {
                helpCmd.execute(this, null, msg.getChat(), null);
            } else {
                BotLogging.getLogger().error("Command " + HELP_CMD + " should be implemented!");
            }
        }
    }
}