package storyutils;

import storypojo.Story;

/**
 * Created by Petr on 02.10.2016.
 */
public class StoryInfo {
    private Story story;
    public StoryInfo(Story story){
        this.story = story;
    }

    public String getInfo(){
        StringBuilder builder = new StringBuilder();

        builder.append("Author of storypojo: ").append(story.getAuthor()).append("\n")
                .append("Name of storypojo: \"").append(story.getName()).append("\"\n")
                .append("Description of storypojo: \"").append(story.getDescription()).append("\"\n");

        return builder.toString();
    }
}
