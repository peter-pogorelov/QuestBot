package storypojo;

/**
 * Created by Petr on 02.10.2016.
 */
public class Story {
    private String name;
    private String author;
    private String description;
    private StoryGroup[] groups;

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGroups(StoryGroup[] groups) {
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

    public StoryGroup[] getGroups() {
        return groups;
    }
}
