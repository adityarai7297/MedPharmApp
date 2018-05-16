package com.example.android.medpharm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Search extends AppCompatActivity {

    TextView nameTv,medTv,genericTv,genTv,sideeddfectTv,sideTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Bundle b = getIntent().getExtras();
        final String medName = b.getString("med");

        nameTv = (TextView) findViewById(R.id.nameTv);
        medTv = (TextView) findViewById(R.id.medTv);
        genericTv = (TextView) findViewById(R.id.genericTv);
        genTv = (TextView) findViewById(R.id.genTv);
        sideeddfectTv = (TextView) findViewById(R.id.sideeffectTv);
        sideTv = (TextView) findViewById(R.id.sideTv);

        medTv.setText(medName);

        DatabaseReference database2 = FirebaseDatabase.getInstance().getReference();

        database2.child("Medicines").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Basically, this says "For each DataSnapshot *Data* in dataSnapshot, do what's inside the method.
                for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()){
                    //Get the suggestion by childing the key of the string you want to get.
                    String suggestion1 = suggestionSnapshot.child("name").getValue(String.class);
                    //Add the retrieved string to the list
                    if(suggestion1.equalsIgnoreCase(medName)){
                        String generic = suggestionSnapshot.child("generic").getValue(String.class);
                        String sideEffects = suggestionSnapshot.child("sideEffects").getValue(String.class);
                        genTv.setText(generic);
                        sideTv.setText(sideEffects);
                        break;

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
