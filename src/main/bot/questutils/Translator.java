package questutils;

import com.google.gson.Gson;
import com.sun.org.apache.xml.internal.security.transforms.TransformationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Petr on 24.10.2016.
 */
public class Translator {
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
    private TreeMap<String, Map<String, String>> locales;
    private static Translator translator;

    private Translator() {
        this.file = new File(LOCALES_FILE);
        this.gson = new Gson();
    }

    public static Translator getInstance() {
        if(translator == null){
            translator = new Translator();
        }

        return translator;
    }

    public void loadTranslations() throws TranslatorException{
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.file));
            StringBuilder builder = new StringBuilder();
            String currentLine;

            while ((currentLine = br.readLine()) != null)
                builder.append(currentLine);

            locales = gson.fromJson(builder.toString(), locales.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }

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
