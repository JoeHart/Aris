package com.hart.aris.app;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import android.util.Log;

public class WelcomeActivity extends InterventionActivity implements AnswerFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setArisText("Hi! My name is Aris. Your new personal tutor.");
        ButtonAnswerFragment helloButton = ButtonAnswerFragment.newInstance("Hi Aris!","nameCheck","","","","");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, helloButton).commit();
        }
        hideArisText();

    }

    public void nameCheck(View v){
        if(getUserName()){

            String text = "So you're name is " + user.getName() + "?";
            clearAnswer();
            setArisText(text);
            ButtonAnswerFragment yesNoFragment = ButtonAnswerFragment.newInstance("Yes","subjectUpdate","No","nameUpdate","","");
                getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,  yesNoFragment).commit();
        } else {
            nameUpdate(v);
        }
    }

    public void subjectUpdate(View v){
        setArisMoodNeutral();
        clearAnswer();
        setArisText("What subject are you studying?");
        TextAnswerFragment subjectAnswerFragment = TextAnswerFragment.newInstance("subjectAdd");

        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, subjectAnswerFragment).commit();
    }

    public void subjectAdd(View v, String subject){
        user.setSubject(subject);
        clearAnswer();
        setArisMoodHappy();
        setArisText(subject + ", that sounds fun. What type of project are you working on?");
        ButtonAnswerFragment projectTypeAnswerFragment = ButtonAnswerFragment.newInstance("Dissertation","dissertationResponse","Exams", "examsResponse","Coursework","courseworkResponse");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, projectTypeAnswerFragment).commit();
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

    public void dateResponse(View v, Date d){
        setArisMoodHappy();
        clearAnswer();
        SharedPreferences pref = getSharedPreferences("userdata",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        long datetime = d.getTime();
        editor.putLong("deadline", datetime);
        Date date = new Date();
        Log.e(date.toString(),d.toString());
        long days=0;
        try {
            Date date1 = d;
            Date date2 = date;
            long diff = date1.getTime() - date2.getTime();
            days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
        }
        int daysLeft = safeLongToInt(days);

        if (daysLeft<7){
            setArisText("Thats not very long only " +days + " days to go! How ready do you feel?");
        } else{
            if(daysLeft<30){
                int weeks =daysLeft/7;
                setArisText("Thats ok we've got " + weeks + " weeks left. How ready do you feel?");
            } else{
                int months = daysLeft/30;
                setArisText("That's ages! We've got " + months + " months left. How ready do you feel?");
            }
        }

        ButtonAnswerFragment readyAnswer = ButtonAnswerFragment.newInstance("I'm confident","confidentResponse", "I should be fine","fineResponse", "I'm screwed","screwedResponse");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, readyAnswer).commit();
    }

    public void confidentResponse(View v){
        clearAnswer();
        setArisMoodHappy();
        user.addMood(1.0f);
        setArisText("Awesome, " + user.getName() +"! Well I'll check up on you after awhile to just make sure you're still doing fine. Bye!");
        setWelcomeComplete();
    }

    public void setWelcomeComplete(){
        SharedPreferences appData = getSharedPreferences("app_data", MODE_PRIVATE);
        appData.edit().putBoolean("setup_completed",true).commit();
    }

    public void fineResponse(View v){
        clearAnswer();

        setArisText("Don't worry " + user.getName()+ ", with me you'll be more than fine. When are you studying next?");
        setWelcomeComplete();
        //TODO: complete this
    }

    public void screwedResponse(View v){
        clearAnswer();
        setArisMoodWorried();
        setArisText("Oh no " + user.getName() + ", that's not good. I'm sure we can sort it out. When are you studying next?");
        user.addMood(-1.0f);
        setWelcomeComplete();
        //TODO: complete this
    }

    public void dissertationResponse(View v){
        setArisMoodNeutral();
        clearAnswer();
        setProjectType("dissertaion");
        setArisText("When is your dissertation due?");
        DateAnswerFragment dateFrag = DateAnswerFragment.newInstance("dateResponse");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, dateFrag).commit();

    }

    public void examsResponse(View v){
        setArisMoodNeutral();
        clearAnswer();
        setProjectType("exams");
        setArisText("When is your first exam?");
        DateAnswerFragment dateFrag = DateAnswerFragment.newInstance("dateResponse");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, dateFrag).commit();
    }

    public void courseworkResponse(View v){
        setArisMoodNeutral();
        clearAnswer();
        setProjectType("coursework");
        setArisText("When is your coursework due?");
        DateAnswerFragment dateFrag = DateAnswerFragment.newInstance("dateResponse");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, dateFrag).commit();
    }

    public void setProjectType(String s){
        SharedPreferences pref = getSharedPreferences("userdata",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("projectType",s);
        edit.commit();
    }

    public void nameUpdate(View v){
        setArisMoodNeutral();
        clearAnswer();
        setArisText("What do you want to be called?");

        TextAnswerFragment nameAnswerFragment = TextAnswerFragment.newInstance("nameTest");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, nameAnswerFragment).commit();
    }

    public void nameTest(View v, String name){
        setArisMoodHappy();
        setArisText("Hi " + name + ", it's nice to meet you. Am I saying that right?");
        SharedPreferences pref = getSharedPreferences("userdata",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("name",name);
        edit.commit();
        clearAnswer();
        ButtonAnswerFragment yesNoFragment = ButtonAnswerFragment.newInstance("Yes","subjectUpdate","No","nameUpdate","","");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, yesNoFragment).commit();

    }


    public boolean getUserName(){
        String testName="";
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
                if(columnName.equals("display_name")){
                    if(!columnValue.isEmpty()) {
                        SharedPreferences pref = getSharedPreferences("userdata",MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        columnValue.trim();
                        String arr[] = columnValue.split(" ", 2);
                        String firstWord = arr[0];
                        edit.putString("name",firstWord);
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


    public void onFragmentInteraction(Uri uri){
        clearAnswer();

    }
}
