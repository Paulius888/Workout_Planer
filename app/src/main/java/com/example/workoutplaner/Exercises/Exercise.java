package com.example.workoutplaner.Exercises;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String name;
    private int sets;
    private String userID;
    private String dayID;
    private String key;
    private String videoID;

    public Exercise(){

    }

    // used for fixing youtube links without need for user to trim them on there own
    private String trimmer (String videoid){
        String result="";
        if(videoid.length() == 0){
            return result;
        }
        result = videoid.substring(17, 28);
        return result;
    }

    public Exercise(String name, int sets, String userid, String dayid, String videoid){
        this.name = name;
        this.sets = sets;
        this.userID = userid;
        this.dayID = dayid;
        this.videoID = trimmer(videoid);
    }

    public String getName(){
        return name;
    }

    public int getSets(){
        return sets;
    }

    public String getKey(){
        return this.key;
    }

    public String getDayID() { return dayID; }

    public String getUserID(){
        return userID;
    }

    public String getVideoID(){
        return videoID;
    }

    public void setKey(String key){
        this.key = key;
    }
}
