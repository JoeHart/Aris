package com.hart.aris.app;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import android.app.PendingIntent;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import java.util.Calendar;
import android.app.AlarmManager;
import java.util.Date;
import android.util.Log;
public class ProjectCheck extends InterventionActivity implements AnswerFragment.OnFragmentInteractionListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (findViewById(R.id.answerContainer) != null) {

            if (savedInstanceState != null) {
                return;
            }
            ButtonAnswerFragment firstAnswer = ButtonAnswerFragment.newInstance("Great!", "positiveResponse", "Alright", "neutralResponse", "I'm worried.", "notResponse");

            setArisText("How's the " + lang.getActivity() +  " going?");
            //setArisText("I'm sorry Dave, I'm afraid I can't do that.");
            getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,firstAnswer).commit();
        }
    }

    public void positiveResponse(View view){
        setArisMoodHappy();
        setArisText("Awesome! Good Work! Keep it up!");
        user.addMood(1.0f);
        clearAnswer();

        ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("Thanks Aris!","goodbye","","","","");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,thanks).commit();
    }



    public void neutralResponse(View view){
        clearAnswer();
        setArisMoodNeutral();
        Calendar cal = Calendar.getInstance();
        //get 24 hour hour
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour>17){
            howManyHours(view);

        } else{
            setArisText("Are you going to revise today?");


        }


    }

    public void notResponse(View view){
        //TODO: Check if they should be revising at the moment
        //If so ask why they arent?
        //if not ask when they will next
        setArisMoodWorried();
        clearAnswer();
        setArisText("Oh no. Why is that?");
        user.addMood(-0.2f);
        clearAnswer();
        //ReasonBadQuestion newAnswer = new ReasonBadQuestion();
        ButtonAnswerFragment newAnswer =
                ButtonAnswerFragment.newInstance(
                        "I keep getting distracted", "distractedResponse",
                        "I'm bored", "boredResponse",
                        "I feel terrible", "terribleResponse"
                );
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, newAnswer).commit();

    }



    public void distractedResponse(View view){
        //TODO: recommend distraction techniques

        clearAnswer();

        setArisText("If you get bored, try reading parts in funny accents!");
        ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("I'll try that.","goodbye","","","","");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,thanks).commit();

    }

    public void boredResponse(View view){
        //TODO: Fetch ted talk to do with persons subject
        clearAnswer();
        setArisMoodHappy();
        setArisText("Try watching a TED TALK!");
        ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("I'll try that.","goodbye","","","","");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,thanks).commit();
    }

    public void terribleResponse(View view){
        //user.addMood(-1.0f);
        setArisMoodHappy();
        clearAnswer();
        setArisText("Try and excercise in the morning before you revise. It'll really wake you up.");
        ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("Thanks for the advice Aris","goodbye","","","","");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,thanks).commit();
    }
    public void onFragmentInteraction(Uri uri){
        clearAnswer();

    }
}
