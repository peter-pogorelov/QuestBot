package questpojo;

/**
 * Created by Petr on 02.10.2016.
 */

//POJO class
public class QuestNode {
    private Integer id;
    private String text;
    private QuestAnswer[] answers;
    private QuestGroupGoto[] gotos;
    private Integer end;

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public QuestAnswer[] getAnswers() {
        return answers;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAnswers(QuestAnswer[] answers) {
        this.answers = answers;
    }

    public QuestGroupGoto[] getGotos() {
        return gotos;
    }

    public void setGotos(QuestGroupGoto[] gotos) {
        this.gotos = gotos;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }
}
