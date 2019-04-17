package com.jss.artha;

import android.app.Application;

public class GlobalClass extends Application
{


    private String state;
    private String city;
    private int statePosition;
    private int cityPosition;
    private int genderPosition;
    private int stateCount;
    private int cityCount;
    private int genderCount;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStatePosition() {
        return statePosition;
    }

    public void setStatePosition(int statePosition) {
        this.statePosition = statePosition;
    }

    public int getCityPosition() {
        return cityPosition;
    }

    public void setCityPosition(int cityPosition) {
        this.cityPosition = cityPosition;
    }

    public int getGenderPosition() {
        return genderPosition;
    }

    public void setGenderPosition(int genderPosition) {
        this.genderPosition = genderPosition;
    }

    public int getStateCount() {
        return stateCount;
    }

    public void setStateCount(int stateCount) {
        this.stateCount = stateCount;
    }

    public int getCityCount() {
        return cityCount;
    }

    public void setCityCount(int cityCount) {
        this.cityCount = cityCount;
    }

    public int getGenderCount() {
        return genderCount;
    }

    public void setGenderCount(int genderCount) {
        this.genderCount = genderCount;
    }
}
