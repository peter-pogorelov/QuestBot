package questutils;

import com.google.gson.Gson;
import questpojo.Quest;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Petr on 02.10.2016.
 */

//class used to load quests
public class QuestLoader {
    public static final String QUEST_DIR = "quests";
    private File dir;
    private List<Quest> questList;
    private Gson gson;
    private static QuestLoader loader;

    private QuestLoader() {
        this.dir = new File(System.getProperty("user.dir") + "/" + QUEST_DIR);
        gson = new Gson();
    }

    public static QuestLoader getInstance() {
        if(loader == null) {
            loader = new QuestLoader();
        }

        return loader;
    }

    public void loadQuests() throws IOException{
        questList = new ArrayList<Quest>();
        for(final File entry : dir.listFiles()){
            if(entry.isFile() && entry.getName().toLowerCase().endsWith(".json")){
                BufferedReader br = new BufferedReader(new FileReader(entry));
                StringBuilder builder = new StringBuilder();
                String currentLine = "";

                while((currentLine = br.readLine()) != null)
                    builder.append(currentLine);

                Quest quest = gson.fromJson(builder.toString(), Quest.class);
                questList.add(quest);
            }
        }
    }

    public Quest getQuestByName(String name){
        for(final Quest quest : questList){
            if(quest != null && quest.getName().equals(name)){
                return quest;
            }
        }

        return null;
    }

    public List<String> getQuestNames(){
        List<String> names = new ArrayList<String>();

        for(final Quest quest : questList){
            if(quest != null) {
                names.add(quest.getName());
            }
        }

        return names;
    }

    public List<Quest> getQuestList(){
        return questList;
    }
}
