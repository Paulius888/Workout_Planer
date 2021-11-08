package com.example.workoutplaner;

public class WorkoutTimeElapsed {
    String userID;
    long timeInSeconds;
    String workoutName;

    public WorkoutTimeElapsed() {

    }

    public WorkoutTimeElapsed(String userid, long timeinseconds) {
        this.userID = userid;
        this.timeInSeconds = timeinseconds;
    }
}
