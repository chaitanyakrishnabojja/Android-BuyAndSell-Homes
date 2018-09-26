package com.example.android.buyandsellhomes;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SellHomeFragment extends Fragment implements LocationListener {

    private MainActivityComs mMainActivityComs;
    private static final int CAMERA_REQUEST = 123;
    private ImageView mImageView;
    // The filepath for the photo
    String mCurrentPhotoPath;

    // A reference to our database
    private DataManager mDataManager;

    // Where the captured image is stored
    private Uri mImageUri = Uri.EMPTY;

    // For the Location
//    private Location mLocation = new Location("");
    private Location mLocation;
    private LocationManager mLocationManager;
    private String mProvider;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataManager = new DataManager(getActivity().getApplicationContext());

        // Initialize mLocationManager
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        mProvider = mLocationManager.getBestProvider(criteria, false);


        try {
            mLocation = mLocationManager.getLastKnownLocation(mProvider);
            // Initialize the location
            if (mLocation != null) {
                onLocationChanged(mLocation);
            }

        }catch (SecurityException e){

        }





    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.sell_home_fragment, container, false);

        mImageView = (ImageView)view.findViewById(R.id.imageView);
//        Button btnCapture = (Button)view.findViewById(R.id.btnCapture);
        Button btnSave = (Button)view.findViewById(R.id.btnSave);
        final EditText mEditTextTitle = (EditText)view.findViewById(R.id.editTextTitle);
        final EditText editTextPrice = (EditText)view.findViewById(R.id.editTextPrice);
        final EditText editTextCity = (EditText)view.findViewById(R.id.editTextCity);
        final EditText editTextProvince = (EditText)view.findViewById(R.id.editTextProvince);
        final EditText editTextPostalCode = (EditText)view.findViewById(R.id.editTextPostalCode);
        final EditText editTextDescription = (EditText)view.findViewById(R.id.editTextDescription);
// Listen for clicks on the capture button
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
// Error occurred while creating the File
                    Log.e("error", "error creating file");
                }
// Continue only if the File was successfully created
                if (photoFile != null) {
                    mImageUri = Uri.fromFile(photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mImageUri != null) {
                    if (!mImageUri.equals(Uri.EMPTY)) {
// We have a photo to save
                        House house = new House();
                        house.setTitle(mEditTextTitle.getText().toString());
                        house.setStorageLocation(mImageUri);

                        // Set the current GPS location
                        house.setGpsLocation(mLocation);
// What is in the tags
                        String city = editTextCity.getText().toString();
                        String province = editTextProvince.getText().toString();
                        String postalCode = editTextPostalCode.getText().toString();
                        String description = editTextDescription.getText().toString();
                        String title = mEditTextTitle.getText().toString();

//                        Log.e("title", mEditTextTitle.getText().toString());
//                        Log.e("city", city);
//                        Log.e("province", province);
//                        Log.e("postalCode", postalCode);
//                        Log.e("description", description);
//                        Log.e("price", editTextPrice.getText().toString());
                        double price = 0;


                        if (title.isEmpty() || city.isEmpty() || province.isEmpty() || postalCode.isEmpty() || description.isEmpty() || editTextPrice.getText().toString().isEmpty()) {
//                            Toast.makeText(getActivity(), "Please fill all the details", Toast.LENGTH_LONG).show();
                            TextView textViewError = (TextView)view.findViewById(R.id.textViewError);
                            textViewError.setText("Please fill all the details");
                        } else {

                            price = Double.parseDouble(editTextPrice.getText().toString());

// Assign the strings to the Photo object
                            house.setPrice(price);
                            house.setCity(city);
                            house.setProvince(province);
                            house.setPostalCode(postalCode);
                            house.setDescription(description);
// Send the new object to our DataManager
                            mDataManager.addHouse(house);
                            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();

                            mMainActivityComs.onHomeUploaded();
                        }


                    } else {
// No image
                        Toast.makeText(getActivity(), "No image to save", Toast.
                                LENGTH_LONG).show();
                    }
                } else {
// Uri not initialized
                    Log.e("Error ", "uri is null");
                }
            }

        });




        return view;
    }

    private File createImageFile() throws IOException {
// Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, // filename
                ".jpg", // extension
                storageDir // folder
        );
// Save for use with ACTION_VIEW Intent
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            try {
                mImageView.setImageURI(Uri.parse(mImageUri.toString()));
            }catch(Exception e){
                Log.e("Error","Uri not set");
            }
        }else{
            mImageUri = Uri.EMPTY;
        }
    }

    public void onDestroy(){
        super.onDestroy();
// Make sure we don't run out of memory
//            BitmapDrawable bd = (BitmapDrawable) mImageView.getDrawable();
//            bd.getBitmap().recycle();
            mImageView.setImageBitmap(null);
    }

    @Override
    public void onLocationChanged(Location location) {
// Update the location if it changed

        Log.e("latitude", "" + location.getLatitude());
        Log.e("longitude", "" + location.getLongitude());
    }
    @Override
    public void onStatusChanged(String provider,
                                int status, Bundle extras) {
    }
    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onProviderDisabled(String provider) {
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
