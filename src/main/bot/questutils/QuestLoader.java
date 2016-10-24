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

//class used to load stories
public class QuestLoader {
    private File dir;
    private List<Quest> questList;
    private Gson gson;

    public QuestLoader(String dir) {
        this.dir = new File(dir);
        gson = new Gson();
    }

    public void loadQuests(){
        questList = new ArrayList<Quest>();
        for(final File entry : dir.listFiles()){
            if(entry.isFile() && entry.getName().toLowerCase().endsWith(".json")){
                try {
                    BufferedReader br = new BufferedReader(new FileReader(entry));
                    StringBuilder builder = new StringBuilder();
                    String currentLine = "";

                    while((currentLine = br.readLine()) != null)
                        builder.append(currentLine);

                    Quest quest = gson.fromJson(builder.toString(), Quest.class);
                    questList.add(quest);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Quest getStoryByName(String name){
        for(final Quest quest : questList){
            if(quest != null && quest.getName().equals(name)){
                return quest;
            }
        }

        return null;
    }

    public List<String> getStoryNames(){
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
