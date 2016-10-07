package sessionpojo;

import java.util.List;

/**
 * Created by Petr on 02.10.2016.
 */

//POJO class
public class GameSessionPool {
    List<GameSession> sessions;

    public List<GameSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<GameSession> sessions) {
        this.sessions = sessions;
    }
}
