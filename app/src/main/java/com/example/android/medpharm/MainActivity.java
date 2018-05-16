package com.example.android.medpharm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView selectedImage;
    private Bitmap currentImage;
    TextView nameTxt,emailTxt,welcomeTxt;
    int logFlag = 0;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    String name,email,type;

    public final static String EXTRA_EMAIL = "com.example.myfirstapp.EMAIL";
    public final static String EXTRA_FNAME = "com.example.myfirstapp.FNAME";
    public final static String EXTRA_LNAME = "com.example.myfirstapp.LNAME";
    public final static String EXTRA_PASS = "com.example.myfirstapp.PASS";
    public final static String EXTRA_UTYPE = "com.example.myfirstapp.UTYPE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //nameTxt = (TextView) findViewById(R.id.nameHeadTxt);
        //emailTxt = (TextView) findViewById(R.id.emailHeadTxt);
        //Toast.makeText(this, nameTxt.getText().toString(), Toast.LENGTH_SHORT).show();

//        Parse.initialize(new Parse.Configuration.Builder(this)
//                .applicationId("medpharmAppId")
//                .server("http://medpharm.herokuapp.com/parse")
//                .build()
//        );
        mAuth = FirebaseAuth.getInstance();
           FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null) {
            String uid = currentUser.getUid();
            Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
            mDatabase.keepSynced(true);

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                     name = dataSnapshot.child("name").getValue().toString();
                     email = dataSnapshot.child("email").getValue().toString();
                     type = dataSnapshot.child("type").getValue().toString();
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    View headerView = navigationView.getHeaderView(0);
                    nameTxt = (TextView) headerView.findViewById(R.id.nameHeadTxt);
                    emailTxt = (TextView) headerView.findViewById(R.id.emailHeadTxt);
                    welcomeTxt = (TextView) headerView.findViewById(R.id.welcomeHeadTxt);
                    nameTxt.setText(name);
                    emailTxt.setText(email);
                    welcomeTxt.setText("Welcome"+ " " + type);
                    //Toast.makeText(MainActivity.this, type, Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            //mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }


        else
        {
            Toast.makeText(this, "NO USER", Toast.LENGTH_SHORT).show();
        }
//
//        if(currentUser!=null){
//            String uid = currentUser.getUid();
//            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
//            mUserDatabase.keepSynced(true);
//
//            mUserDatabase.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//
//                    String name = dataSnapshot.child("name").getValue().toString();
//                    String email = dataSnapshot.child("email").getValue().toString();
//                    final String type = dataSnapshot.child("type").getValue().toString();
//
//                    nameTxt.setText(name);
//                    emailTxt.setText(email);
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
//
//        else{
//           //do nothing
//        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

//        emailTxt.setText(email);
//        nameTxt.setText(name);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        //menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                       int x = menuItem.getItemId();

                        if(x==R.id.nav_login){
//                            Intent ii = new Intent(MainActivity.this,Login.class);
//                            startActivity(ii);
//                            logFlag = 1;
                          //startActivityForResult(new Intent(getApplicationContext(),EmailPasswordActivity.class),999);
                            Intent ii = new Intent(getApplicationContext(),EmailPasswordActivity.class);
                            startActivity(ii);

                        }

//                        if(x==R.id.nav_logout){
//                            if(logFlag==1) {
//                                Toast.makeText(MainActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
//                                nameTxt.setText("User Name");
//                                emailTxt.setText("User Email-ID");
//                                logFlag=0;
//                            }
//
//                            else{
//                                Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
//                            }
//                        }

                        if(x==R.id.nav_setting){

                            Intent setIntent = new Intent(MainActivity.this,Settings.class);
                            startActivity(setIntent);
                        }

//                        if (x==R.id.nav_register){
//                            Intent i2 = new Intent(MainActivity.this,EnterEmail.class);
//                            startActivity(i2);
//                        }

//                        if(x==R.id.nav_gallery){
//                            selectedImage = (ImageView) findViewById(R.id.profileImageView);
//                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                            photoPickerIntent.setType("image/*");
//                            startActivityForResult(photoPickerIntent, 1);
//
//                        }

//                        if(x==R.id.nav_camera) {
//                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                                startActivityForResult(takePictureIntent, 2);
//                            }
//                        }

                        if (x==R.id.nav_help){
                            Intent helpIntent = new Intent(MainActivity.this,Help.class);
                            startActivity(helpIntent);
                        }

                        if(x==R.id.nav_prescription){



                                if (type.equalsIgnoreCase("doctor")) {
                                    Intent docIntent = new Intent(getApplicationContext(), DoctorHomeNew.class);
                                    startActivity(docIntent);
                                } else if (type.equalsIgnoreCase("Patient")) {
                                    Intent intent = new Intent(getApplicationContext(), Retrieve.class);
                                    startActivity(intent);
                                    //Toast.makeText(MainActivity.this, "Patient", Toast.LENGTH_SHORT).show();
                                } else if (type.equalsIgnoreCase("Pharmacist")) {
//                                    Intent intent = new Intent(getApplicationContext(), PharmacistActivity.class);
//                                    startActivity(intent);
                                    Toast.makeText(MainActivity.this, "This feature is only for Doctors and Patients!", Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(MainActivity.this, "Patient", Toast.LENGTH_SHORT).show();
                                }

                        }
                        return true;
                    }
                });


        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView;
        Bitmap mBitmap = null;
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri photoUri = data.getData();
                if (photoUri != null) {
                    try {
                        currentImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                        selectedImage.setImageBitmap(currentImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (requestCode == 2) {
//            imageView = (ImageView) findViewById(R.id.profileImageView);
//            if (resultCode == RESULT_OK) {
//                Bundle extras = data.getExtras();
//                mBitmap = (Bitmap) extras.get("data");
//                imageView.setImageBitmap(mBitmap);
//            }
        }

        if (requestCode==999 && resultCode==RESULT_OK){

//            nameTxt.setText(data.getStringExtra("name"));
//            emailTxt.setText(data.getStringExtra("email"));
//            userFlag = data.getStringExtra("type");
        }
    }


    public void onStop() {
        super.onStop();
        if (currentImage != null) {
            currentImage.recycle();
            currentImage = null;
            System.gc();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}