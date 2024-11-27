package com.b07.planetze;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

import com.b07.planetze.ecotracker.EcoTrackerActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseDatabase.getInstance("https://planetze-3cc9d-default-rtdb.firebaseio.com/");
        View container = findViewById(R.id.fragment_container);
        if (container == null) {
            throw new IllegalStateException("Fragment container not found in the layout!");
        } else {
            Log.d("DEBUG", "Fragment container found.");
        }
        //DatabaseReference myRef = db.getReference("testDemo");

        //myRef.setValue("B07 Demo!");
        //User bruh = new User("Caleb", "passwordthatneedstobeencrypted", "bruh.com");
        //myRef.child("User").setValue(bruh);

//        EcoTrackerActivity.start(this);

        if (savedInstanceState == null) {
            loadFragment(new WelcomeFragment());
        }


    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}