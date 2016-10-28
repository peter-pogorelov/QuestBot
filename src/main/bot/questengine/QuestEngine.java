package questengine;

import org.glassfish.hk2.utilities.reflection.Logger;
import questutils.Translator;
import sessionpojo.GameSession;
import sessionutils.GameSessionManager;
import questpojo.*;
import questutils.QuestLoader;
import utils.BotLogging;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petr on 04.10.2016.
 */

//class used to manage active client sessions
public class QuestEngine {
    public static class QuestException extends Exception{
        public QuestException(String exception) {
            super(exception);
        }
    }

    private Map<String, ActiveSession> sessionList;
    private GameSessionManager manager;

    public QuestEngine(GameSessionManager manager) {
        sessionList = new HashMap<String, ActiveSession>();
        this.manager = manager;
    }

    public void setActiveSession(String userName, String story) throws QuestException {
        Quest quest = QuestLoader.getInstance().getQuestByName(story); //get current story
        ActiveSession activeSession = getActiveSession(userName); //load active session

        if(quest == null) {
            try {
                String questNotExists = Translator.getInstance().getTranslation("quest_not_exists", activeSession.getLocale());
                throw new QuestException(questNotExists);
            } catch (Translator.UnknownTranslationException e) {
                BotLogging.getLogger().fatal(e);
            }
        }

        GameSession session = getManager().getGameSession(userName, quest); //load session from storage (save file)

        if(session == null) {
            session = getManager().createClientSession(userName, quest); //register session
            getManager().saveSessions(); //save session in storage
        }

        if(activeSession != null) { //save current active session if it already exists
            if(!activeSession.getQuest().getName().equals(quest.getName())) {
                saveToGameSession(userName);
            } else {
                return; //if this story is already going for this user
            }
        }

        newActiveSession(quest, session);
    }

    public void saveToGameSession(String userName) throws QuestException {
        ActiveSession activeSession = getActiveSession(userName);
        if(activeSession != null) {
            getManager().updateGameSession(activeSession.toGameSession());
            getManager().saveSessions();
        } else {
            try {
                String saveSessionFailure = Translator.getInstance().getTranslation("save_session_failure", activeSession.getLocale());
                throw new QuestException(saveSessionFailure);
            } catch (Translator.UnknownTranslationException e) {
                BotLogging.getLogger().fatal(e);
            }
        }
    }

    public ActiveSession getActiveSession(String userName) {
        ActiveSession activeSession = sessionList.get(userName); //can be null
        if(activeSession == null) {
            GameSession session = getManager().getGameSession(userName); //get any game session
            if(session == null) {
                session = getManager().createClientSession(userName); //create session without specified quest
                getManager().saveSessions(); //save session in storage
            }

            activeSession = newActiveSession(session);
        }

        return activeSession;
    }

    public GameSessionManager getManager() {
        return manager;
    }

    private ActiveSession newActiveSession(Quest quest, GameSession session) {
        ActiveSession activeSession = new ActiveSession(quest, session);
        sessionList.put(session.getUser(), activeSession);
        return activeSession;
    }

    private ActiveSession newActiveSession(GameSession session) {
        ActiveSession activeSession = new ActiveSession(session);
        sessionList.put(session.getUser(), activeSession);
        return activeSession;
    }
}
