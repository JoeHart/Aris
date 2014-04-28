package com.hart.aris.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class ProjectCheckActivity extends InterventionActivity implements AnswerFragment.OnFragmentInteractionListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (findViewById(R.id.answerContainer) != null) {

            if (savedInstanceState != null) {
                return;
            }
            ButtonAnswerFragment firstAnswer = ButtonAnswerFragment.newInstance("Great!", "positiveResponse", "Alright", "neutralResponse", "I'm worried.", "notResponse");

            setArisText("How's the " + lang.getActivityNoun() + " going?");
            //setArisText("I'm sorry Dave, I'm afraid I can't do that.");
            getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, firstAnswer).commit();
        }
    }

    public void positiveResponse(View view) {
        setArisMoodHappy();
        setArisText("Awesome! Good Work! Keep it up!");
        //setArisText("Joe is so fucked. My creator is a moron");
        user.addMood(1.0f);
        clearAnswer();

        ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("Thanks Aris!", "goodbye", "", "", "", "");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, thanks).commit();
    }


    public void neutralResponse(View view) {
        clearAnswer();
        setArisMoodNeutral();
        Calendar cal = Calendar.getInstance();
        //get 24 hour hour
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour > 17) {
            howManyHours(view);

        } else {
            setArisText("Are you going to " + user.getProjectVerb() + " today?");

            ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("Yes", "laterToday", "No", "noResponse", "I already have", "howManyHours");
            getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, thanks).commit();
        }
    }

    public void laterToday(View view) {
        clearAnswer();
        setArisMoodHappy();
        setArisText("Ok I'll catch up with you later today.");
        Calendar cal = Calendar.getInstance();

        Log.e("Hour of day", Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
        addPromise(lang.getActivityNoun(), ProjectCheckActivity.class, cal.getTime());

        ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("See you then", "goodbye", "", "", "", "");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, thanks).commit();
    }

    public void notResponse(View view) {
        setArisMoodWorried();
        clearAnswer();
        setArisText("Oh no. Why is that?");
        user.addMood(-0.2f);
        clearAnswer();
        ButtonAnswerFragment newAnswer =
                ButtonAnswerFragment.newInstance(
                        "I keep getting distracted", "distractedResponse",
                        "I'm bored", "boredResponse",
                        "I feel terrible", "terribleResponse"
                );
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, newAnswer).commit();

    }


    public void distractedResponse(View view) {
        //TODO: recommend distraction techniques

        clearAnswer();

        setArisText("If you get bored, try reading parts in funny accents!");
        ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("I'll try that.", "goodbye", "", "", "", "");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, thanks).commit();

    }

    public void boredResponse(View view) {
        //TODO: Fetch ted talk to do with persons subject
        clearAnswer();
        setArisMoodHappy();
        setArisText("Try watching a ted talk about your subject. Here I've searched for some for you.");
        ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("View Ted Talks", "tedTalk", "I'll watch one later", "goodbye", "", "");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, thanks).commit();
    }

    public void tedTalk(View view) {
        String url = "https://www.ted.com/search?q=";
        String subject = user.getSubject();
        subject = subject.replace(" ", "+");
        String urlSearch = url + subject;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(urlSearch));
        startActivity(i);

    }

    public void terribleResponse(View view) {
        //user.addMood(-1.0f);
        setArisMoodHappy();
        clearAnswer();
        setArisText("Try and excercise in the morning before you " + user.getProjectVerb() + ". It'll really wake you up.");
        ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("Thanks for the advice Aris", "goodbye", "", "", "", "");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, thanks).commit();
    }

    public void onFragmentInteraction(Uri uri) {
        clearAnswer();

    }
}
