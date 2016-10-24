package questutils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by Petr on 24.10.2016.
 */
public class SettingsLoader {
    public static final String SETTHINGS_FILE = "settings.json";
    private File file;
    private Gson gson;
    private Settings settings;
    private static SettingsLoader loader;

    private SettingsLoader() {
        this.file = new File(SETTHINGS_FILE);
        this.gson = new Gson();
    }

    public void loadSettings(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.file));
            StringBuilder builder = new StringBuilder();
            String currentLine;

            while ((currentLine = br.readLine()) != null)
                builder.append(currentLine);

            settings = gson.fromJson(builder.toString(), Settings.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SettingsLoader getIntance() {
        if(loader == null) {
            loader = new SettingsLoader();
        }

        return loader;
    }

    public Settings getSettings(){
        return settings;
    }
}
