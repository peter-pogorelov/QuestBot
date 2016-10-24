package questpojo;

/**
 * Created by Petr on 02.10.2016.
 */

//POJO class
public class QuestGroup {
    private Integer id;
    private QuestNode[] nodes;

    public Integer getId() {
        return id;
    }

    public QuestNode[] getNodes() {
        return nodes;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNodes(QuestNode[] nodes) {
        this.nodes = nodes;
    }
}
