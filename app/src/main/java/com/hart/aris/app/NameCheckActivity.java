package com.hart.aris.app;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;



public class NameCheckActivity extends InterventionActivity implements AnswerFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(getUserName()){
            String name = getSharedPreferences("userdata",MODE_PRIVATE).getString("name","there");
            String text = "So you're name is " + name + "?";
            setArisText(text);
            ButtonAnswerFragment yesNoFragment = ButtonAnswerFragment.newInstance("Yes","nextSetup","No","nameCheck","","");
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,  yesNoFragment).commit();
            }
        } else {
            setArisText("I don't know you're name, what is it?");
            TextAnswerFragment nameAnswerFragment = TextAnswerFragment.newInstance("nameTest");
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, nameAnswerFragment).commit();
            }
        }
        hideArisText();

    }

    public void nameTest(View v, String name){
        setArisText("Hi " + name + ", it's nice to meet you. Am I saying that right?");
        SharedPreferences pref = getSharedPreferences("userdata",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("name",name);
        edit.commit();
        clearAnswer();
        ButtonAnswerFragment yesNoFragment = ButtonAnswerFragment.newInstance("Yes","nextSetup","No","nameCheck","","");
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
