package com.example.bhavesh.roadtraffic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Bhavesh on 15-02-2018.
 */

public class Report extends FragmentActivity implements View.OnClickListener {

    EditText locationSource;
    EditText locationDest;
    EditText editRemarks;
    TextView distance;
    Button searchButton;
    private String radio_signal=null;
    private DatabaseReference mDatabase;
    private String currentDateTimeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_layout);

        locationSource = (EditText) findViewById(R.id.editSource);
        locationDest = (EditText) findViewById(R.id.editDest);
        editRemarks = (EditText)findViewById(R.id.editRemarks);
        distance = (TextView) findViewById(R.id.textDistance);
        searchButton = (Button) findViewById(R.id.submit);
        searchButton.setOnClickListener(this);
        mDatabase= FirebaseDatabase.getInstance().getReference();

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.high:
                if (checked)
                    radio_signal="high";
                break;
            case R.id.medium:
                if (checked)
                    radio_signal="medium";
                break;
            case R.id.low:
                if (checked)
                    radio_signal="low";
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:

                String source = locationSource.getText().toString().trim();
                String destination = locationDest.getText().toString().trim();
                String remarks = editRemarks.getText().toString().trim();
                //To get system date and time
                currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                if (source != null && !source.equals("") && destination != null && !destination.equals("") && radio_signal!=null) {
                    Details details =new Details(source,destination,currentDateTimeString,radio_signal,remarks);
                    //HashMap<String,Details> dataMap=new HashMap<String, Details>();
                    //dataMap.put(source+"_"+destination,details);
                    mDatabase.child(source+"_"+destination).push().setValue(details).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Report.this,"Stored..",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(Report.this,"Error..",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(this,"Enter all the fields",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

}