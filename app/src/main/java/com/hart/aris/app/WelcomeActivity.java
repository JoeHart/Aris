package com.hart.aris.app;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class WelcomeActivity extends InterventionActivity implements AnswerFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setArisText("Hi! My name is Aris. Your new personal tutor.");
            ButtonAnswerFragment helloButton = ButtonAnswerFragment.newInstance("Hi Aris!","next","","","","");
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, helloButton).commit();
            }
        hideArisText();

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
