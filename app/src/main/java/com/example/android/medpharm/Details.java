package com.example.android.medpharm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Details extends AppCompatActivity {

    EditText name,type,phone;
    Button button;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle b = getIntent().getExtras();
        final String uid  = b.getString("uid");
        final String email = b.getString("email");
        final String token = b.getString("token");

        //Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

        name = (EditText) findViewById(R.id.name);
        type = (EditText) findViewById(R.id.type);
        phone = (EditText) findViewById(R.id.phone);


        button = (Button) findViewById(R.id.button);





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = name.getText().toString();
                String t = type.getText().toString();
                String p = phone.getText().toString();
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                HashMap<String, String> userMap1 = new HashMap<>();
                userMap1.put("name",n);
                userMap1.put("type",t);
                userMap1.put("email",email);
                userMap1.put("phone",p);
                userMap1.put("device_token",token);
                mDatabase.setValue(userMap1);
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });


    }
}
