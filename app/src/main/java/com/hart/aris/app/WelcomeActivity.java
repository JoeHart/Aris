package com.hart.aris.app;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

public class WelcomeActivity extends InterventionActivity implements AnswerFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setArisText("Hi! My name is Aris. Your new personal tutor.");
        ButtonAnswerFragment helloButton = ButtonAnswerFragment.newInstance("Hi Aris!", "nameCheck", "", "", "", "");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, helloButton).commit();
        }
        hideArisText();

    }

    public void nameCheck(View v) {
        if (getUserName()) {

            String text = "So you're name is " + user.getName() + "?";
            clearAnswer();
            setArisText(text);
            ButtonAnswerFragment yesNoFragment = ButtonAnswerFragment.newInstance("Yes", "subjectUpdate", "No", "nameUpdate", "", "");
            getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, yesNoFragment).commit();
        } else {
            nameUpdate(v);
        }
    }

    public void subjectUpdate(View v) {
        setArisMoodNeutral();
        clearAnswer();
        setArisText("What subject are you studying?");
        TextAnswerFragment subjectAnswerFragment = TextAnswerFragment.newInstance("subjectAdd");

        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, subjectAnswerFragment).commit();
    }

    public void subjectAdd(View v, String subject) {
        user.setSubject(subject);
        clearAnswer();
        setArisMoodHappy();
        setArisText(subject + ", that sounds fun. What type of project are you working on?");
        ButtonAnswerFragment projectTypeAnswerFragment = ButtonAnswerFragment.newInstance("Dissertation", "dissertationResponse", "Exams", "examsResponse", "Coursework", "courseworkResponse");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, projectTypeAnswerFragment).commit();
    }


    public void dateResponse(View v, Date d) {
        setArisMoodHappy();
        clearAnswer();
        user.setDeadline(d);

        //Add projecte dependant check
        if (user.getProjectType().equals("exam")) {
            int daysUntilExam = lang.getDaysUntilInt(d);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.DAY_OF_MONTH, -(2));
            Log.e("PastPaperDate", cal.toString());
            Log.e("Month date: ", Integer.toString(cal.get(Calendar.MONTH)));
            Log.e("Month day: ", Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
            Log.e("Month hour: ", Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
            addPromise("past paper", PastPaperCheckActivity.class, cal.getTime());
        }
        setArisText(lang.getTimeReaction(d) + " How do you feel?");

        ButtonAnswerFragment readyAnswer = ButtonAnswerFragment.newInstance("I'm confident", "confidentResponse", "I should be fine", "fineResponse", "I'm screwed", "screwedResponse");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, readyAnswer).commit();
    }

    public void confidentResponse(View v) {
        clearAnswer();
        setArisMoodHappy();
        user.addMood(1.0f);
        setArisText("Awesome, " + user.getName() + "! Well I'll check up on you after awhile to just make sure you're still doing fine. Bye!");
        setWelcomeComplete();

        ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("Bye Aris!", "closeAris", "", "", "", "");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, thanks).commit();
    }

    public void setWelcomeComplete() {
        SharedPreferences appData = getSharedPreferences("app_data", MODE_PRIVATE);
        appData.edit().putBoolean("setup_completed", true).commit();
    }

    public void fineResponse(View v) {
        clearAnswer();

        setArisText("Don't worry " + user.getName() + ", with me you'll be more than fine. When are you studying next?");
        setWelcomeComplete();
        nextStudyCheck(lang.getActivityNoun(), ProjectCheck.class);
    }

    public void screwedResponse(View v) {
        clearAnswer();
        setArisMoodWorried();
        setArisText("Oh no " + user.getName() + ", that's not good. I'm sure we can sort it out. When are you studying next?");
        user.addMood(-1.0f);
        setWelcomeComplete();
        nextStudyCheck(lang.getActivityNoun(), ProjectCheck.class);
    }

    public void dissertationResponse(View v) {
        setArisMoodNeutral();
        clearAnswer();
        user.setProjectType("dissertation");
        setArisText("When is your dissertation due?");
        DateAnswerFragment dateFrag = DateAnswerFragment.newInstance("dateResponse");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, dateFrag).commit();

    }

    public void examsResponse(View v) {
        setArisMoodNeutral();
        clearAnswer();

        user.setProjectType("exam");
        setArisText("When is your first exam?");
        DateAnswerFragment dateFrag = DateAnswerFragment.newInstance("dateResponse");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, dateFrag).commit();
    }

    public void courseworkResponse(View v) {
        setArisMoodNeutral();
        clearAnswer();
        user.setProjectType("coursework");
        setArisText("When is your coursework due?");
        DateAnswerFragment dateFrag = DateAnswerFragment.newInstance("dateResponse");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, dateFrag).commit();
    }

    public void nameUpdate(View v) {
        setArisMoodNeutral();
        clearAnswer();
        setArisText("What do you want to be called?");

        TextAnswerFragment nameAnswerFragment = TextAnswerFragment.newInstance("nameTest");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, nameAnswerFragment).commit();
    }

    public void nameTest(View v, String name) {
        setArisMoodHappy();
        setArisText("Hi " + name + ", it's nice to meet you. Am I saying that right?");
        SharedPreferences pref = getSharedPreferences("userdata", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("name", name);
        edit.commit();
        clearAnswer();
        ButtonAnswerFragment yesNoFragment = ButtonAnswerFragment.newInstance("Yes", "subjectUpdate", "No", "nameUpdate", "", "");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, yesNoFragment).commit();

    }


    public boolean getUserName() {
        String testName = "";
        Cursor c = getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
        int count = c.getCount();
        String[] columnNames = c.getColumnNames();
        boolean b = c.moveToFirst();
        int position = c.getPosition();
        if (count == 1 && position == 0) {
            for (int j = 0; j < columnNames.length; j++) {
                String columnName = columnNames[j];
                String columnValue = c.getString(c.getColumnIndex(columnName));

                //Log.e(columnName,"blarg");
                if (columnName.equals("display_name")) {
                    if (!columnValue.isEmpty()) {
                        SharedPreferences pref = getSharedPreferences("userdata", MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        columnValue.trim();
                        String arr[] = columnValue.split(" ", 2);
                        String firstWord = arr[0];
                        edit.putString("name", firstWord);
                        edit.commit();
                        return true;
                    }

                }
            }
        }
        c.close();
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.initial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onFragmentInteraction(Uri uri) {
        clearAnswer();

    }
}
