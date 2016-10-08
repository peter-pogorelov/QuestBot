package sessionutils;

import com.google.gson.Gson;
import sessionpojo.GameSession;
import sessionpojo.GameSessionPool;
import storypojo.Story;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

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
        try {
            PrintWriter writer = new PrintWriter(file.getName(), "UTF-8");
            writer.write(gson.toJson(getPool()));
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void loadSessions() {
        try {
            if(file.isFile()) {
                BufferedReader br = new BufferedReader(new FileReader(file));

                StringBuilder builder = new StringBuilder();
                String currentLine = "";

                while ((currentLine = br.readLine()) != null)
                    builder.append(currentLine);

                setPool(gson.fromJson(builder.toString(), GameSessionPool.class));
            } else {
                createEmptyPool();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
