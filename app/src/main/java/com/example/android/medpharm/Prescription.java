package com.example.android.medpharm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Prescription extends AppCompatActivity implements View.OnClickListener{
    Button b;
    ListView l;
    String s,one,two,three,four,five;
    ArrayList<String> listItems=new ArrayList<>();
    ArrayAdapter<String> adapter1;
    AutoCompleteTextView autoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            one = extras.getString("text1");
            two = extras.getString("text2");
            three = extras.getString("text3");
            four = extras.getString("text4");
            five = extras.getString("text5");
        }
//        final ArrayList<String> medicines = new ArrayList<>();
//        medicines.add("Crocin");
//        medicines.add("Sinarest");
//        medicines.add("Limcee");
//        medicines.add("Pan 40");

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.search);

        //Nothing special, create database reference.
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        //Create a new ArrayAdapter with your context and the simple layout for the dropdown menu provided by Android
        final ArrayAdapter<String> autoComplete = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        //Child the root before all the push() keys are found and add a ValueEventListener()
        database.child("Medicines").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                autoComplete.clear();
                //Basically, this says "For each DataSnapshot *Data* in dataSnapshot, do what's inside the method.
                for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()){
                    //Get the suggestion by childing the key of the string you want to get.
                    String suggestion = suggestionSnapshot.child("name").getValue(String.class);
                    //Add the retrieved string to the list
                    autoComplete.add(suggestion);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        autoCompleteTextView.setAdapter(autoComplete);


        l= (ListView) findViewById(R.id.list);
        adapter1=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        l.setAdapter(adapter1);
        //final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, medicines);
        //final AutoCompleteTextView search = (AutoCompleteTextView) findViewById(R.id.search);
        //search.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoCompleteTextView.setText(autoComplete.getItem(position));
                final AlertDialog.Builder builder = new AlertDialog.Builder(Prescription.this);
                View mview = getLayoutInflater().inflate(R.layout.dialog, null);
                final RadioGroup r1= (RadioGroup) mview.findViewById(R.id.tabsyr);
                final NumberPicker n1= (NumberPicker) mview.findViewById(R.id.dosage);
                final NumberPicker n2= (NumberPicker) mview.findViewById(R.id.count);
                final EditText e1= (EditText) mview.findViewById(R.id.mg);
                n1.setMaxValue(20);
                n1.setMinValue(1);
                n2.setMaxValue(5);
                n2.setMinValue(1);
                Button but = (Button) mview.findViewById(R.id.but);
                builder.setView(mview);
                final AlertDialog dialog = builder.create();
                dialog.show();
                but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(r1.getCheckedRadioButtonId()== R.id.tab)
                        {
                            s="Tablet "+autoCompleteTextView.getText()+"\n"+"Dosage "+n1.getValue()+"days"+"*"+n2.getValue()+"\n"+"Potency "+e1.getText().toString()+" mg"+"\n";
                        }
                        if(r1.getCheckedRadioButtonId()== R.id.syr)
                        {
                            s="Syrup "+autoCompleteTextView.getText()+"\n"+"Dosage "+n1.getValue()+"days"+"*"+n2.getValue()+"\n"+"Potency "+e1.getText().toString()+" mg"+"\n";
                        }
                        if(r1.getCheckedRadioButtonId()== R.id.crm)
                        {
                            s="Cream "+autoCompleteTextView.getText()+"\n"+"Dosage "+n1.getValue()+"days"+"*"+n2.getValue()+"\n"+"Potency "+e1.getText().toString()+" mg"+"\n";
                        }
                        listItems.add(s);
                        adapter1.notifyDataSetChanged();
                        //l.setText(l.getText()+"\n"+s);
                        Toast.makeText(Prescription.this, "Added Successful", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
        b = (Button) findViewById(R.id.submit);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Prescription.this,Preview.class);
        i.putExtra("text1",one);
        i.putExtra("text2",two);
        i.putExtra("text3",three);
        i.putExtra("text4",four);
        i.putExtra("text5",five);
        i.putStringArrayListExtra("list",listItems);
        startActivity(i);
    }
}
