package com.example.acer.mypda;

/**
 * Created by Raghid Nami - ID: 260701283
 * For the CCCS325 - Mobile Development Course
 * Assignment 3
 */

public class ExerciseDetails {

    private String name;
    private String date;
    private int statistic;
    private String location;
    private int _id;
    private int is_done;

    public ExerciseDetails(){

    }

    public ExerciseDetails(String name, String date, int statistic, String location, int done){
        this.setName(name);
        this.setDate(date);
        this.setStatistic(statistic);
        this.setLocation(location);
        this.setIs_done(done);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatistic() {
        return statistic;
    }

    public void setStatistic(int statistic) {
        this.statistic = statistic;
    }

    @Override
    public String toString(){
        return ("" + name + "\nDate: " + date + "\nDuration: " + statistic +" min\nLocation: " + location);
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getIs_done() {
        return is_done;
    }

    public void setIs_done(int is_done) {
        this.is_done = is_done;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
