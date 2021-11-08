package com.example.workoutplaner.Exercises;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String name;
    private int sets;
    private String userID;
    private String key;

    public Exercise(){

    }

    public Exercise(String name, int sets, String userID){
        this.name = name;
        this.sets = sets;
        this.userID = userID;
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

    public String getUserId(){
        return this.userID;
    }

    public void setKey(String key){
        this.key = key;
    }
}
