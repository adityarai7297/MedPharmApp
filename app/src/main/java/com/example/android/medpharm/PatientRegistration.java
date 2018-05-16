package com.example.android.medpharm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PatientRegistration extends AppCompatActivity {

    EditText name;
    EditText address;
    EditText phone;
    EditText password;
    EditText confirmPassword;
    TextView error;
    String pname;
    String paddress;
    String pphone;
    String ppassword;
    String pconfirmPassword;
    int i;


    DatabaseReference patientDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name=(EditText)this.findViewById(R.id.pharm_name);
        phone=(EditText)this.findViewById(R.id.pharm_phone);
        address=(EditText)this.findViewById(R.id.pharm_add);
        password=(EditText)this.findViewById(R.id.pharm_pass);
        confirmPassword=(EditText)this.findViewById(R.id.pharm_cpass);
        error=(TextView)this.findViewById(R.id.textView6);

        patientDatabase = FirebaseDatabase.getInstance().getReference();


    }


    public void Register(View view){

        pname= name.getText().toString();
        paddress= address.getText().toString();
        pphone= phone.getText().toString();
        ppassword= password.getText().toString();
        pconfirmPassword= confirmPassword.getText().toString();
       // String id = patientDatabase.push().getKey();
        //Patient p = new Patient(pname,paddress,pphone,ppassword);

        if(ppassword.equals(pconfirmPassword)){
            error.setText("");

           patientDatabase.child("patients").child("1").child("Name").setValue(pname);
           patientDatabase.child("patients").child("1").child("Address").setValue(paddress);
           patientDatabase.child("patients").child("1").child("Phone").setValue(pphone);
           patientDatabase.child("patients").child("1").child("Password").setValue(ppassword);

            Toast.makeText(this, "Successfully Registered. Welcome!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

        else{
            error.setText("Passwords don't match! Try Again!");
            password.setText("");
            confirmPassword.setText("");
        }




    }





}
