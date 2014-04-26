package com.hart.aris.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Joe on 26/04/2014.
 */
public class User {
    private SharedPreferences userdata;
    private SharedPreferences.Editor edit;
    public User(Activity a){
        userdata = a.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        edit = userdata.edit();
    }

    public void setUserMood(float mood){
        edit.putFloat("mood",mood);
        edit.commit();
    }

    public void addMood(float f){
        float currentMood = getUserMood();
        float newMood = (f+currentMood)/2;
        setUserMood(newMood);

    }

    public float getUserMood(){
        return userdata.getFloat("mood",0.0f);
    }

    public void setName(String name){
        edit.putString("name",name);
        edit.commit();
    }

    public String getName(){
        return userdata.getString("name","");
    }

    public void setSubject(String subject){
        edit.putString("subject",subject);
        edit.commit();
    }

    public String getSubject(){
        return userdata.getString("subject","");
    }

    public void setProjectType(String project){
        edit.putString("projectType",project);
        edit.commit();
    }

    public String getProjectType(){
        return userdata.getString("projectType","");
    }
}
