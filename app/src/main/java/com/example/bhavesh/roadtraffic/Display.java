package com.example.bhavesh.roadtraffic;

import android.content.Intent;
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


import java.util.ArrayList;



/**
 * Created by Bhavesh on 19-02-2018.
 */

public class Display extends FragmentActivity{

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
                        mDetails.add("Frequency : "+details.getFrequency()+"\n\n  "+details.getDateTime()+"\n\nRemarks: "+details.getRemarks());
                        arrayAdapter.notifyDataSetChanged();
                        details.getSource();
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
                    Intent i = new Intent(Display.this, Show_map.class);
                    i.putExtra("source", source);
                    i.putExtra("destination", dest);
                    startActivity(i);
                }
            });

        }

    }


}
