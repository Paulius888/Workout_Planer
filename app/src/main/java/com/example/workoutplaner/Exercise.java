package com.example.workoutplaner;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String Name;

    public Exercise(String name){
        Name = name;
    }

    public String getName(){
        return Name;
    }
}
