package com.hart.aris.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.util.Log;

import java.lang.reflect.Method;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ButtonAnswerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ButtonAnswerFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ButtonAnswerFragment extends AnswerFragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param button1 Text for the first button
     * @param method1 method to call when first button pressed
     * @param button2 Text for the first button
     * @param method2 method to call when first button pressed
     * @param button3 Text for the first button
     * @param method3 method to call when first button pressed
     * @return A new instance of fragment ButtonAnswerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ButtonAnswerFragment newInstance(String button1, String method1,String button2,String method2,String button3,String method3) {
        ButtonAnswerFragment fragment = new ButtonAnswerFragment();
        Bundle args = new Bundle();
        args.putString("buttonString1",button1);
        args.putString("buttonString2",button2);
        args.putString("buttonString3",button3);
        args.putString("methodString1",method1);
        args.putString("methodString2",method2);
        args.putString("methodString3",method3);
        fragment.setArguments(args);
        return fragment;
    }
    public ButtonAnswerFragment() {
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
        return inflater.inflate(R.layout.fragment_answer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            View container = this.getView().findViewById(R.id.answerContainer);
            Button buttonView1 =(Button) this.getView().findViewById(R.id.button1);
            Button buttonView2 =(Button) this.getView().findViewById(R.id.button2);
            Button buttonView3 =(Button) this.getView().findViewById(R.id.button3);

            //Set methods to be called when buttons are clickerd
            buttonView1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    method(getArguments().getString("methodString1"));
                    }
                 });
            buttonView2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    method(getArguments().getString("methodString2"));
                }
            });
            buttonView3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    method(getArguments().getString("methodString3"));
                }
            });
            String bString1 = getArguments().getString("buttonString1");
            String bString2 = getArguments().getString("buttonString2");
            String bString3 = getArguments().getString("buttonString3");
            if(bString1.isEmpty()){
                buttonView1.setVisibility(View.INVISIBLE);
                buttonView1.setAlpha(0.0f);
            } else{
                buttonView1.setText(bString1);
            }
            if(bString2.isEmpty()){
                buttonView2.setVisibility(View.INVISIBLE);
                buttonView2.setAlpha(0.0f);
            } else{
                buttonView2.setText(bString2);
            }
            if(bString3.isEmpty()){
                buttonView3.setVisibility(View.INVISIBLE);
                buttonView3.setAlpha(0.0f);
            } else{
                buttonView3.setText(bString3);
            }



        }
    }

    public void method(String methodName){
        Button buttonView1 =(Button) this.getView().findViewById(R.id.button1);
        Activity current = getActivity();
        try {

            Method method = getActivity().getClass().getMethod(methodName,View.class);
            method.invoke(current,getView());
        } catch (Exception e) {
            Log.e("METHOD ERROR",methodName);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
