package com.example.android.medpharm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class SendPrescription extends AppCompatActivity implements View.OnClickListener{
    Button b;
    EditText e1,e2,e3,e4;
    RadioGroup r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_prescription);
        e1= (EditText) findViewById(R.id.patient_phone);
        e2= (EditText) findViewById(R.id.patient_name);
        e3= (EditText) findViewById(R.id.patient_age);
        e4= (EditText) findViewById(R.id.diag);
        r= (RadioGroup) findViewById(R.id.mf);
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

        String five=e4.getText().toString();
        i.putExtra("text5",five);
        startActivity(i);
    }
}
