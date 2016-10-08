package storyutils;

import com.google.gson.Gson;
import storypojo.Story;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petr on 02.10.2016.
 */

//class used to load stories
public class StoryLoader {
    private File dir;
    List<Story> storyList;
    Gson gson;

    public StoryLoader(String dir) {
        this.dir = new File(dir);
        gson = new Gson();
    }

    public void loadStories(){
        storyList = new ArrayList<Story>();
        for(final File entry : dir.listFiles()){
            if(entry.isFile() && entry.getName().endsWith(".json") && entry.getName().toLowerCase().contains("quest")){
                try {
                    BufferedReader br = new BufferedReader(new FileReader(entry));
                    StringBuilder builder = new StringBuilder();
                    String currentLine = "";

                    while((currentLine = br.readLine()) != null)
                        builder.append(currentLine);

                    storyList.add(gson.fromJson(builder.toString(), Story.class));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Story getStoryByName(String name){
        for(final Story story : storyList){
            if(story != null && story.getName().equals(name)){
                return story;
            }
        }

        return null;
    }

    public List<String> getStoryNames(){
        List<String> names = new ArrayList<String>();

        for(final Story story : storyList){
            if(story != null) {
                names.add(story.getName());
            }
        }

        return names;
    }

    public List<Story> getStoryList(){
        return storyList;
    }
}
