import storycommands.*;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.bots.commands.BotCommand;
import sessionutils.PersistentPoolManager;
import sessionutils.PoolManager;
import storycommands.infocommands.CommandCurrentStory;
import storycommands.infocommands.CommandHelp;
import storycommands.infocommands.CommandStories;
import storycommands.infocommands.CommandStoryInfo;
import storyengine.StoryEngine;
import storyutils.StoryLoader;

public class TelegramBot extends TelegramLongPollingCommandBot {
    public static final boolean PERSISTENT_STORAGE = false;

    public static final String START_CMD = "start";
    public static final String HELP_CMD = "help";
    public static final String STORIES_CMD = "stories";
    public static final String STORYINFO_CMD = "storyinfo";
    public static final String RESET_CMD = "reset";
    public static final String CURRENT_CMD = "current";

    private StoryLoader loader;
    private StoryEngine engine;

    public static void main(String[] args) {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public TelegramBot(){
        PoolManager poolManager = new PersistentPoolManager(System.getProperty("user.dir"));

        loader = new StoryLoader(System.getProperty("user.dir"));
        loader.loadStories();
        this.engine = new StoryEngine(loader, poolManager);

        register(new CommandStartGame(START_CMD, "Start new game", engine));
        register(new CommandHelp(HELP_CMD, "List of storycommands available"));
        register(new CommandStories(STORIES_CMD, "List of stories available", engine));
        register(new CommandStoryInfo(STORYINFO_CMD, "Describe specific story, use name after cmd", engine));
        register(new CommandResetGame(RESET_CMD, "Reset current story or specific, use name after cmd as optional parameter"));
        register(new CommandCurrentStory(CURRENT_CMD, "Get information about current story"));
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