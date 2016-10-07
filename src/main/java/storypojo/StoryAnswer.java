package storypojo;

/**
 * Created by Petr on 02.10.2016.
 */

//POJO class
public class StoryAnswer {
    private String text;
    private Integer weight;
    private String responce;

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
}
