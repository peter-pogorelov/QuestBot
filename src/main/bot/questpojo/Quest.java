package questpojo;

/**
 * Created by Petr on 02.10.2016.
 */
public class Quest {
    private String name;
    private String author;
    private String description;
    private QuestGroup[] groups;
    private String language;

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGroups(QuestGroup[] groups) {
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public QuestGroup[] getGroups() {
        return groups;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
