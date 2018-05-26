package com.example.bhavesh.roadtraffic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Bhavesh on 19-02-2018.
 */

public class Display_time extends FragmentActivity{

    Button goToMap;
    private DatabaseReference mDatabase;
    private ListView mUserList;
    private ArrayList<String> mDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_layout);
        goToMap=(Button)findViewById(R.id.map_button);

        if (getIntent().getStringExtra("source") != null && getIntent().getStringExtra("destination") != null) {
            final String source = getIntent().getStringExtra("source");
            final String dest = getIntent().getStringExtra("destination");
            mDatabase=FirebaseDatabase.getInstance().getReference().child(source+"_"+dest);

            //Toast.makeText(this,mDatabase.toString(),Toast.LENGTH_SHORT).show();
            mUserList = (ListView) findViewById(R.id.user_list);
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDetails);
            mUserList.setAdapter(arrayAdapter);

            mDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Details details = dataSnapshot.getValue(Details.class);
                    String[] sp =details.getDateTime().split(" ");
                    String time1 = sp[1];
                    String[] cr = DateFormat.getDateTimeInstance().format(new Date()).split(" ");
                    String time2 = cr[1];

                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = format.parse(time1);
                        date2 = format.parse(time2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long difference = date2.getTime() - date1.getTime();
                    difference = difference/60000;
                    if(difference >= 0 && difference <= 120 && sp[0].equalsIgnoreCase(cr[0])) {
                        mDetails.add("Frequency : " + details.getFrequency() + "\n\nTime : " + difference + "min. Before\n\nRemarks: " + details.getRemarks());
                        arrayAdapter.notifyDataSetChanged();
                        details.getSource();
                    }
                    else {
                        // Creates an Intent that will load a map of San Francisco
                        Uri gmmIntentUri = Uri.parse("geo:0,0");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            goToMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Display_time.this, Show_map.class);
                    i.putExtra("source", source);
                    i.putExtra("destination", dest);
                    startActivity(i);
                }
            });

        }

    }


}
