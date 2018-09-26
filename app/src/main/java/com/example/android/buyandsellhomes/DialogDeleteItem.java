package com.example.android.buyandsellhomes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DialogDeleteItem extends DialogFragment {

    private static int dbID;
    private static String title;
    private DataManager d;
    private MainActivityComs mMainActivityComs;

    public static DialogDeleteItem deleteItemId(int id, String title){
        DialogDeleteItem dialogInstance = new DialogDeleteItem();
        DialogDeleteItem.dbID = id;
        DialogDeleteItem.title = title;
        return dialogInstance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        d = new DataManager(getActivity().getApplicationContext());


        builder.setMessage("Delete Item: " + DialogDeleteItem.title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            d.deleteOriginalTableRowById(DialogDeleteItem.dbID);
                            dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });



        return  builder.create();
    }

    @Override
    public void onDismiss(DialogInterface frag) {
        super.onDismiss(frag);
        mMainActivityComs.onRefreshBuyHomeFragment();
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
