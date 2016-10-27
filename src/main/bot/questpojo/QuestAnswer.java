package questpojo;

/**
 * Created by Petr on 02.10.2016.
 */

//POJO class
public class QuestAnswer {
    private String text;
    private Integer weight;
    private String responce;
    private Integer jump;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setResponce(String responce) {
        this.responce = responce;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getResponce() {
        return responce;
    }

    public Integer getJump() {
        return jump;
    }

    public void setJump(Integer jump) {
        this.jump = jump;
    }
}
