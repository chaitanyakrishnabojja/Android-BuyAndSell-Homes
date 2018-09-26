package com.example.android.buyandsellhomes;

public interface MainActivityComs {
    void onImageButtonClicked(String str);
    void onTitlesListItemSelected(int id);
    void onSoldListItemSelected(int id);
    void onHomeUploaded();
    void onSoldHouse();
    void onRefreshBuyHomeFragment();
    void onRefreshSoldHomeFragment();

}
