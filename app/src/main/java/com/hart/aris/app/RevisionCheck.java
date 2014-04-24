package com.hart.aris.app;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

public class RevisionCheck extends InterventionActivity implements AnswerFragment.OnFragmentInteractionListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (findViewById(R.id.answerContainer) != null) {

            if (savedInstanceState != null) {
                return;
            }
            AnswerFragment firstAnswer = AnswerFragment.newInstance("Great!","positiveResponse","Alright","neutralResponse","I'm not revising.","notResponse");

            setArisText("How's the revision going?");

            getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,firstAnswer).commit();
        }
    }

    public void positiveResponse(View view){

        setArisText("Awesome! Good Work! Keep it up!");
        //TODO: Implement storage of positive response
        clearAnswer();
    }



    public void neutralResponse(View view){
        setArisText("Oh no. Why is that?");
        clearAnswer();
        //ReasonBadQuestion newAnswer = new ReasonBadQuestion();
        AnswerFragment newAnswer =
                AnswerFragment.newInstance(
                        "I keep getting distracted", "distractedResponse",
                        "I'm bored", "boredResponse",
                        "I feel terrible","terribleResponse"
                );
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, newAnswer).commit();

    }

    public void notResponse(View view){
        //TODO: Check if they should be revising at the moment
        //If so ask why they arent?

        //if not ask when they will next
        setArisText("When will you revise next?");
        clearAnswer();


    }

    public void distractedResponse(View view){
        //TODO: recommend distraction techniques
        clearAnswer();
    }

    public void boredResponse(View view){
        //TODO: Fetch ted talk to do with persons subject
        clearAnswer();
    }

    public void terribleResponse(View view){
        //TODO: Advise excercise, sun and eating healthily.
        clearAnswer();
    }
    public void onFragmentInteraction(Uri uri){
        clearAnswer();

    }
}
