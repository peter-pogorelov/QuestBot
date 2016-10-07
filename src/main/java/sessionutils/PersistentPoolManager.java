package sessionutils;

import com.google.gson.Gson;
import sessionpojo.GameSession;
import sessionpojo.GameSessionPool;
import storypojo.Story;

import java.io.*;

/**
 * Created by Petr on 03.10.2016.
 */
public class PersistentPoolManager extends PoolManager {
    private File file;
    private Gson gson;

    public PersistentPoolManager(String dir) {
        super(StorageType.PERSISTENT_STORAGE);

        this.file = new File(dir);
        gson = new Gson();
    }

    public void saveSessions() {
    }

    public void loadSessions() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            StringBuilder builder = new StringBuilder();
            String currentLine = "";

            while((currentLine = br.readLine()) != null)
                builder.append(currentLine);

            setPool(gson.fromJson(builder.toString(), GameSessionPool.class));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
