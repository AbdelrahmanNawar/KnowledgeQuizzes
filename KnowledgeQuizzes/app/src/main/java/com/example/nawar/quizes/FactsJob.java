package com.example.nawar.quizes;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.nawar.quizes.R;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


public class FactsJob extends JobService {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;

    @Override
    public boolean onStartJob(JobParameters job) {
        FirebaseApp.initializeApp(getApplicationContext());
        mFirebaseDatabase= FirebaseDatabase.getInstance();


        mMessagesDatabaseReference = mFirebaseDatabase.getReference("Facts").child(String.valueOf(random()));

        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                String fact = dataSnapshot.getValue().toString();
                SharedPreferencesMethods.savePreferencesString(getApplicationContext(),getString(R.string.facts_key),fact);
                Intent intent = new Intent(getApplicationContext(), FactsAppWidget.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), FactsAppWidget.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                sendBroadcast(intent);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Nawar", "Failed to read value.", databaseError.toException());
            }
        });



        return false;
    }


    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
    private int random(){
        int min = 0;
        int max = 50;

        Random r = new Random();
        int randomeNum = r.nextInt(max - min + 1) + min;
        return randomeNum;
    }
}
