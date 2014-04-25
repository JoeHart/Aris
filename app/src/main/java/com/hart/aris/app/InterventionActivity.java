package com.hart.aris.app;

import android.accounts.AccountManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Log;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.content.SharedPreferences;

public class InterventionActivity extends FragmentActivity implements AnswerFragment.OnFragmentInteractionListener {
    private TextView arisText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intervention);
        initializeUser();
        //get user name etc
        initializeArisText();


        ArisTriangleFragment arisTriangleFragment = new ArisTriangleFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.arisTriangleContainer, arisTriangleFragment).commit();
    }

    public void initializeUser(){
        /*AccountManager accMan = AccountManager.get(this);
        Account[] acc = accMan.getAccounts();
        for(int i=0; i<acc.length;i++) {
            Log.e("ACCOUNT", acc[i].toString());
        }*/

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
                        Log.e(columnName, columnValue);
                        SharedPreferences pref = getSharedPreferences("userdata",MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("name",columnValue);
                        edit.commit();
                    }else{
                        Log.e(columnName,"No Name");
                        SharedPreferences pref = getSharedPreferences("userdata",MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("name","no_name_error");
                        edit.commit();
                    }

                }
            }
        }
        c.close();

    }

    public void onPause(){
        super.onPause();
        finish();
    }

    public void initializeArisText(){
        arisText=(TextView) findViewById(R.id.arisTextView);
    }

    public void hideArisText(){
            arisText.setVisibility(TextView.INVISIBLE);
            arisText.setAlpha(0.0f);
    }

    public void setArisText(String s){
        arisText.setText(s);
    }

    public void clearAnswer(){
        getSupportFragmentManager().beginTransaction().
                remove(getSupportFragmentManager().findFragmentById(R.id.answerContainer)).commit();
    }

    public void onFragmentInteraction(Uri uri){
        clearAnswer();

    }
}
