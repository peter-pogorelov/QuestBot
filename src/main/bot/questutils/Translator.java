package questutils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.telegram.telegrambots.logging.BotLogger;
import utils.BotLogging;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Petr on 24.10.2016.
 */
public final class Translator {
    public static class TranslatorException extends Exception {
        public TranslatorException(String e){
            super(e);
        }
    }

    public static class UnknownTranslationException extends  Exception {
        public UnknownTranslationException(String e){
            super(e);
        }
    }

    public static final String LOCALES_FILE = "translate.json";
    private File file;
    private Gson gson;
    private Map<String, Map<String, String>> locales;
    private static Translator translator;

    private Translator() {
        this.file = new File(System.getProperty("user.dir") + "/" + LOCALES_FILE);
        this.gson = new Gson();
    }

    public static Translator getInstance() {
        if(translator == null){
            translator = new Translator();
        }

        return translator;
    }

    public void loadTranslations() throws TranslatorException, IOException {
        Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF8"));
        StringBuilder builder = new StringBuilder();
        String currentLine;

        while ((currentLine = br.readLine()) != null)
            builder.append(currentLine);

        locales = gson.fromJson(builder.toString(), type);

        checkBotLocales();
    }

    private void checkBotLocales() throws TranslatorException { //grab all locales from first element
        for(Map.Entry<String, Map<String, String>> entry : locales.entrySet()) {
            for (Map.Entry<String, String> subentry : entry.getValue().entrySet()) {
                if(!SettingsLoader.getIntance().getSettings().getLocales().contains(subentry.getKey())) {
                    throw new TranslatorException(subentry.getKey());
                }
            }
        }
    }

    public String getTranslation(String str, String locale) throws UnknownTranslationException {
        Map<String, String> translation = locales.get(str);

        if(translation == null || translation.get(locale) == null) {
            throw new UnknownTranslationException(str);
        }

        return translation.get(locale);
    }
}
