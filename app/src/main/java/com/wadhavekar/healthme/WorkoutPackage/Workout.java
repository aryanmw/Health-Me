package com.wadhavekar.healthme.WorkoutPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wadhavekar.healthme.MyWorkout;
import com.wadhavekar.healthme.Profile;
import com.wadhavekar.healthme.R;
import com.wadhavekar.healthme.history;
import com.wadhavekar.healthme.Pedometer.ped;

public class Workout extends AppCompatActivity {
    GridLayout gridLayout;
    TextView tvMyWorkout;
    CardView legs,chest,upperbody,abs,cardio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        legs = findViewById(R.id.cv_legs);
        upperbody = findViewById(R.id.cv_upperbody);
        chest = findViewById(R.id.cv_chest);
        abs = findViewById(R.id.cv_abs);
        cardio = findViewById(R.id.cv_cardio);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);






        tvMyWorkout = findViewById(R.id.my_workout);

        tvMyWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Workout.this, MyWorkout.class);
                startActivity(intent);
            }
        });

        legs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Workout.this, Legs.class);
                startActivity(intent);
            }
        });
        upperbody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Workout.this, UpperBody.class);
                startActivity(intent);
            }
        });

        chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Workout.this, Chest.class);
                startActivity(intent);
            }
        });

        abs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Workout.this, Abs.class);
                startActivity(intent);
            }
        });

        cardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Workout.this, Cardio.class);
                startActivity(intent);
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_user:
                    Intent intent = new Intent(Workout.this, Profile.class);
                    startActivity(intent);
                    break;

                case R.id.nav_pedometer:
                    Intent intent2 = new Intent(Workout.this, ped.class);
                    startActivity(intent2);
                    break;

                case R.id.nav_history:
                    Intent intent1 = new Intent(Workout.this, history.class);
                    startActivity(intent1);
                    break;
            }
            return true;
        }
    };
}
