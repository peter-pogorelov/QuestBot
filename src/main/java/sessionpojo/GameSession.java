/**
 * Created by Petr on 02.10.2016.
 */
package sessionpojo;

import storypojo.Story;

//POJO class
public class GameSession {
    private String story;
    private String user;
    private Integer weight;
    private Integer group;
    private Integer node;

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getNode() {
        return node;
    }

    public void setNode(Integer node) {
        this.node = node;
    }
}
