package com.hart.aris.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.content.Context;
import java.util.Calendar;
import java.util.Date;
import java.lang.reflect.Method;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.hart.aris.app.TextAnswerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.hart.aris.app.TextAnswerFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DateAnswerFragment extends AnswerFragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param method method to call when first button pressed
     * @return A new instance of fragment ButtonAnswerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DateAnswerFragment newInstance(String method) {
        DateAnswerFragment fragment = new DateAnswerFragment();
        Bundle args = new Bundle();
        args.putString("methodString",method);
        fragment.setArguments(args);
        return fragment;
    }
    public static DateAnswerFragment newInstance(String method,String studyType, Class activity) {
        DateAnswerFragment fragment = new DateAnswerFragment();
        Bundle args = new Bundle();
        args.putString("methodString",method);
        args.putString("studyType",studyType);
        args.putString("classType",activity.toString());
        fragment.setArguments(args);
        return fragment;
    }
    public DateAnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_answer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            Button buttonView1 =(Button) this.getView().findViewById(R.id.submitButton);

            //Set methods to be called when buttons are clickerd
            buttonView1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    method(getArguments().getString("methodString"));

                }
            });

            DatePicker datePicker = (DatePicker) getView().findViewById(R.id.datePicker);
            Calendar cal = Calendar.getInstance();
            long time = cal.getTimeInMillis();
            Date today = new Date();
            long todayLong = today.getTime();
            datePicker.setMinDate(time-1000);



        }
    }

    public void method(String methodName){
        Activity current = getActivity();
        if(getArguments().getString("studyType","").isEmpty()) {

            try {

                Method method = getActivity().getClass().getMethod(methodName, View.class, Date.class);
                DatePicker datePicker = (DatePicker) getView().findViewById(R.id.datePicker);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                switch (month) {
                    case 0:
                        month = Calendar.JANUARY;
                        break;
                    case 1:
                        month = Calendar.FEBRUARY;
                        break;
                    case 2:
                        month = Calendar.MARCH;
                        break;
                    case 3:
                        month = Calendar.APRIL;
                        break;
                    case 4:
                        month = Calendar.MAY;
                        break;
                    case 5:
                        month = Calendar.JUNE;
                        break;
                    case 6:
                        month = Calendar.JULY;
                        break;
                    case 7:
                        month = Calendar.AUGUST;
                        break;
                    case 8:
                        month = Calendar.SEPTEMBER;
                        break;
                    case 9:
                        month = Calendar.OCTOBER;
                        break;
                    case 10:
                        month = Calendar.NOVEMBER;
                        break;
                    case 11:
                        month = Calendar.DECEMBER;
                        break;
                    default:
                        month = Calendar.JANUARY;
                        break;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                Date deadline = calendar.getTime();
                method.invoke(current, getView(), deadline);
            } catch (Exception e) {
                Log.e("METHOD ERROR1", methodName);
            }
        } else{
            String studyType = getArguments().getString("studyType");
            String classType = getArguments().getString("classType");

            Log.e("study",studyType);
            Log.e("class",classType);
            try {

                String className = classType.substring(classType.lastIndexOf(" ")+1);
                Class c =  Class.forName(className);
                Method method = getActivity().getClass().getMethod(methodName, View.class, Date.class,String.class,Class.class);
                DatePicker datePicker = (DatePicker) getView().findViewById(R.id.datePicker);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                switch (month) {
                    case 0:
                        month = Calendar.JANUARY;
                        break;
                    case 1:
                        month = Calendar.FEBRUARY;
                        break;
                    case 2:
                        month = Calendar.MARCH;
                        break;
                    case 3:
                        month = Calendar.APRIL;
                        break;
                    case 4:
                        month = Calendar.MAY;
                        break;
                    case 5:
                        month = Calendar.JUNE;
                        break;
                    case 6:
                        month = Calendar.JULY;
                        break;
                    case 7:
                        month = Calendar.AUGUST;
                        break;
                    case 8:
                        month = Calendar.SEPTEMBER;
                        break;
                    case 9:
                        month = Calendar.OCTOBER;
                        break;
                    case 10:
                        month = Calendar.NOVEMBER;
                        break;
                    case 11:
                        month = Calendar.DECEMBER;
                        break;
                    default:
                        month = Calendar.JANUARY;
                        break;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                Date deadline = calendar.getTime();
                method.invoke(current, getView(), deadline, studyType,c);
            } catch (Exception e) {
                Log.e("METHOD ERROR2", methodName);
                e.printStackTrace();
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public void hideKeyboard(EditText et){
        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AnswerFragment.OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
