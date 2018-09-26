package com.example.android.buyandsellhomes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements MainActivityComs{

    private ListView mNavDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    public DataManager dataManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataManager = new DataManager(getApplicationContext());

        // We will come back here in a minute!
        mNavDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mActivityTitle = getTitle().toString();
// We will finish off this method next
// From here
        // Initialize an array with our titles from strings.xml
        String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
// Initialize our ArrayAdapter
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navMenuTitles);
// Set the adapter to the ListView
        mNavDrawerList.setAdapter(mAdapter);
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mNavDrawerList.setOnItemClickListener
                (new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view, int whichItem, long id) {
                        switchFragment(whichItem);
                    }
                });
        switchFragment(0);

    }

    private void switchFragment(int position) {
        Fragment fragment = null;
        String fragmentID = "";
        switch (position) {
            case 0:
                fragmentID = "TITLES";
                Bundle args = new Bundle();
                args.putString("Tag", "_NO_TAG");
                fragment = new MainFragment();
                fragment.setArguments(args);
                break;
            case 1:
                fragmentID = "buy";
                fragment = new BuyHomeFragment();
                break;
            case 2:
                fragmentID = "sell";
                fragment = new SellHomeFragment();
                break;
            case 3:
                fragmentID = "sold";
                fragment = new SoldHomesFragment();
                break;
            case 4:
                fragmentID = "contactUs";
                fragment = new ContactUsFragment();
                break;
            case 5:
                fragmentID = "about";
                fragment = new AboutFragment();
                break;

            default:
                break;
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentHolder, fragment, fragmentID).commit();
        // Close the drawer
        mDrawerLayout.closeDrawer(mNavDrawerList);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            // Called when drawer is opened
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Make selection");
// triggers call to onPrepareOptionsMenu
                invalidateOptionsMenu();
            }
            // Called when drawer is closed
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
// triggers call to onPrepareOptionsMenu
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
// Close drawer if open
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
//drawer is open so close it
            mDrawerLayout.closeDrawer(mNavDrawerList);
        }else {
// Go back to titles fragment
// Quit if already at titles fragment
            Fragment f = getFragmentManager().findFragmentById(R.id.fragmentHolder);
            if (f instanceof MainFragment) {
                finish();
                System.exit(0);
            }else if (f instanceof ViewFragment){
                switchFragment(1);
            }else if (f instanceof SoldHomesViewFragment){
                switchFragment(3);
            }else{
                switchFragment(0);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//noinspection SimplifiableIfStatement
        if (id == R.string.action_settings) {
            return true;
        }
// Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onImageButtonClicked(String clickedImageButton){
        Log.e("clickedImageButton", clickedImageButton);
        Fragment fragment = null;
        if(clickedImageButton == "buy"){
            fragment = new BuyHomeFragment();
        }else if(clickedImageButton == "sell"){
            fragment = new SellHomeFragment();
        }else if(clickedImageButton == "sold"){
            fragment = new SoldHomesFragment();
        }else if(clickedImageButton == "about"){
            fragment = new AboutFragment();
        }

// Start the fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace
                (R.id.fragmentHolder, fragment, "TAGS").commit();

        mNavDrawerList.setItemChecked(1, true);
        mNavDrawerList.setSelection(1);
        mDrawerLayout.closeDrawer(mNavDrawerList);
    }

    @Override
    public void onTitlesListItemSelected(int position) {
// Load up the bundle with the row _id
        Bundle args = new Bundle();
        args.putInt("Position", position);
// Create the fragment and add the bundle
        ViewFragment fragment = new ViewFragment();
        fragment.setArguments(args);
// Start the fragment
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().
                    replace(R.id.fragmentHolder, fragment, "VIEW").commit();
// update selected item and title, then close the drawer
            mNavDrawerList.setItemChecked(1, true);
            mNavDrawerList.setSelection(1);
//setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mNavDrawerList);
        } else {
// error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void onSoldListItemSelected(int position) {
// Load up the bundle with the row _id
        Bundle args = new Bundle();
        args.putInt("Position", position);
// Create the fragment and add the bundle
        SoldHomesViewFragment fragment = new SoldHomesViewFragment();
        fragment.setArguments(args);
// Start the fragment
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().
                    replace(R.id.fragmentHolder, fragment, "VIEW").commit();
// update selected item and title, then close the drawer
            mNavDrawerList.setItemChecked(1, true);
            mNavDrawerList.setSelection(1);
//setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mNavDrawerList);
        } else {
// error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void onHomeUploaded(){
// Create the fragment and add the bundle
        MainFragment fragment = new MainFragment();

// Start the fragment
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().
                    replace(R.id.fragmentHolder, fragment, "MAIN").commit();
// update selected item and title, then close the drawer
            mNavDrawerList.setItemChecked(1, true);
            mNavDrawerList.setSelection(1);
//setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mNavDrawerList);
        } else {
// error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void onSoldHouse(){
// Create the fragment and add the bundle
        MainFragment fragment = new MainFragment();

// Start the fragment
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().
                    replace(R.id.fragmentHolder, fragment, "MAIN").commit();
// update selected item and title, then close the drawer
            mNavDrawerList.setItemChecked(1, true);
            mNavDrawerList.setSelection(1);
//setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mNavDrawerList);
        } else {
// error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void onRefreshBuyHomeFragment() {
        BuyHomeFragment fragment = new BuyHomeFragment();

// Start the fragment
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().
                    replace(R.id.fragmentHolder, fragment, "MAIN").commit();
// update selected item and title, then close the drawer
            mNavDrawerList.setItemChecked(1, true);
            mNavDrawerList.setSelection(1);
//setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mNavDrawerList);
        } else {
// error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void onRefreshSoldHomeFragment() {
        SoldHomesFragment fragment = new SoldHomesFragment();

// Start the fragment
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().
                    replace(R.id.fragmentHolder, fragment, "MAIN").commit();
// update selected item and title, then close the drawer
            mNavDrawerList.setItemChecked(1, true);
            mNavDrawerList.setSelection(1);
//setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mNavDrawerList);
        } else {
// error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}
