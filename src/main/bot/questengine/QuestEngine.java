package questengine;

import sessionpojo.GameSession;
import sessionutils.GameSessionManager;
import questpojo.*;
import questutils.QuestLoader;

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

    private QuestLoader loader;
    private GameSessionManager manager;

    public QuestEngine(QuestLoader loader, GameSessionManager manager) {
        sessionList = new HashMap<String, ActiveSession>();
        this.loader = loader;
        this.manager = manager;
    }

    public void setActiveSession(String userName, String story) throws QuestException {
        Quest quest = getLoader().getStoryByName(story); //get current story
        if(quest != null) {
            GameSession session = getManager().getGameSession(userName, quest); //load session from storage (save file)
            ActiveSession activeSession = getActiveSession(userName); //load active session

            if(session == null) {
                session = getManager().createClientSession(userName, quest); //register session
                getManager().saveSessions(); //save session in storage
            }

            if(activeSession != null) {
                getManager().updateGameSession(activeSession.toGameSession()); //save current active session to storage
                getManager().saveSessions(); //
            }

            createActiveSession(quest, session);
            return;
        }

        throw new QuestException("Following story does not exists!");
    }

    private ActiveSession createActiveSession(Quest quest, GameSession session) {
        ActiveSession activeSession = new ActiveSession(quest, session);
        sessionList.put(session.getUser(), activeSession);
        return activeSession;
    }

    public void saveClientSession(String userName) throws QuestException {
        ActiveSession session = getActiveSession(userName);
        if(session != null) {
            getManager().updateGameSession(session.toGameSession());
            getManager().saveSessions();
        } else {
            throw new QuestException("Could not save client session due its not exists");
        }
    }

    public ActiveSession getActiveSession(String userName) {
        return sessionList.get(userName); //can be null
    }

    public QuestLoader getLoader() {
        return loader;
    }

    public GameSessionManager getManager() {
        return manager;
    }
}
