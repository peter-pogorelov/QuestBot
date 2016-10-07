package storyengine;

import sessionpojo.GameSession;
import storypojo.Story;
import storypojo.StoryAnswer;
import storypojo.StoryGroup;
import storypojo.StoryNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petr on 07.10.2016.
 */
public class ActiveSession {
    Integer weight;
    String userName;
    Story story;
    StoryGroup group;
    StoryNode node;

    public ActiveSession(Story story, GameSession gameSession) {
        this.story = story;
        this.userName = gameSession.getUser();
        this.group = getGroupFromSession(this.story, gameSession);
        this.node = getNodeFromSession(group, gameSession);
        this.weight = gameSession.getWeight();
    }

    private Integer getWeight() {
        return weight;
    }

    public Story getStory() {
        return story;
    }

    public String getUserName() {
        return userName;
    }

    private StoryGroup getGroup() {
        return group;
    }

    private StoryNode getNode() {
        return node;
    }

    public GameSession toGameSession() {
        GameSession ret = new GameSession();
        ret.setNode(node.getId());
        ret.setGroup(group.getId());
        ret.setWeight(weight);
        ret.setUser(userName);
        ret.setStory(story.getName());
        return ret;
    }

    public String getCurrentQuestion() {
        return getNode().getText();
    }

    public List<String> getCurrentAnswers() {
        List<String> lst = new ArrayList<String>();
        for(final StoryAnswer answer : getNode().getAnswers()){
            lst.add(answer.getText());
        }
        return lst;
    }

    public String getAnswerReply(String answerString){
        for(final StoryAnswer answer : getNode().getAnswers()){
            if(answer.getText().equals(answerString)){
                return answer.getResponce();
            }
        }

        return null;
    }

    //should be called at the very end, updates weight and redirects to another node
    public void updateState(String answerString) {

    }

    //utils functions

    private StoryGroup getGroupFromSession(Story story, GameSession gameSession){
        StoryGroup currentGroup = null;
        for(final StoryGroup group : story.getGroups()) {
            if(group.getId().equals(gameSession.getGroup())) {
                currentGroup = group;
                break;
            }
        }

        return currentGroup;
    }

    private StoryNode getNodeFromSession(StoryGroup group, GameSession session) {
        StoryNode currentNode = null;
        for(final StoryNode node : group.getNodes()) {
            if(node.getId().equals(session.getNode())) {
                currentNode = node;
                break;
            }
        }

        return currentNode;
    }
}
