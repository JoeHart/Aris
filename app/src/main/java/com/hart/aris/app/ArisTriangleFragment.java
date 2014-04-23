package com.hart.aris.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


/**
 *
 */
public class ArisTriangleFragment extends Fragment {
    public Animation arisGlow;
    public ArisTriangleFragment() {
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
        return inflater.inflate(R.layout.fragment_aris_triangle, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //load Animations
        arisGlow = AnimationUtils.loadAnimation(getView().getContext(), R.anim.aris_glow);
        //Start the Aris glowing
        ImageView glow = (ImageView) getView().findViewById(R.id.glowImageView);
        glow.startAnimation(arisGlow);
    }
}
