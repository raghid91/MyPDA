package com.example.acer.mypda;

/**
 * Created by Raghid Nami - ID: 260701283
 * For the CCCS325 - Mobile Development Course
 * Assignment 3
 */

public class TimeLogDetails {

    private String name;
    private String description;
    private String sTime;
    private String eTime;
    private String location;
    private int _id;
    private int is_done;

    public TimeLogDetails(){

    }

    public TimeLogDetails(String name, String description, String sTime, String eTime, String location, int done){
        this.setName(name);
        this.setDescription(description);
        this.setsTime(sTime);
        this.seteTime(eTime);
        this.setLocation(location);
        this.setIs_done(done);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    @Override
    public String toString(){
        return ("" + name + "\n" + description + "\nTime: " + sTime + " - " + eTime + "\nLocation: " + location);
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
