package com.example.android.buyandsellhomes;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ContactUsFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_us_fragment, container, false);

        final EditText txtName = (EditText)view.findViewById(R.id.txtName);
        final String name = txtName.getText().toString();

        final EditText txtEmail = (EditText)view.findViewById(R.id.txtEmail);
        final String email = txtEmail.getText().toString();

        final EditText txtQuery = (EditText)view.findViewById(R.id.txtQuery);
        final String query = txtQuery.getText().toString();

        final TextView txtError = (TextView)view.findViewById(R.id.txtError);

        Button btnSend = (Button)view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = txtName.getText().toString();
                final String email = txtEmail.getText().toString();
                final String query = txtQuery.getText().toString();

                if(name.isEmpty() || email.isEmpty() || query.isEmpty()){
                    txtError.setText("Please fill all the details");
                    Log.e("error", "error in contact details");

                }else {

                    txtError.setText("");
                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    myIntent.setType("text/plain");
                    myIntent.putExtra(Intent.EXTRA_TEXT, name + ", " + email + ", " + query);
                    startActivity(myIntent);

                }

            }
        });


        return view;
    }
}
