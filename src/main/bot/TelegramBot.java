import questcommands.*;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.bots.commands.BotCommand;
import questutils.SettingsLoader;
import sessionutils.PersistentGameSessionManager;
import sessionutils.GameSessionManager;
import questcommands.choosecommands.CommandChoose;
import questcommands.infocommands.CommandCurrentQuest;
import questcommands.infocommands.CommandHelp;
import questcommands.infocommands.CommandQuests;
import questcommands.infocommands.CommandQuestInfo;
import questengine.QuestEngine;
import questutils.QuestLoader;

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
            e.printStackTrace();
        }
    }

    public void registerCommands() {
        register(new CommandStartGame(START_CMD, "Start new game", engine));
        register(new CommandHelp(HELP_CMD, "List of questcommands available"));
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
        SettingsLoader.getIntance().loadSettings();
        GameSessionManager gameSessionManager = new PersistentGameSessionManager(System.getProperty("user.dir").concat("/sessions.json"));
        gameSessionManager.loadSessions();

        QuestLoader loader = new QuestLoader(System.getProperty("user.dir") + "/quests");
        loader.loadQuests();
        this.engine = new QuestEngine(loader, gameSessionManager);

        registerCommands();
    }

    //@Override
    public String getBotUsername() {
        return "gm11_bot";
    }

    //@Override
    public String getBotToken() {
        return "285762062:AAFl8RHfdCiaVZ01TjG14Fvk7ghfmP2_f60";
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
                System.out.println("Something went wrong!"); //Заглушечка
            }
        }
    }
}