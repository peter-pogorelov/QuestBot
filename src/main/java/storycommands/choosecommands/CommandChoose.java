package storycommands.choosecommands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import storycommands.QuestBaseCommand;
import storyengine.ActiveSession;
import storyengine.QuestEngine;

/**
 * Created by Petr on 07.10.2016.
 */
public class CommandChoose extends QuestBaseCommand{
    private QuestEngine engine;

    public CommandChoose(String commandIdentifier, String description, QuestEngine engine) throws NumberFormatException {
        super(commandIdentifier, description);

        Integer.parseInt(commandIdentifier); //preventing useing non-integer strings as command identifier
        this.engine = engine;
        this.visible = false;
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        int commandId = Integer.parseInt(getCommandIdentifier());
        ActiveSession activeSession = engine.getActiveSession(user.getId().toString()); //TODO watch TODO in CommandStartGame

        if(activeSession != null) {
            reply(activeSession.getAnswerReply(commandId), absSender, user, chat); //output reply
            activeSession.toNextNode(commandId); //go to next node
            if(activeSession.isQuestEnd()) {
                reply(activeSession.getCurrentQuestion(), absSender, user, chat);
                reply("Congratulations! You finished the game!", absSender, user, chat);
                try {
                    engine.saveClientSession(user.getId().toString()); //TODO watch TODO in CommandStartGame
                } catch (QuestEngine.QuestException e) {
                    reply(e.getMessage(), absSender, user, chat);
                }
            } else {
                reply(activeSession.getCurrentQuestion(), absSender, user, chat);
                replyVariant(activeSession.getCurrentAnswers(), absSender, user, chat);
            }
        } else {
            reply("Unexpected input", absSender, user, chat);
        }
    }
}
