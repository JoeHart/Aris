package com.hart.aris.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.lang.reflect.Method;

//import android.app.Fragment;

/**
 * Fragment for hours answer
 */
public class HoursAnswerFragment extends AnswerFragment {

    public HoursAnswerFragment() {
        // Required empty public constructor
    }

    public static HoursAnswerFragment newInstance(String method) {
        HoursAnswerFragment fragment = new HoursAnswerFragment();
        Bundle args = new Bundle();
        args.putString("methodString", method);
        fragment.setArguments(args);
        return fragment;
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

        Button buttonView1 = (Button) this.getView().findViewById(R.id.submitButton);

        //Set methods to be called when buttons are clickerd
        buttonView1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                method(getArguments().getString("methodString"));

            }
        });
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

    public void method(String methodName) {
        Button buttonView1 = (Button) this.getView().findViewById(R.id.button1);
        TextView textHours = (TextView) getView().findViewById(R.id.hoursTextView);
        int hours = Integer.parseInt(textHours.getText().toString());
        Activity current = getActivity();
        try {

            Method method = getActivity().getClass().getMethod(methodName, View.class, int.class);
            method.invoke(current, getView(), hours);
        } catch (Exception e) {
            Log.e("METHOD ERROR", methodName);
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
}
