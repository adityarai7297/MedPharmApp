package com.example.android.medpharm;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Retrieve extends AppCompatActivity implements View.OnClickListener{
    Button b,notif;
    ListView l;
    ArrayList<String> listItems=new ArrayList<>();
    private static final int DISCOVER_DURATION=300;
    private static final int REQUEST_BLU=1;
    String file;
    String aBuffer = "";
    ArrayAdapter<String> adapter1;
    private DatabaseReference mUsersDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference mNotification;
    private FirebaseUser mCurrent_user;
    //pharmacist uid
    ArrayList<String> user_ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

       // mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mNotification = FirebaseDatabase.getInstance().getReference().child("notifications");
        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();


        user_ids = new ArrayList<String>();
        user_ids.add("yGUvc3vo1UVw6dMLrJddB6zMScv2");
        user_ids.add("vCwNpZyqKPUhZMPOlwRViKQtyQ83");
        user_ids.add("YPwl78ANrIPzX85TQ4RKJIh1UVs2");
        user_ids.add("utwG45dnY0WcJZRMAJNvzQnwIwI2");

        notif = (Button) findViewById(R.id.notif);
        l = (ListView) findViewById(R.id.list);
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        l.setAdapter(adapter1);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            file = extras.getString("file");
        }
        b= (Button) findViewById(R.id.button);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String filename = "prescription.txt";
        //String filename=editTextFileName.getText().toString();
        //StringBuffer stringBuffer = new StringBuffer();
        String aDataRow;
        String data;

        int i;
        listItems.clear();
        File directory = new File("/sdcard/bluetooth/");
        File[] files = directory.listFiles();

        for (i = 0; i < files.length; i++) {
            aBuffer="";
            filename = files[i].getName();
            // you can store name to arraylist and use it later
            //filenames.add(filename);

            try {
                File myFile = new File("/sdcard/bluetooth/" + filename);
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
                while ((aDataRow = myReader.readLine()) != null) {
                    aBuffer = aBuffer + aDataRow + "\n";
                }
                myReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            listItems.add(aBuffer);
            adapter1.notifyDataSetChanged();
        }

        try {
            File myFile = new File("/sdcard/bluetooth/" + "prescription-"+i+".txt");
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer = aBuffer + aDataRow + "\n";
            }
            myReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),aBuffer, Toast.LENGTH_LONG).show();
    }

    public void NotificationClick(View view) {

        for (int i=0;i<user_ids.size();i++){

            String notifi = aBuffer;

            HashMap<String, String> notificationData = new HashMap<>();
            notificationData.put("From", mCurrent_user.getUid());
            notificationData.put("Content", notifi);

            mNotification.child(user_ids.get(i)).push().setValue(notificationData);
        }



    }


    public void enableBluetooth(View view) {
        Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVER_DURATION);
        startActivityForResult(discoveryIntent, REQUEST_BLU);
    }
}
