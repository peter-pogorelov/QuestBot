package storyengine;

import sessionpojo.GameSession;
import sessionutils.PoolManager;
import storypojo.*;
import storyutils.StoryLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petr on 04.10.2016.
 */

//class used to manage active client sessions
public class StoryEngine {
    private Map<String, ActiveSession> sessionList;

    private StoryLoader loader;
    private PoolManager manager;

    public StoryEngine(StoryLoader loader, PoolManager manager) {
        sessionList = new HashMap<String, ActiveSession>();
    }

    public void setActiveSession(String userName, String story) throws Exception {
        Story quest = loader.getStoryByName(story);
        if(story != null) {
            GameSession session = manager.getGameSession(userName, quest); //load session from storage (save file)
            ActiveSession activeSession = getActiveSession(userName); //load active session

            if(session == null) {
                session = manager.createClientSession(userName, quest); //register session
                manager.saveSessions(); //save session in storage
            }

            if(activeSession != null) {
                manager.updateGameSession(activeSession.toGameSession()); //save current active session to storage
                manager.saveSessions(); //
            }

            createActiveSession(quest, session);
            return;
        }

        throw new Exception("Story name does not exists!");
    }

    private ActiveSession createActiveSession(Story story, GameSession session) {
        ActiveSession activeSession = new ActiveSession(story, session);
        sessionList.put(session.getUser(), activeSession);
        return activeSession;
    }

    public ActiveSession getActiveSession(String userName) {
        return sessionList.get(userName); //can be null
    }

    public StoryLoader getLoader() {
        return loader;
    }

    public PoolManager getManager() {
        return manager;
    }
}
