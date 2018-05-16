package com.example.android.medpharm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PharmacistActivity extends AppCompatActivity {

    private DatabaseReference mUsersDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference mNotification1;
    private FirebaseUser mCurrent_user1;
    Button buttonNotif;
    TextView presc;
    String user_id="X2tCAH0dsoZNVtIqoSEw9KQXcdi2";
    //String user_id = "6Zq9lxoQSzexGpBizc6hQNFwxw62";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist);

        Bundle b = getIntent().getExtras();
        String content = b.getString("content");

       // user_id = b.getString("user_id");
        Toast.makeText(this, user_id, Toast.LENGTH_SHORT).show();


        mNotification1 = FirebaseDatabase.getInstance().getReference().child("notification1");
        Toast.makeText(this, "notification 1 created", Toast.LENGTH_SHORT).show();

        mCurrent_user1 = FirebaseAuth.getInstance().getCurrentUser();


        buttonNotif = (Button) findViewById(R.id.buttonNotif);
        presc = (TextView) findViewById(R.id.presc);

        presc.setText(content);

//        mNotification = FirebaseDatabase.getInstance().getReference().child("notifications");
//        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

//        buttonNotif.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String notifi = "Yes I have all the medicines!";
//
//                HashMap<String, String> notificationData = new HashMap<>();
//                notificationData.put("From", mCurrent_user.getUid());
//                notificationData.put("Content", notifi);
//
//                mNotification.child(user_id).push().setValue(notificationData);
//            }
//        });



    }

    public void notif1(View view) {
        String pharma_response = "Yes";

        HashMap<String, String> notificationData1 = new HashMap<>();
        notificationData1.put("From", mCurrent_user1.getUid());
        notificationData1.put("Content", pharma_response);

        mNotification1.child(user_id).push().setValue(notificationData1);
        Toast.makeText(this, "Your Response has been sent!", Toast.LENGTH_LONG).show();
    }
}
