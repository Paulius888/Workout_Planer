package com.example.workoutplaner.Exercises;

public class ExerciseInput {

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
