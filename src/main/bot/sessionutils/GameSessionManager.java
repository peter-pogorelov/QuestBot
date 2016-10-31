package sessionutils;

import questutils.SettingsLoader;
import questutils.Translator;
import sessionpojo.GameSession;
import sessionpojo.GameSessionPool;
import questpojo.Quest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petr on 03.10.2016.
 */

//class used to save client sessions to hard drive
public abstract class GameSessionManager {
    public enum StorageType {PERSISTENT_STORAGE, TEMPORARY_STORAGE, NET_STORAGE}; //You are welcome to implement it

    private StorageType storageType;
    private GameSessionPool pool;

    public GameSessionManager(StorageType type) {
        storageType = type;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }

    public GameSessionPool getPool() {
        return pool;
    }

    public void setPool(GameSessionPool pool) {
        this.pool = pool;
    }

    public GameSession createClientSession(String userName) {
        GameSession gs = new GameSession();
        gs.setGroup(1);
        gs.setNode(1);
        gs.setUser(userName);
        gs.setWeight(1);
        gs.setLocale(SettingsLoader.getIntance().getSettings().getLocales().get(0)); //Setting first locale as main locale
        getPool().getSessions().add(gs);
        return gs;
    }

    public GameSession createClientSession(String userName, Quest quest) {
        GameSession gs = createClientSession(userName);
        gs.setQuest(quest.getName());
        return gs;
    }

    public GameSession getGameSession(String userName) { //return any session
        List<GameSession> sessions = getPool().getSessions();

        for(final GameSession session : sessions) {
            if(session.getUser().equals(userName)){
                return session;
            }
        }

        return null;
    }


    public GameSession getGameSession(String userName, Quest quest) {
        List<GameSession> sessions = getPool().getSessions();

        for(final GameSession session : sessions) {
            if(session.getUser() != null && session.getQuest() != null &&
               session.getUser().equals(userName) && session.getQuest().equals(quest.getName())){
                return session;
            }
        }

        return null;
    }

    public void updateQuestSession(GameSession upd){
        List<GameSession> sessions = getPool().getSessions();
        GameSession ref = null;

        for(final GameSession session : sessions) {
            if(session.getUser().equals(upd.getUser()) && session.getQuest() != null && session.getQuest().equals(upd.getQuest())){
                ref = session;
                break;
            }
        }

        if(ref != null) {
            ref.setWeight(upd.getWeight());
            ref.setNode(upd.getNode());
            ref.setGroup(upd.getGroup());
        }
    }

    public void updateLocale(String userName, String locale) {
        List<GameSession> sessions = getPool().getSessions();

        for(final GameSession session : sessions) {
            if(session.getUser().equals(userName)){
                session.setLocale(locale);
            }
        }
    }

//    public void deleteClientSession(GameSession del) {
//        List<GameSession> sessions = getPool().getSessions();
//        GameSession ref = null;
//
//        for(final GameSession session : sessions) {
//            if(session.getUser().equals(del.getUser()) && session.getQuest() != null && session.getQuest().equals(del.getQuest())){
//                ref = session;
//                break;
//            }
//        }
//
//        if(ref != null){
//            sessions.remove(ref);
//        }
//    }

    public void createEmptyPool() {
        pool = new GameSessionPool();
        pool.setSessions(new ArrayList<GameSession>());
    }

    public abstract void saveSessions();
    public abstract void loadSessions() throws Exception;
}
