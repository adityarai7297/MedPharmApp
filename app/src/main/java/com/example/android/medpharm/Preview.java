package com.example.android.medpharm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Preview extends AppCompatActivity implements View.OnClickListener {
    Button b1, b2;
    TextView t1, t2, t3, t4, t5, t6, t7;
    String data;
    ArrayList<String>Filename = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        t1 = (TextView) findViewById(R.id.doc_name);
        t2 = (TextView) findViewById(R.id.patient_name);
        t3 = (TextView) findViewById(R.id.patient_phone);
        t4 = (TextView) findViewById(R.id.patient_age);
        t5 = (TextView) findViewById(R.id.date);
        t6 = (TextView) findViewById(R.id.med);
        t7 = (TextView) findViewById(R.id.diago);
        ArrayList<String> list = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String one = extras.getString("text1");
            t3.setText(one);
            String two = extras.getString("text2");
            t2.setText(two);
            String three = extras.getString("text3");
            String four = extras.getString("text4");
            t4.setText(three + "/" + four);
            String five = extras.getString("text5");
            t7.setText(five);
            list = extras.getStringArrayList("list");
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        t5.setText(formattedDate);
        for (int j = 0; j < list.size(); j++) {
            t6.setText(t6.getText() + "\n" + list.get(j));
        }
        b1 = (Button) findViewById(R.id.send);
        b2 = (Button) findViewById(R.id.edit);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = "Doctor Name " + t1.getText() + "\n" + "Patient Name " + t2.getText() + "\n" + "Patient Phone " + t3.getText() + "\n" + "Age/Sex " + t4.getText() + "\n" + "Date " + t5.getText() + "\n" + t6.getText() + "Diagnosis "+ t7.getText();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
                String formattedDate = df.format(c.getTime());
                String filename = "prescription"+".txt";
                Filename.add(filename);
                try {
                    File myFile = new File("/sdcard/download/"+ filename);
                    myFile.createNewFile();
                    FileOutputStream fOut = new FileOutputStream(myFile);
                    OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                    myOutWriter.append(data);
                    myOutWriter.close();
                    fOut.close();
                    Toast.makeText(getApplicationContext(), filename + " Saved", Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent i1 = new Intent(Preview.this,Bluetooth.class);
                i1.putExtra("file",filename);
                startActivity(i1);
            }
        });
        b2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Preview.this, EditPrescription.class);
        i.putExtra("one",t2.getText());
        i.putExtra("two",t3.getText());
        i.putExtra("",t4.getText());
        i.putExtra("diag",t7.getText());
        startActivity(i);
    }
}

