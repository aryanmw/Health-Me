package com.wadhavekar.healthme;

public class WorkoutCheckedList {
    String workoutName,workoutSets,workoutReps;

    public WorkoutCheckedList(String name, String workoutSets, String workoutReps) {
        this.workoutName = name;
        this.workoutSets = workoutSets;
        this.workoutReps = workoutReps;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public String getWorkoutSets() {
        return workoutSets;
    }

    public String getWorkoutReps() {
        return workoutReps;
    }
}
