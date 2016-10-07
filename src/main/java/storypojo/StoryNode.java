package storypojo;

/**
 * Created by Petr on 02.10.2016.
 */

//POJO class
public class StoryNode {
    private Integer id;
    private String text;
    private StoryAnswer[] answers;
    private StoryGroupGoto[] gotos;
    private Integer end;

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public StoryAnswer[] getAnswers() {
        return answers;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAnswers(StoryAnswer[] answers) {
        this.answers = answers;
    }

    public StoryGroupGoto[] getGotos() {
        return gotos;
    }

    public void setGotos(StoryGroupGoto[] gotos) {
        this.gotos = gotos;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }
}
