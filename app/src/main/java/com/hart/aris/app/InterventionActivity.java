package com.hart.aris.app;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;

public class InterventionActivity extends FragmentActivity implements AnswerFragment.OnFragmentInteractionListener {
    private TextView arisText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention);

        initializeArisText();

        ArisTriangleFragment arisTriangleFragment = new ArisTriangleFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.arisTriangleContainer, arisTriangleFragment).commit();
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
