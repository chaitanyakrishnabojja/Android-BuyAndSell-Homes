package com.example.android.buyandsellhomes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentDialog extends DialogFragment {

    private MainActivityComs mMainActivityComs;
    private DataManager d;
    private String paymentType;
    private boolean clickedBtnCancel = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle b = getArguments();
        final int DBID = b.getInt("DBID");
        final String homeName = b.getString("HomeName");

        d = new DataManager(getActivity().getApplicationContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_payment, null);

        final TextView txtHomeName = (TextView)dialogView.findViewById(R.id.txtHomeName);
        txtHomeName.setText(homeName);
        final EditText txtName = (EditText)dialogView.findViewById(R.id.txtName);
        final RadioGroup rdgrp = (RadioGroup)dialogView.findViewById(R.id.rdgrp);
        final RadioButton rdoBtnCredit = (RadioButton)dialogView.findViewById(R.id.rdoBtnCredit);
        final RadioButton rdoBtnDebit = (RadioButton)dialogView.findViewById(R.id.rdoBtnDebit);
        rdoBtnCredit.setChecked(true);


        final EditText txtCardNumber = (EditText)dialogView.findViewById(R.id.txtCardNumber);
        final EditText txtExpDate = (EditText)dialogView.findViewById(R.id.txtExpDate);
        final EditText txtCVV = (EditText)dialogView.findViewById(R.id.txtCVV);
        final TextView txtError = (TextView)dialogView.findViewById(R.id.txtError);

        Button btnCancel = (Button)dialogView.findViewById(R.id.btnCancel);
        Button btnBuy = (Button)dialogView.findViewById(R.id.btnBuy);

        builder.setView(dialogView).setMessage("Buy Home: " + homeName);

        rdgrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                switch (rb.getId()) {
                    case R.id.rdoBtnCredit:
                        paymentType = "credit";
                        break;
                    case R.id.rdoBtnDebit:
                        paymentType = "debit";
                        break;
                }
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedBtnCancel = true;
                dismiss();
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String cardNumber = txtCardNumber.getText().toString();
                String expDate = txtExpDate.getText().toString();
                String cvv = txtCVV.getText().toString();

                if(name.isEmpty() || cardNumber.isEmpty() || expDate.isEmpty() || cvv.isEmpty()){
                    txtError.setText("Please fill all the details");
                }else {
                    d.soldHouse(DBID, name, paymentType, cardNumber, expDate, cvv);
                    Toast.makeText(getActivity(), "Sold Out", Toast.LENGTH_LONG).show();
                    dismiss();
                }

            }
        });

        return  builder.create();

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(clickedBtnCancel){

        }else {
            mMainActivityComs.onSoldHouse();
        }

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
