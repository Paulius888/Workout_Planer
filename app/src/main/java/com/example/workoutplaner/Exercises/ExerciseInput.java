package com.example.workoutplaner.Exercises;

import java.io.Serializable;

public class ExerciseInput implements Serializable {

    private double Weight;
    private int Reps;

    public ExerciseInput(double weight, int reps){
        Weight = weight;
        Reps = reps;
    }

    public double getWeight(){
        return Weight;
    }

    public int getReps(){
        return Reps;
    }

    public void setWeight(double weight){
        Weight = weight;
    }

    public void setReps(int reps){
        Reps = reps;
    }
}
