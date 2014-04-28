package com.hart.aris.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Date;

/**
 * Created by Joe on 26/04/2014.
 */
public class User {
    private SharedPreferences userdata;
    private SharedPreferences.Editor edit;

    public User(Activity a) {
        userdata = a.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        edit = userdata.edit();
    }

    public void addMood(float f) {
        float currentMood = getUserMood();
        float newMood = (f + currentMood) / 2;
        setUserMood(newMood);
        Log.e("Mood:", Float.toString(newMood));

    }

    public void storeHours(int hours) {
        edit.putInt("hours", hours);
        edit.commit();
    }

    public int getHours() {
        return userdata.getInt("hours", 0);
    }

    public void addToTotalHours(int hours) {
        edit.putInt("total_hours", hours);
        edit.commit();
    }

    public int getTotalHours() {
        return userdata.getInt("total_hours", 0);
    }

    public float getUserMood() {
        return userdata.getFloat("mood", 0.0f);
    }

    public void setUserMood(float mood) {
        edit.putFloat("mood", mood);
        edit.commit();
    }

    public String getName() {
        return userdata.getString("name", "");
    }

    public void setName(String name) {
        edit.putString("name", name);
        edit.commit();
    }

    public String getSubject() {
        return userdata.getString("subject", "");
    }

    public void setSubject(String subject) {
        edit.putString("subject", subject);
        edit.commit();
    }

    public String getProjectType() {
        return userdata.getString("projectType", "");
    }

    public void setProjectType(String project) {
        edit.putString("projectType", project);
        edit.commit();
    }

    public Date getDeadline() {
        long deadlineLong = userdata.getLong("deadline", 0L);
        Log.e("DeadlineLong: ", Long.toString(deadlineLong));
        Date deadline = new Date(deadlineLong);
        Log.e("DeadlineDATE: ", deadline.toString());
        return deadline;
    }

    public void setDeadline(Date d) {
        long datetime = d.getTime();
        edit.putLong("deadline", datetime);
        edit.commit();
    }

    //returns string for sentence eg: "That's after your deadline" "that's after your exam" etc
    public String getStringDeadline() {
        String projectType = getProjectType();
        if (projectType.equals("exam")) {
            return "exam";
        } else {
            if (projectType.equals("dissertation")) {
                return "deadline";
            } else {
                return "deadline";
            }
        }
    }

    public String getProjectVerb() {
        String projectType = getProjectType();
        if (projectType.equals("exam")) {
            return "revise";
        } else {
            if (projectType.equals("dissertation")) {
                return "work on your dissertation";
            } else {
                return "work on your coursework";
            }
        }
    }

    public int getWordCount() {
        return userdata.getInt("wordCount", 0);
    }

    public void setWordCount(int words) {

        edit.putInt("wordCount", words);
        edit.commit();
    }
}
