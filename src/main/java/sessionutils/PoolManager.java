package sessionutils;

import org.javatuples.Pair;
import sessionpojo.GameSession;
import sessionpojo.GameSessionPool;
import storypojo.Story;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Petr on 03.10.2016.
 */

//class used to save client sessions to hard drive
public abstract class PoolManager {
    public enum StorageType {PERSISTENT_STORAGE, TEMPORARY_STORAGE, NET_STORAGE}; //You are welcome to implement it

    private StorageType storageType;
    private GameSessionPool pool;

    public PoolManager(StorageType type) {
        storageType = type;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public GameSessionPool getPool() {
        return pool;
    }

    public void setPool(GameSessionPool pool) {
        this.pool = pool;
    }

    public GameSession createClientSession(String userName, Story story) {
        GameSession gs = new GameSession();
        gs.setGroup(0);
        gs.setNode(0);
        gs.setStory(story.getName());
        gs.setUser(userName);
        gs.setWeight(0);
        getPool().getSessions().add(gs);
        return gs;
    }

    public GameSession getGameSession(String userName, Story story) {
        List<GameSession> sessions = getPool().getSessions();

        for(final GameSession session : sessions) {
            if(session.getUser().equals(userName) && session.getStory().equals(story.getName())){
                return session;
            }
        }

        return null;
    }

    public void updateGameSession(GameSession upd){
        List<GameSession> sessions = getPool().getSessions();
        GameSession ref = null;

        for(final GameSession session : sessions) {
            if(session.getUser().equals(upd.getUser()) && session.getStory().equals(upd.getStory())){
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

    public void deleteGameSession(GameSession del) {
        List<GameSession> sessions = getPool().getSessions();
        GameSession ref = null;

        for(final GameSession session : sessions) {
            if(session.getUser().equals(del.getUser()) && session.getStory().equals(del.getStory())){
                ref = session;
                break;
            }
        }

        if(ref != null){
            sessions.remove(ref);
        }
    }

    public abstract void saveSessions();
    public abstract void loadSessions();
}
