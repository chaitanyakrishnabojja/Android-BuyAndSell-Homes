package com.example.android.buyandsellhomes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DialogSoldItemDelete extends DialogFragment {

    private static int dbID;
    private static String title;
    private DataManager d;
    private MainActivityComs mMainActivityComs;

    public static DialogSoldItemDelete deleteItemId(int id, String title){
        DialogSoldItemDelete dialogInstance = new DialogSoldItemDelete();
        DialogSoldItemDelete.dbID = id;
        DialogSoldItemDelete.title = title;
        return dialogInstance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        d = new DataManager(getActivity().getApplicationContext());


        builder.setMessage("Delete Item: " + DialogSoldItemDelete.title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        d.deleteOriginalTableRowById(DialogSoldItemDelete.dbID);
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
        mMainActivityComs.onRefreshSoldHomeFragment();
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
