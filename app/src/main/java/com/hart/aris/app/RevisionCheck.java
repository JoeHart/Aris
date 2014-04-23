package com.hart.aris.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.hart.aris.app.R;

public class RevisionCheck extends ActionBarActivity {

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
            HoursAnswerFragment firstFragment = new HoursAnswerFragment();


            //Aris Fragment
            ArisTriangleFragment arisTriangleFragment = new ArisTriangleFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            // firstFragment.setArguments(getIntent().getExtras());
            //getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, firstFragment);
            getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,firstFragment).commit();

            getSupportFragmentManager().beginTransaction().add(R.id.arisTriangleContainer, arisTriangleFragment).commit();
        }
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

}
