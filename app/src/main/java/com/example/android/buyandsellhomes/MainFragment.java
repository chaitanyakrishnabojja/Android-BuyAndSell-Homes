package com.example.android.buyandsellhomes;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class MainFragment extends Fragment implements View.OnClickListener, Animation.AnimationListener{

    private MainActivityComs mMainActivityComs;

    Animation animFadeIn;
    Animation animFadeOut;
    Animation animFadeInOut;
    Animation animZoomIn;
    Animation animZoomOut;
    Animation animLeftRight;
    Animation animRightLeft;
    Animation animTopBottom;
    Animation animBounce;
    Animation animFlash;
    Animation animRotateLeft;
    Animation animRotateRight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadAnimations();

    }

    private void loadAnimations(){
//        animFadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_in);
//        animFadeOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_out);
//        animFadeInOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_in_out);
        animZoomIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in);
//        animZoomOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_out);
//        animLeftRight = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.left_right);
//        animRightLeft = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.right_left);
//        animTopBottom = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.top_bot);
        animBounce = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.bounce);
        animFlash = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.flash);
//        animRotateLeft = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_left);
//        animRotateRight = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_right);
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        ImageView  imageViewLogo = (ImageView)view.findViewById(R.id.imageViewLogo);
        ImageButton imageButtonBuy = (ImageButton)view.findViewById(R.id.imageButtonBuy);
        ImageButton imageButtonSell = (ImageButton)view.findViewById(R.id.imageButtonSell);
        ImageButton imageButtonSold = (ImageButton)view.findViewById(R.id.imageButtonSold);
        ImageButton imageButtonAbout = (ImageButton)view.findViewById(R.id.imageButtonAbout);

        animBounce.setDuration(2000);
        animBounce.setAnimationListener(this);
        imageViewLogo.startAnimation(animBounce);

        animZoomIn.setDuration(2000);
        animZoomIn.setAnimationListener(this);
        imageButtonBuy.startAnimation(animZoomIn);
        imageButtonSell.startAnimation(animZoomIn);

        animFlash.setDuration(1000);
        animFlash.setAnimationListener(this);
//        imageButtonSold.startAnimation(animFlash);
        imageButtonAbout.startAnimation(animFlash);

        imageButtonBuy.setOnClickListener(this);
        imageButtonSell.setOnClickListener(this);
        imageButtonSold.setOnClickListener(this);
        imageButtonAbout.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButtonBuy:
                mMainActivityComs.onImageButtonClicked("buy");
                break;
            case R.id.imageButtonSell:
                mMainActivityComs.onImageButtonClicked("sell");
                break;
            case R.id.imageButtonSold:
                mMainActivityComs.onImageButtonClicked("sold");
                break;
            case R.id.imageButtonAbout:
                mMainActivityComs.onImageButtonClicked("about");
                break;
        }
    }


    @Override
    public void onAnimationEnd(Animation animation) {

    }
    @Override
    public void onAnimationRepeat(Animation animation) {
    }
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivityComs = (MainActivityComs)activity;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mMainActivityComs = null;
    }
}
