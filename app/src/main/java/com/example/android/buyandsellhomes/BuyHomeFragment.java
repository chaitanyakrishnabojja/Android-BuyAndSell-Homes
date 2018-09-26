package com.example.android.buyandsellhomes;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BuyHomeFragment extends ListFragment implements LocationListener{

    private Cursor mCursor;
    private Cursor newCursor;
    private MainActivityComs mMainActivityComs;
    private ArrayList<House> mHouseList = new ArrayList<House>();

    // For the Location
//    private Location mLocation = new Location("");
//    private Location mLocation;
    private Location mLocation;
    private LocationManager mLocationManager;
    private String mProvider;
    private DataManager d;
    HomeListAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


// Get an instance of DataManager
        d = new DataManager(getActivity().getApplicationContext());
//        d.setLatLon(1, "43.653226", "-79.38318429999998");
//        d.setLatLon(2, "43.4642578", "-80.5204096");
//        d.setLatLon(3, "31.9685988", "-99.90181310000003");
//        d.setLatLon(4, "40.7127753", "-74.0059728");

        // Initialize mLocationManager
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        mProvider = mLocationManager.getBestProvider(criteria, false);
        mLocation = mLocationManager.getLastKnownLocation(mProvider);

        if (mLocation != null) {
            onLocationChanged(mLocation);
            Log.e("Presentlat", "" + mLocation.getLatitude());
            Log.e("Presentlng", "" + mLocation.getLongitude());
        }

        double currentLat = mLocation.getLatitude();
        double currentLng = mLocation.getLongitude();


// Get all the titles from the database
        mCursor = d.getAvailableHouses();
        int arrayCount = mCursor.getCount();
//        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(),
//                android.R.layout.simple_list_item_1, mCursor,
//                new String[] { DataManager.TABLE_ROW_TITLE },
//                new int[] { android.R.id.text1 }, 0 );
//
//        int arrayCount = cursorAdapter.getCount();

        Log.i("arrayCount", "" + arrayCount);
        mCursor.moveToFirst();
        if(arrayCount > 0){
            do {
                House newHouse = new House();
                newHouse.setTitle(mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_TITLE)));
                newHouse.setPrice(Double.parseDouble(mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_PRICE))));
                newHouse.setStorageLocation(Uri.parse(mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_URI))));
                Location mtempLocation = new Location("");
                mtempLocation.setLatitude(Double.parseDouble(mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_LOCATION_LAT))));
                mtempLocation.setLongitude(Double.parseDouble(mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_LOCATION_LONG))));
                newHouse.setGpsLocation(mtempLocation);
                Log.i("titles", "" + newHouse.getTitle());
                Log.e("tempLat", "" + mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_LOCATION_LAT)));
                Log.e("tempLng", "" + mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_LOCATION_LONG)));
                double tempLat = newHouse.getGpsLocation().getLatitude();
                double tempLng = newHouse.getGpsLocation().getLongitude();

                double dist = CalculationByDistance(currentLat, currentLng, tempLat, tempLng);
                DecimalFormat df = new DecimalFormat("#.##");
                dist = Double.valueOf(df.format(dist));
                newHouse.setDistance(dist);

                int id = mCursor.getInt(mCursor.getColumnIndex(DataManager.TABLE_ROW_ID));
                String title = mCursor.getString(mCursor.getColumnIndex(DataManager.TABLE_ROW_TITLE));
                Log.e("id", "" + id);
                Log.e("title", "" + title);
                Log.e("dist", "" + dist);
                d.insertRecordIntoNewTable(id, title, dist);
                mHouseList.add(newHouse);
            }while (mCursor.moveToNext());


        }

        Collections.sort(mHouseList, new Comparator<House>() {
            @Override
            public int compare(House h1, House h2) {
                return Double.compare(h1.getDistance(), h2.getDistance());
            }
        });

//        ArrayList<House> sortedList = mHouseList;

        newCursor = d.getSortedHousesList();
//        while (newCursor.moveToNext()){
//            Log.e(newCursor.getString(1), newCursor.getString(2));
//        }
        newCursor.moveToFirst();
        Log.e("reached", "cursor reassigned");

        d.deleteNewTable();

         adapter = new HomeListAdapter(mHouseList);
        setListAdapter(adapter);

    }

    public double CalculationByDistance(double initialLat, double initialLong, double finalLat, double finalLong){
        int R = 6371; // km (Earth radius)
        double dLat = toRadians(finalLat - initialLat);
        double dLon = toRadians(finalLong - initialLong);
        initialLat = toRadians(initialLat);
        finalLat = toRadians(finalLat);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(initialLat) * Math.cos(finalLat);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    public double toRadians(double deg) {
        return deg * (Math.PI/180);
    }

    private class HomeListAdapter extends ArrayAdapter<House>
    {

        public HomeListAdapter(ArrayList<House> houses) {
            super(getActivity(), R.layout.list_item, houses);
        }

        @Override
        public View getView(int whichItem, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getLayoutInflater();
                view = inflater.inflate(R.layout.list_item, null);
            }
// We also have this super-handy getItem method
            House tempHouse = getItem(whichItem);
            TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtTitle.setText(tempHouse.getTitle());

            TextView txtPrice = (TextView)view.findViewById(R.id.txtPrice);

            String tempPriceStr = Double.toString(tempHouse.getPrice());
            txtPrice.setText("Price: $" + tempPriceStr);

            TextView txtDistance = (TextView)view.findViewById(R.id.txtDistance);

            String tempDistanceStr = Double.toString(tempHouse.getDistance());
            txtDistance.setText("" + tempDistanceStr + " km");

            final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            imageView.setImageURI(tempHouse.getStorageLocation());

            return view;
        }
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> av, View v, int position, long id) {
                //Get your item here with the position

                newCursor.moveToPosition(position);
// What is the database _id of this item?
                int dBID = newCursor.getInt(
                        newCursor.getColumnIndex(
                                DataManager.TABLE_SORTED_ROW_ID));
                String title = newCursor.getString(
                        newCursor.getColumnIndex(
                                DataManager.TABLE_SORTED_ROW_TITLE));
                DialogDeleteItem instance = DialogDeleteItem.deleteItemId(dBID, title);
                instance.show(getFragmentManager(), "123");
                return true;
            }

        });
    }



    public void onListItemClick(ListView l, View v, int position, long id) {

// Move the cursor to the clicked item in the list
        newCursor.moveToPosition(position);
// What is the database _id of this item?
        int dBID = newCursor.getInt(
                newCursor.getColumnIndex(
                        DataManager.TABLE_SORTED_ROW_ID));
//// Use the interface to send the clicked _id
        mMainActivityComs.onTitlesListItemSelected(dBID);
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

    // Start updates when app starts/resumes
    @Override
    public void onResume() {
        super.onResume();
        mLocationManager.requestLocationUpdates(mProvider, 500, 1, this);
    }

    // pause the location manager when app is paused/stopped
    @Override
    public void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
//        mLocation = location;
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    @Override
    public void onProviderEnabled(String provider) {

    }
    @Override
    public void onProviderDisabled(String provider) {

    }

}
