package com.example.android.buyandsellhomes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataManager {

    private SQLiteDatabase db;

    private static final String DB_NAME = "buy_sell_homes_database1";
    private static final int DB_VERSION = 1;
    private static final String TABLE_HOME = "home_table";
    public static final String TABLE_ROW_ID = "_id";
    public static final String TABLE_ROW_TITLE = "home_title";
    public static final String TABLE_ROW_URI = "image_uri";
    public static final String TABLE_ROW_LOCATION_LAT = "gps_location_lat";
    public static final String TABLE_ROW_LOCATION_LONG = "gps_location_long";
    public static final String TABLE_ROW_PRICE = "price";
    public static final String TABLE_ROW_CITY = "city";
    public static final String TABLE_ROW_PROVINCE = "province";
    public static final String TABLE_ROW_POSTAL_CODE = "postal_code";
    public static final String TABLE_ROW_DESCRIPTION = "description";
    public static final String TABLE_ROW_SALE_STATUS = "sale_status";
    public static final String TABLE_ROW_BUYER_NAME = "buyer_name";
    public static final String TABLE_ROW_PAYMENT_TYPE = "payment_type";
    public static final String TABLE_ROW_CARD_NUMBER = "card_number";
    public static final String TABLE_ROW_EXPIRY_DATE = "expiry_date";
    public static final String TABLE_ROW_CVV = "cvv";


    //sorted list table
    private static final String TABLE_SORTED_HOMES_LIST = "sorted_home_table";
    public static final String TABLE_SORTED_ROW_ID = "sorted_id";
    public static final String TABLE_SORTED_ROW_TITLE = "sorted_title";
    public static final String TABLE_SORTED_ROW_DISTANCE = "sorted_dist";





    public DataManager(Context context) {

        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    // Here are all our helper methods
    public void addHouse(House house){
        // Add all the details to the photos table
        String query = "INSERT INTO " + TABLE_HOME + " (" +
                TABLE_ROW_TITLE + ", " +
                TABLE_ROW_URI + ", " +
                TABLE_ROW_PRICE + ", " +
                TABLE_ROW_LOCATION_LAT + ", " +
                TABLE_ROW_LOCATION_LONG + ", " +
                TABLE_ROW_CITY + ", " +
                TABLE_ROW_PROVINCE + ", " +
                TABLE_ROW_POSTAL_CODE + ", " +
                TABLE_ROW_DESCRIPTION +
                ") " +
                "VALUES (" +
                "'" + house.getTitle() + "'" + ", " +
                "'" + house.getStorageLocation() + "'" + ", " +
                house.getPrice() + ", " +
                house.getGpsLocation().getLatitude() + ", " +
                house.getGpsLocation().getLongitude() + ", " +
                "'" + house.getCity() + "'" + ", " +
                "'" + house.getProvince() + "'" + ", " +
                "'" + house.getPostalCode() + "'" + ", " +
                "'" + house.getDescription() + "'" +
                ");";
        Log.i("addHouse()", query);
        Log.e("addHouse()", query);
        db.execSQL(query);
    }

    public void soldHouse(int id, String buyer_name, String payment_type, String card_no, String exp_date, String cvv){
        // Add all the details to the photos table
        String query = "UPDATE " + TABLE_HOME + " SET " +
                TABLE_ROW_SALE_STATUS + " = 'sold', "  + TABLE_ROW_BUYER_NAME + " = '" + buyer_name + "'," + TABLE_ROW_PAYMENT_TYPE +
                " = '" + payment_type + "'," + TABLE_ROW_CARD_NUMBER + " = '" + card_no + "'," + TABLE_ROW_EXPIRY_DATE + " = '" +
                exp_date + "'," + TABLE_ROW_CVV + " = '" + cvv + "' WHERE " + TABLE_ROW_ID + " = " + id ;
        Log.e("soldHouse()", query);
        db.execSQL(query);
    }

    public Cursor getTitles() {
        Log.i("call", "getTitles in DataManager");
        Cursor c = db.rawQuery("SELECT " + TABLE_ROW_ID + ", " + TABLE_ROW_TITLE +
                " from " + TABLE_HOME, null);
        c.moveToFirst();
        return c;
    }

    public Cursor getAvailableHouses() {
        Log.i("call", "getAvailableHouses in DataManager");
        Cursor c = db.rawQuery("SELECT * from " + TABLE_HOME + " WHERE " + TABLE_ROW_SALE_STATUS + " = 'available'", null);
        c.moveToFirst();
        return c;
    }

    public Cursor getSoldHouses() {
        Log.i("call", "getSoldHouses in DataManager");
        Cursor c = db.rawQuery("SELECT * from " + TABLE_HOME + " WHERE " + TABLE_ROW_SALE_STATUS + " = 'sold'", null);
        c.moveToFirst();
        return c;
    }

    public Cursor getHouse(String title) {
        Log.i("call", "getHouse(String) in DataManager");
        Cursor c = db.rawQuery("SELECT * from " +
                TABLE_HOME +
                " WHERE " +
                TABLE_ROW_TITLE + " = '" + title + "'", null);
        c.moveToFirst();
        return c;
    }

    public Cursor getHouse(int id) {
        Log.i("call", "getHouse(int) in DataManager");
        Cursor c = db.rawQuery("SELECT * from " +
                TABLE_HOME +
                " WHERE " +
                TABLE_ROW_ID + " = " + id, null);
        c.moveToFirst();
        return c;
    }

    public void deleteOriginalTableRowById(int id){
        String query = "DELETE FROM " + TABLE_HOME + " WHERE " + TABLE_ROW_ID + " = " + id;
        Log.e("deleteOldTableRowById()", query);
        db.execSQL(query);
    }


//testing method starts--------------------------------------------------------------------------
    public void setLatLon(int id, String lat, String lng){
        // Add all the details to the photos table
        String query = "UPDATE " + TABLE_HOME + " SET " +
                TABLE_ROW_LOCATION_LAT + " =  '" + lat + "', " + TABLE_ROW_LOCATION_LONG + " =  '" + lng + "'" + " WHERE " + TABLE_ROW_ID + " = " + id ;
        Log.i("setLatLon(id)", query);
        db.execSQL(query);
    }

//testing method Ends--------------------------------------------------------------------------


    public void insertRecordIntoNewTable(int id, String title, Double distance){
        // Add all the details to the photos table
        String query = "INSERT INTO " + TABLE_SORTED_HOMES_LIST + " (" +
                TABLE_SORTED_ROW_ID + ", " +
                TABLE_SORTED_ROW_TITLE + ", " +
                TABLE_SORTED_ROW_DISTANCE +
                ") " +
                "VALUES (" +
                id + ", " +
                "'" + title + "'" + ", " +
                distance +
                ");";
        Log.i("IntoNewTable()", query);
        db.execSQL(query);
    }

    public Cursor getSortedHousesList() {

        String query = "SELECT * from " + TABLE_SORTED_HOMES_LIST + " ORDER BY " + TABLE_SORTED_ROW_DISTANCE + " ASC ";
        Log.e("call", "getSortedHousesList" + query);
        Cursor c = db.rawQuery("SELECT * from " + TABLE_SORTED_HOMES_LIST + " ORDER BY " + TABLE_SORTED_ROW_DISTANCE +
                " ASC ", null);
        c.moveToFirst();
        return c;
    }

    public void deleteNewTable(){
        String query = "DELETE FROM " + TABLE_SORTED_HOMES_LIST + ";";
        Log.e("IntoNewTable()", query);
        db.execSQL(query);
    }




    // This class is created when our DataManager is initialized
    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
        public CustomSQLiteOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }
        // This method only runs the first time the database is created
        @Override
        public void onCreate(SQLiteDatabase db) {
// Create a table for photos and all their details
            String newTableQueryString = "create table "
                    + TABLE_HOME + " ("
                    + TABLE_ROW_ID
                    + " integer primary key autoincrement not null,"
                    + TABLE_ROW_TITLE
                    + " text not null,"
                    + TABLE_ROW_PRICE
                    + " double,"
                    + TABLE_ROW_URI
                    + " text not null,"
                    + TABLE_ROW_LOCATION_LAT
                    + " text not null,"
                    + TABLE_ROW_LOCATION_LONG
                    + " text not null,"
                    + TABLE_ROW_CITY
                    + " text not null,"
                    + TABLE_ROW_PROVINCE
                    + " text not null,"
                    + TABLE_ROW_POSTAL_CODE
                    + " text not null,"
                    + TABLE_ROW_DESCRIPTION
                    + " text not null,"
                    + TABLE_ROW_BUYER_NAME
                    + " text,"
                    + TABLE_ROW_PAYMENT_TYPE
                    + " text,"
                    + TABLE_ROW_CARD_NUMBER
                    + " text,"
                    + TABLE_ROW_EXPIRY_DATE
                    + " text,"
                    + TABLE_ROW_CVV
                    + " text,"
                    + TABLE_ROW_SALE_STATUS
                    + " text default 'available'" + ");";
            db.execSQL(newTableQueryString);

            newTableQueryString = "create table "
                    + TABLE_SORTED_HOMES_LIST + " ("
                    + TABLE_SORTED_ROW_ID
                    + " integer not null,"
                    + TABLE_SORTED_ROW_TITLE
                    + " text not null,"
                    + TABLE_SORTED_ROW_DISTANCE
                    + " double not null"
                    + ");";
            db.execSQL(newTableQueryString);

        }
        // This method only runs when we increment DB_VERSION
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            // Update for version 2
//            String addLongColumn = "ALTER TABLE " +
//                    TABLE_HOME +
//                    " ADD " +
//                    TABLE_ROW_PRICE +
//                    " double;";
//            db.execSQL(addLongColumn);
//            String addLatColumn = "ALTER TABLE " +
//                    TABLE_PHOTOS + " ADD " +
//                    TABLE_ROW_LOCATION_LAT +
//                    " real;";
//            db.execSQL(addLatColumn);
        }
    }
}

