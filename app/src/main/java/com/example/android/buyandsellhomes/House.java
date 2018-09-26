package com.example.android.buyandsellhomes;

import android.location.Location;
import android.net.Uri;

import java.util.Comparator;

public class House{

    private String mTitle;
    private Uri mStorageLocation;
    private double mPrice;
    private String mCity;
    private String mProvince;
    private String mPostalCode;
    private String mDescription;
    public String mSaleStatus;
    private Location mGpsLocation;

    private double mDistance;

    public double getDistance() {
        return mDistance;
    }

    public void setDistance(double Distance) {
        this.mDistance = Distance;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double Price) {
        this.mPrice = Price;
    }

    public Location getGpsLocation() {
        return mGpsLocation;
    }
    public void setGpsLocation(Location mGpsLocation) {
        this.mGpsLocation = mGpsLocation;
    }
    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String title) {
        this.mTitle = title;
    }
    public Uri getStorageLocation() {
        return mStorageLocation;
    }
    public void setStorageLocation(Uri storageLocation) {
        this.mStorageLocation = storageLocation;
    }

    public String getCity() {
        return mCity;
    }


    public void setCity(String City) {
        this.mCity = City;
    }

    public String getProvince() {
        return mProvince;
    }

    public void setProvince(String Province) {
        this.mProvince = Province;
    }

    public String getPostalCode() {
        return mPostalCode;
    }

    public void setPostalCode(String PostalCode) {
        this.mPostalCode = PostalCode;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String Description) {
        this.mDescription = Description;
    }

    public String getSaleStatus() {
        return mSaleStatus;
    }

    public void setSaleStatus(String SaleStatus) {
        this.mSaleStatus = SaleStatus;
    }


}
