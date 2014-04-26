package com.hart.aris.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;

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
public class TextAnswerFragment extends AnswerFragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param method method to call when first button pressed
     * @return A new instance of fragment ButtonAnswerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TextAnswerFragment newInstance(String method) {
        TextAnswerFragment fragment = new TextAnswerFragment();
        Bundle args = new Bundle();
        args.putString("methodString",method);
        fragment.setArguments(args);
        return fragment;
    }
    public TextAnswerFragment() {
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
        return inflater.inflate(R.layout.fragment_text_answer, container, false);
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



        }
    }

    public void method(String methodName){
        Activity current = getActivity();
        try {

            Method method = getActivity().getClass().getMethod(methodName,View.class,String.class);
            EditText eText = (EditText) getView().findViewById(R.id.nameEditText);
            hideKeyboard(eText);
            String name = eText.getText().toString();
            method.invoke(current,getView(),name);
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
