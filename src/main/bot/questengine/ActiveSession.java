package questengine;

import sessionpojo.GameSession;
import questpojo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petr on 07.10.2016.
 */
public class ActiveSession {
    private Integer weight;
    private String userName;
    private Quest quest;
    private QuestGroup group;
    private QuestNode node;
    private String locale;

    public ActiveSession(Quest quest, GameSession gameSession) {
        this(gameSession);
        this.quest = quest;
        this.group = getGroupFromSession(this.quest, gameSession);
        this.node = getNodeFromSession(group, gameSession);
        this.weight = gameSession.getWeight();
    }

    public ActiveSession(GameSession gameSession) {
        this.userName = gameSession.getUser();
        this.locale = gameSession.getLocale();
    }

    public GameSession toGameSession() {
        GameSession ret = new GameSession();
        ret.setUser(userName);
        ret.setLocale(locale);
        ret.setNode(node.getId());
        ret.setGroup(group.getId());
        ret.setWeight(weight);
        ret.setQuest(quest.getName());

        return ret;
    }

    public void resetProgress() {
        this.group = quest.getGroups()[0];
        this.node = this.group.getNodes()[0];
        this.weight = 0;
    }

    public String getCurrentQuestion() {
        return getNode().getText();
    }

    public List<String> getCurrentAnswers() {
        List<String> lst = new ArrayList<String>();
        QuestAnswer[] answers = getNode().getAnswers();
        for(final QuestAnswer answer : answers){
            if(answer != null) { //hack for GSON
                lst.add(answer.getText());
            }
        }
        return lst;
    }

    public String getAnswerReply(String answerString){
        for(final QuestAnswer answer : getNode().getAnswers()){
            if(answer.getText().equals(answerString)){
                return answer.getResponce();
            }
        }

        return null;
    }

    //command id start with 1
    public String getAnswerReply(int commandId){
        if(commandId - 1 < getNode().getAnswers().length) {
            return getNode().getAnswers()[commandId - 1].getResponce();
        }

        return null;
    }

    //should be called at the very end, updates weight and redirects to another node
    public void toNextNode(int answerId) {
        if(answerId - 1 < getNode().getAnswers().length) {
            QuestAnswer answer = getNode().getAnswers()[answerId - 1]; //Get current answer
            if(answer.getWeight() != null) {
                this.weight += answer.getWeight(); //Update weights if its provided in quest
            }

            if(answer.getJump() != null) {
                this.group = quest.getGroups()[answer.getJump()-1]; //To following group
                this.node = this.getGroup().getNodes()[0]; //To first node
                return;
            }

            int currentNodeIndex = getNode().getId() - 1; //Index it bigger by 1

            if(getGroup().getNodes().length > currentNodeIndex + 1) {
                QuestNode nextNode = getGroup().getNodes()[currentNodeIndex + 1];

                if(nextNode.getGotos() != null && nextNode.getGotos().length != 0) {
                    if(!tryProcessGotos(nextNode)) {
                        this.node = nextNode;
                    }
                } else {
                    this.node = nextNode;
                }
            }
        }
    }

    private boolean tryProcessGotos(QuestNode node) {
        for(final QuestGroupGoto gotos : node.getGotos()) {
            String condition = gotos.getCondition();
            if(condition.equals("<") && this.weight < gotos.getWeight()
            || condition.equals("<=") && this.weight <= gotos.getWeight()
            || condition.equals(">") && this.weight > gotos.getWeight()
            || condition.equals(">=") && this.weight >= gotos.getWeight()
            || condition.equals("==") && this.weight == gotos.getWeight()
            || condition.equals("<>") && this.weight == gotos.getWeight())
            {
                this.group = quest.getGroups()[gotos.getGroup()-1]; //TODO check for bounds alert
                this.node = this.group.getNodes()[0];
                return true;
            }
        }

        return false;
    }

    public boolean isQuestEnd() {
        return this.node.getEnd() != null && this.node.getEnd() == 1;
    }

    //utils functions

    private QuestGroup getGroupFromSession(Quest quest, GameSession gameSession){
        QuestGroup currentGroup = null;
        for(final QuestGroup group : quest.getGroups()) {
            if(group.getId().equals(gameSession.getGroup())) {
                currentGroup = group;
                break;
            }
        }

        return currentGroup;
    }

    private QuestNode getNodeFromSession(QuestGroup group, GameSession session) {
        QuestNode currentNode = null;
        for(final QuestNode node : group.getNodes()) {
            if(node.getId().equals(session.getNode())) {
                currentNode = node;
                break;
            }
        }

        return currentNode;
    }

    private Integer getWeight() {
        return weight;
    }

    public Quest getQuest() {
        return quest;
    }

    public String getUserName() {
        return userName;
    }

    private QuestGroup getGroup() {
        return group;
    }

    private QuestNode getNode() {
        return node;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
