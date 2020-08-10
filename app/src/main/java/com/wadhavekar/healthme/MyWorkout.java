package com.wadhavekar.healthme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyWorkout extends AppCompatActivity {
    CardView mon,tue,wed,thu,fri,sat;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_workout);

        mon = findViewById(R.id.cv_mon);
        tue = findViewById(R.id.cv_tue);
        wed = findViewById(R.id.cv_wed);
        thu = findViewById(R.id.cv_thu);
        fri = findViewById(R.id.cv_fri);
        sat = findViewById(R.id.cv_sat);

        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWorkout.this,DayMondayWorkout.class);
                startActivity(intent);
            }
        });

        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWorkout.this,DayTuesdayWorkout.class);
                startActivity(intent);
            }
        });

        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWorkout.this,DayWednesdayWorkout.class);
                startActivity(intent);
            }
        });

        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWorkout.this,DayThursdayWorkout.class);
                startActivity(intent);
            }
        });

        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWorkout.this,DayFridayWorkout.class);
                startActivity(intent);
            }
        });
        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWorkout.this,DaySaturdayWorkout.class);
                startActivity(intent);
            }
        });



        tv = findViewById(R.id.list);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenti = new Intent(MyWorkout.this,Workout.class);
                startActivity(intenti);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);


    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_user:
                    Intent intent = new Intent(MyWorkout.this,Profile.class);
                    startActivity(intent);
                    break;

                case R.id.nav_pedometer:
                    Intent intent2 = new Intent(MyWorkout.this,ped.class);
                    startActivity(intent2);
                    break;

                case R.id.nav_history:
                    Intent intent1 = new Intent(MyWorkout.this,history.class);
                    startActivity(intent1);
            }
            return true;
        }
    };


}
