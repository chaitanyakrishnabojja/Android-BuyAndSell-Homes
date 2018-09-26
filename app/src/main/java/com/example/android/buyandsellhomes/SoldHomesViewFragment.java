package com.example.android.buyandsellhomes;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class SoldHomesViewFragment extends Fragment {
    private MainActivityComs mMainActivityComs;
    private Cursor mCursor;
    private ImageView mImageView;
    DataManager d;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Where is the photo object we want to show?
        int position = getArguments().getInt("Position");
// Load the appropriate photo from db
        d = new DataManager(getActivity().getApplicationContext());
        mCursor = d.getHouse(position);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        TextView textViewTitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView textViewPrice = (TextView) view.findViewById(R.id.txtPrice);
        TextView textViewCity = (TextView) view.findViewById(R.id.textViewCity);
        TextView textViewProvince = (TextView) view.findViewById(R.id.textViewProvince);
        TextView textViewPostalCode = (TextView) view.findViewById(R.id.textViewPostalCode);
        TextView textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);
        TextView textViewSaleStatus = (TextView) view.findViewById(R.id.textViewSaleStatus);
        TextView txtBuyer = (TextView) view.findViewById(R.id.txtBuyer);
        Button buttonShowLocation = (Button) view.findViewById(R.id.buttonShowLocation);
// Set the text from the tile column of the data.
        textViewTitle.setText(mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_TITLE)));
        textViewPrice.setText("Price: $" + mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_PRICE)));
        textViewCity.setText("City: " + mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_CITY)));
        textViewProvince.setText("Province: " + mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_PROVINCE)));
        textViewPostalCode.setText("Postal Code: " + mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_POSTAL_CODE)));
        textViewDescription.setText("Description: " + mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_DESCRIPTION)));
        textViewSaleStatus.setText("Status: " + mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_SALE_STATUS)));
        txtBuyer.setText("Buyer: " + mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_BUYER_NAME)));
        mImageView = (ImageView) view.findViewById(R.id.imageView);
// Load the image into the TextView via the URI
        mImageView.setImageURI(Uri.parse(mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_URI))));

        buttonShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude = Double.valueOf(mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_LOCATION_LAT)));
                double longitude = Double.valueOf(mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_LOCATION_LONG)));
// Create a URI from the latitude and longitude
                Log.e("showinglatitude", "" + latitude);
                Log.e("showinglongitude", "" + longitude);
                String uri = String.format(Locale.ENGLISH,"geo:%f,%f", latitude, longitude);
// Create a Google maps intent
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
// Start the maps activity
                getActivity().startActivity(intent);
            }
        });

        Button btnBuy = (Button)view.findViewById(R.id.btnBuy);
        String status = textViewSaleStatus.getText().toString();

        if(status.contains("sold")){
            btnBuy.setVisibility(View.INVISIBLE);
        }

//        btnBuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int DBID = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_ID)));
//                d.soldHouse(DBID);
//                Toast.makeText(getActivity(), "Sold Out", Toast.LENGTH_LONG).show();
//                mMainActivityComs.onSoldHouse();
//            }
//        });

        return view;
    }

    public void onDestroy(){
        super.onDestroy();
// Make sure we don't run out of memory
        BitmapDrawable bd = (BitmapDrawable) mImageView.getDrawable();
        bd.getBitmap().recycle();
        mImageView.setImageBitmap(null);
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
