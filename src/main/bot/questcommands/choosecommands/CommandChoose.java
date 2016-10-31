package questcommands.choosecommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import questcommands.QuestBaseCommand;
import questengine.ActiveSession;
import questengine.QuestEngine;
import questutils.Translator;
import utils.BotLogging;

/**
 * Created by Petr on 07.10.2016.
 */
public class CommandChoose extends QuestBaseCommand{
    public CommandChoose(String commandIdentifier, String description, QuestEngine engine) throws NumberFormatException {
        super(commandIdentifier, description, engine);

        Integer.parseInt(commandIdentifier); //preventing useing non-integer strings as command identifier
        this.visible = false;
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        int commandId = Integer.parseInt(getCommandIdentifier());
        ActiveSession activeSession = engine.getActiveSession(user.getId().toString()); //TODO watch TODO in CommandStartGame
        try {
            if(activeSession != null && activeSession.getQuest() != null && !activeSession.getCurrentAnswers().isEmpty()) {
                String reply = activeSession.getAnswerReply(commandId);
                if(reply != null) { //reply can be skipped
                    reply(activeSession.getAnswerReply(commandId), absSender, user, chat); //output reply
                }

                activeSession.toNextNode(commandId); //go to next node

                String finishGame = Translator.getInstance().getTranslation("finish_game", activeSession.getLocale());
                if(activeSession.isQuestEnd()){
                    reply(activeSession.getCurrentQuestion(), absSender, user, chat);
                    reply(finishGame, absSender, user, chat);
//                    try {
//                        engine.saveProgressToGameSession(user.getId().toString()); //TODO watch TODO in CommandStartGame
//                    } catch (QuestEngine.QuestException e) {
//                        reply(e.getMessage(), absSender, user, chat);
//                    }
                } else {
                    reply(activeSession.getCurrentQuestion(), absSender, user, chat);
                    replyVariant(activeSession.getCurrentAnswers(), absSender, user, chat);
                }
            } else {
                String wrongInput = Translator.getInstance().getTranslation("input_error", activeSession.getLocale());
                reply(wrongInput, absSender, user, chat);
            }
        } catch (Translator.UnknownTranslationException e) {
            BotLogging.getLogger().fatal(e);
        }
    }
}
