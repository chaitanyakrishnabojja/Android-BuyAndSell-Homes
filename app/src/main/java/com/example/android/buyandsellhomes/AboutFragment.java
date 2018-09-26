package com.example.android.buyandsellhomes;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class AboutFragment extends Fragment {

    ViewPager viewPager;
    PagerAdapter adapter;
    int[] images;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);

        // reference the images and put them in our array
        images = new int[] { R.drawable.image1,
                R.drawable.image2,
                R.drawable.image3,
                R.drawable.image4,
                R.drawable.image5,
                R.drawable.image6 };
// get a reference to the ViewPager in the layout
        viewPager = (ViewPager) view.findViewById(R.id.pager);
// Initialize our PagerAdapter
        adapter = new ImagePagerAdapter(inflater.getContext(), images);
// Bind the PagerAdapter to the ViewPager
        viewPager.setAdapter(adapter);




        return view;
    }
}
