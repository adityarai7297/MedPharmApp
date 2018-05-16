package com.example.android.medpharm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TabFragment extends Fragment {

    int position;
    private TextView textView;
    AutoCompleteTextView autoCompleteTextView;
    WebView webView;
    ImageButton searchButton;
    Button but1;
    View v;


    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(position==0)
        return inflater.inflate(R.layout.fragment_tab, container, false);
        if(position==1) {
            v= inflater.inflate(R.layout.activity_maps, container, false);
            but1=(Button)v.findViewById(R.id.btnshops);
            but1.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v)
                {
                    Intent mapIntent = new Intent(getContext(),MapActivity2.class);
                    startActivity(mapIntent);
                }
            });
            return v;
        }
        if(position==2)
            return inflater.inflate(R.layout.fragment_tab3, container, false);

        else return inflater.inflate(R.layout.fragment_tab3, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if(position==0){

            searchButton = (ImageButton) view.findViewById(R.id.searchButton);
            autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);

            //Nothing special, create database reference.
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            //Create a new ArrayAdapter with your context and the simple layout for the dropdown menu provided by Android
            final ArrayAdapter<String> autoComplete = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1);
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


            webView = (WebView) view.findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.loadUrl("https://www.healthline.com/");


            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String medName = autoCompleteTextView.getText().toString();
                    Intent searchIntent = new Intent(getActivity(),Search.class);
                    searchIntent.putExtra("med",medName);
                    startActivity(searchIntent);
                }
            });
        }

        if(position==1){

//            Intent mapIntent = new Intent(getContext(),MapsActivity.class);
//            startActivity(mapIntent);


        }
        if(position==2){

//            Intent mapIntent = new Intent(getContext(),MapActivity2.class);
//            startActivity(mapIntent);

        }

    }


}