package com.example.workoutplaner.Exercises;

import java.util.ArrayList;
import java.util.Date;

public class DoneExercise {
//    private Date date;
    private String weights;
    private String reps;

    public DoneExercise(){}

    public DoneExercise(ArrayList<ExerciseInput> exercises){
//        date = new Date();
        weights = "";
        reps = "";
        for(int i = 0; i < exercises.size(); i++){
            if (exercises.get(i) == null){
                continue;
            }
            weights += Double.toString(exercises.get(i).getWeight()) + " ";
            reps += Integer.toString(exercises.get(i).getReps()) + " ";
        }

        weights.trim();
        reps.trim();
    }

    public String getWeights(){
        return weights;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public void setWeights(String weights) {
        this.weights = weights;
    }
}
