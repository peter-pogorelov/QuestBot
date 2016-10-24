package questpojo;

/**
 * Created by Petr on 02.10.2016.
 */

//POJO class
public class QuestGroupGoto {
    private String condition;
    private Integer weight;
    private Integer group;

    public String getCondition() {
        return condition;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getGroup() {
        return group;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }
}
