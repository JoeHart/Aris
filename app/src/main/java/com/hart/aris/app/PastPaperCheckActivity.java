package com.hart.aris.app;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class PastPaperCheckActivity extends InterventionActivity implements AnswerFragment.OnFragmentInteractionListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (findViewById(R.id.answerContainer) != null) {

            if (savedInstanceState != null) {
                return;
            }
            ButtonAnswerFragment firstAnswer = ButtonAnswerFragment.newInstance("Yes.", "howDidTheyGo", "Not yet.", "recommendPapers", "", "");

            setArisText("Hi " + user.getName() + ". Have you tried any past papers yet? Since you've only got " + lang.getTimeUntilString(user.getDeadline()) + " until your exam!");
            getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, firstAnswer).commit();
        }
    }

    public void recommendPapers(View view) {
        setArisMoodNeutral();
        setArisText("Research shows they're the best ways to prepare for an exam, do you want to do one tomorrow?");
        user.addMood(1.0f);
        clearAnswer();
        ButtonAnswerFragment answer = ButtonAnswerFragment.newInstance("Yes", "addPaperPromise", "Later", "laterPaper", "", "");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, answer).commit();
    }

    public void addPaperPromise(View v) {
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DAY_OF_MONTH,1);
        cal.add(Calendar.DATE, 1);
        addStudyPromise(v, cal.getTime(), "past_paper", PastPaperCheckActivity.class);

    }

    public void laterPaper() {
        clearAnswer();
        setArisText("Ok when will you try a past paper?");
        nextStudyCheck("pastPaper", PastPaperCheckActivity.class);

    }


    public void howDidTheyGo(View view) {
        setArisMoodNeutral();
        clearAnswer();
        setArisText("How did the papers go?");
        ButtonAnswerFragment firstAnswer = ButtonAnswerFragment.newInstance("Quite Well", "positiveResponse", "Alright", "neutralResponse", "Not very well", "badResponse");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, firstAnswer).commit();
    }

    public void positiveResponse(View v) {
        clearAnswer();
        setArisMoodHappy();
        user.addMood(1.0f);
        setArisText("Good work " + user.getName() + "! I'm sure you'll ace the exam.");

        ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("Thanks Aris!", "goodbye", "", "", "", "");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, thanks).commit();
    }

    public void neutralResponse(View v) {
        clearAnswer();
        user.addMood(0.0f);
        setArisText("That's better than nothing, do you know which parts you struggled on?");
        ButtonAnswerFragment firstAnswer = ButtonAnswerFragment.newInstance("Yes", "yesResponse", "No", "noResponse", "", "");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, firstAnswer).commit();
    }

    public void badResponse(View v) {
        clearAnswer();
        setArisMoodWorried();
        user.addMood(-1.0f);
        setArisText("That's not good, do you know which parts you struggled on?");
        ButtonAnswerFragment firstAnswer = ButtonAnswerFragment.newInstance("Yes", "yesResponse", "No", "noResponse", "", "");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, firstAnswer).commit();
    }

    public void yesResponse(View v) {
        clearAnswer();
        setArisMoodHappy();
        setArisText("Well that's half the battle, when are you revising what you're weak on?");
        nextStudyCheck("revising", ProjectCheckActivity.class);
    }

    public void noResponse(View v) {
        clearAnswer();
        setArisMoodNeutral();
        setArisText("Try going back over the paper and see which parts you struggles on. When do you think you'll do that?");
        nextStudyCheck("revising", ProjectCheckActivity.class);
    }

    public void onFragmentInteraction(Uri uri) {
        clearAnswer();

    }
}
