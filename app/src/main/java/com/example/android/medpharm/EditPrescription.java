package com.example.android.medpharm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class EditPrescription extends AppCompatActivity implements View.OnClickListener
{
    Button b;
    EditText e1,e2,e3;
    RadioGroup r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_prescription);
        e1= (EditText) findViewById(R.id.patient_phone);
        e2= (EditText) findViewById(R.id.patient_name);
        e3= (EditText) findViewById(R.id.patient_age);
        r= (RadioGroup) findViewById(R.id.mf);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String one = extras.getString("one");
            e2.setText(one);
            String two = extras.getString("two");
            e1.setText(two);
            String three = extras.getString("three");
            e3.setText(three);
        }
        b= (Button) findViewById(R.id.button);
        b.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent i = new Intent(this,Prescription.class);
        String one=e1.getText().toString();
        i.putExtra("text1",one);
        String two=e2.getText().toString();
        i.putExtra("text2",two);
        String three=e3.getText().toString();
        i.putExtra("text3",three);
        String four;
        if(r.getCheckedRadioButtonId() == R.id.m)
        {
            four="M";
        }
        else
        {
            four="F";
        }
        i.putExtra("text4",four);
        startActivity(i);
    }
}
