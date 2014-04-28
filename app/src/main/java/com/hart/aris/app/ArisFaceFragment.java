package com.hart.aris.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 *
 */
public class ArisFaceFragment extends Fragment {
    //public Animation arisGlow;
    public ArisFaceFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aris_triangle, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void smile() {
        ImageView face = (ImageView) getView().findViewById(R.id.arisFace);
        face.setImageResource(R.drawable.arisfacesmile);
    }

    public void worry() {
        ImageView face = (ImageView) getView().findViewById(R.id.arisFace);
        face.setImageResource(R.drawable.arisfaceworry);
    }

    public void neutral() {
        ImageView face = (ImageView) getView().findViewById(R.id.arisFace);
        face.setImageResource(R.drawable.arisface);
    }
}
