package com.wadhavekar.healthme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;
import static com.wadhavekar.healthme.ped.STEP_COUNT;

public class MyService extends BroadcastReceiver {
    ped p = new ped();
    TextView pedometer;
    DatabaseManager databaseManager;
    private MyServiceListener listener;
    public static final String SHARED_PREFS = "HealthyMePref";




    @Override
    public void onReceive(Context context, Intent intent) {
       // p.getStepsFromPed();xx
        databaseManager = new DatabaseManager(context);
        Calendar cal = Calendar.getInstance();
        SharedPreferences result = context.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        int t = result.getInt(STEP_COUNT,0);

            boolean insert = databaseManager.addStepsToDB(t);
//            Intent intent1 = new Intent(context,Profile.class);
//            context.startActivity(intent1);
            Log.i("MyServiceClass","^^^^^^^^^"+t);




        Log.i("MyService Class","&&&&&&&&&&&&& "+t);
        //Toast.makeText(context, ""+t, Toast.LENGTH_SHORT).show();

           // Log.d("Alert",""+insert);


    }
}
