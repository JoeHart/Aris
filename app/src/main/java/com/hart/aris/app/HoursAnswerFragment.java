package com.hart.aris.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * Fragment for hours answer
 *
 */
public class HoursAnswerFragment extends Fragment {

    public HoursAnswerFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hours_answer, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        //once the fragment has been loaded to a view
        super.onViewCreated(view, savedInstanceState);


        //this links the seekbar to the hours counter on the screen.
        final TextView textHours = (TextView) getView().findViewById(R.id.hoursTextView);
        final SeekBar seekHours = (SeekBar) getView().findViewById(R.id.hoursSeekBar);
        seekHours.setProgress(12);

        seekHours.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textHours.setText(Integer.toString(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textHours.setText(Integer.toString(seekBar.getProgress()));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textHours.setText(Integer.toString(seekBar.getProgress()));
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
}
