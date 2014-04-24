package com.hart.aris.app;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;

import com.hart.aris.app.R;

import java.util.ArrayList;

public class RevisionCheck extends FragmentActivity implements AnswerFragment.OnFragmentInteractionListener {
    private ArrayList<TextView> arisTextViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision_check);



        ///fragment shizzle
// Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.answerContainer) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
           // answerFragment = new RevisionCheckAnswerFragment();
            AnswerFragment firstAnswer = AnswerFragment.newInstance("Great!","positiveResponse","Alright","neutralResponse","I'm not revising.","notResponse");

            initializeArisText();

            //Aris Fragment
            ArisTriangleFragment arisTriangleFragment = new ArisTriangleFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            // firstFragment.setArguments(getIntent().getExtras());
            //getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, firstFragment);
            getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,firstAnswer).commit();

            getSupportFragmentManager().beginTransaction().add(R.id.arisTriangleContainer, arisTriangleFragment).commit();
        }
    }

    public void onPause(){
        super.onPause();
        finish();
    }

    public void initializeArisText(){
        arisTextViews = new ArrayList<TextView>();
        //put them into the array list for future manipulation
        arisTextViews.add((TextView) findViewById(R.id.arisTextView1));
        arisTextViews.add((TextView) findViewById(R.id.arisTextView2));
        arisTextViews.add((TextView) findViewById(R.id.arisTextView3));

    }

    public void hideArisText(){
        for(TextView t: arisTextViews){
            t.setVisibility(TextView.INVISIBLE);
            t.setAlpha(0.0f);
        }
    }

    public void setArisText(String line1, String line2, String line3){
        arisTextViews.get(0).setText(line1);
        arisTextViews.get(1).setText(line2);
        arisTextViews.get(2).setText(line3);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.revision_check, menu);
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

    public void positiveResponse(View view){

        setArisText("Awesome! Good Work!","Keep it up!","");
        //TODO: Implement storage of positive response
        //Fragment remove = answerFragment;
        clearAnswer();
    }

    public void clearAnswer(){
        getSupportFragmentManager().beginTransaction().
                remove(getSupportFragmentManager().findFragmentById(R.id.answerContainer)).commit();
    }

    public void neutralResponse(View view){
        setArisText("Oh no.","Why's that?","");
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
        setArisText("When will you","revise next?","");
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
