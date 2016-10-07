package storypojo;

/**
 * Created by Petr on 02.10.2016.
 */

//POJO class
public class StoryGroup {
    private Integer id;
    private StoryNode[] nodes;

    public Integer getId() {
        return id;
    }

    public StoryNode[] getNodes() {
        return nodes;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNodes(StoryNode[] nodes) {
        this.nodes = nodes;
    }
}
