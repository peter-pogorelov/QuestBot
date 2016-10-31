package questutils;

import java.util.List;

/**
 * Created by Petr on 24.10.2016.
 */

//POJO class
public class Settings {
    private List<String> locales;
    private String botUserName;
    private String botToken;

    public List<String> getLocales() {
        return locales;
    }

    public void setLocales(List<String> locales) {
        this.locales = locales;
    }

    public String getBotUserName() {
        return botUserName;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
