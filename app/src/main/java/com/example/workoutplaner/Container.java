package com.example.workoutplaner;

class Container{

    //eventually provides setters and getters
    public float x;
    public float y;
    //------------

    private static Container instance = null;
    private void Container(){

    }
    public static Container getInstance(){
        if(instance==null){
            instance = new Container();
        }
        return instance;
    }
}