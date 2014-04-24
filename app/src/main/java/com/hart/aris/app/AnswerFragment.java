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
 * {@link AnswerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnswerFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AnswerFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

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
     * @return A new instance of fragment AnswerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnswerFragment newInstance(String button1, String method1,String button2,String method2,String button3,String method3) {
        AnswerFragment fragment = new AnswerFragment();
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
    public AnswerFragment() {
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
            /*try {
                this.getResources().getLayout(R.id.button1).setProperty("onClick", getArguments().getString("methodString1"));
                this.getResources().getLayout(R.id.button2).setProperty("onClick", getArguments().getString("methodString2"));
                this.getResources().getLayout(R.id.button3).setProperty("onClick", getArguments().getString("methodString3"));
            } catch (Exception e){

            }*/

                buttonView1.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       method(getArguments().getString("methodString1"));
                                                   }
                                               });
            buttonView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    method(getArguments().getString("methodString2"));
                }
            });
            buttonView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    method(getArguments().getString("methodString3"));
                }
            });

                        buttonView1.setText(getArguments().getString("methodString1"));
            buttonView2.setText(getArguments().getString("methodString2"));
            buttonView3.setText(getArguments().getString("methodString3"));


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
            mListener = (OnFragmentInteractionListener) activity;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
