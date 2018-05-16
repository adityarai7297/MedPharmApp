package com.example.android.medpharm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity {

    //EditText uname;
    EditText email;
    EditText pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //uname = (EditText) findViewById(R.id.editText2);
        email = (EditText) findViewById(R.id.emailEditText);
        pass = (EditText) findViewById(R.id.passEditText);
        //Parse.initialize(this, "h8up9mUy8RC8uubGJJKzPDbXANhqMlZ8jt7diWUH", "CxYmqiAgyaHArADvGZUSn9w2peO9wfzkbgYYdtwe");


    }

    public void LoginClick(View view){
        ParseUser.logInInBackground(email.getText().toString(), pass.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    Context context = getApplicationContext();
                    CharSequence text = "Signed In!";
                    int duration = Toast.LENGTH_LONG;
                    String fname = parseUser.getString("Fname");
                    String email = parseUser.getString("email");
                    //nameTxt.setText(fname);


                    //ParseObject uType = new ParseObject("userType");
                    //uType= parseUser.get("suerType");

                    String utype = parseUser.getString("userType");
                    Toast toast = Toast.makeText(context, utype, duration);
                    toast.show();

                    if(utype.equals("Doctor"))
                    {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("name",fname);
                        intent.putExtra("email",email);
                        intent.putExtra("flag","1");
                        setResult(RESULT_OK,intent);
                        finish();
                        //startActivity(intent);
                    }
                    else if(utype.equals("Patient"))
                    {
                        //Toast.makeText(context, "HIII", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,MainActivity.class);
                        intent.putExtra("name",fname);
                        intent.putExtra("email",email);
                        intent.putExtra("flag","2");
                        setResult(RESULT_OK,intent);
                        finish();
                        //startActivity(intent);
                    }
                    else
                    {
                        Toast toast2 = Toast.makeText(context, "Did not find!!", duration);
                        toast2.show();
                    }


                    /*
                    ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                    userQuery.whereEqualTo("userType", "Doctor");
                    userQuery.whereEqualTo("objectId", parseUser.getObjectId());
                    userQuery.findInBackground(new FindCallback<ParseUser>() {
                        void done(List<ParseUser> results, ParseException e) {
                            // results has the list of users with a hometown team with a winning record

                        }
                    });*/



                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Signed In Error!!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }


    }


