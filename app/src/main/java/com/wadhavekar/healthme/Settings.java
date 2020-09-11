package com.wadhavekar.healthme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wadhavekar.healthme.DialogBoxes.AgeDialogBox;
import com.wadhavekar.healthme.DialogBoxes.HeightDialogBox;
import com.wadhavekar.healthme.DialogBoxes.SetupDialogBoxListener;
import com.wadhavekar.healthme.DialogBoxes.StepTargetDialogBox;
import com.wadhavekar.healthme.DialogBoxes.TargetDialogBox;
import com.wadhavekar.healthme.Pedometer.ped;

public class Settings extends AppCompatActivity implements SetupDialogBoxListener {
    RelativeLayout height,target,age,steps;
    DatabaseManager databaseManager;
    TextView ht,tvStep,pedSteps,targetVar,ageVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        databaseManager = new DatabaseManager(this);

        targetVar = findViewById(R.id.actual_tv_enter_target);
        Cursor targetWeight = databaseManager.getTarget();
        targetWeight.moveToLast();
        targetVar.setText(targetWeight.getString(1));

        ageVar = findViewById(R.id.actual_tv_enter_age);
        Cursor ageData = databaseManager.getAge();
        if (ageData.moveToLast()){
            ageVar.setText(ageData.getString(1));
        }
        else{
            ageVar.setText("25");
        }

        height = findViewById(R.id.adjust_height);
        target = findViewById(R.id.adjust_target_weight);
        age = findViewById(R.id.adjust_age);
        steps = findViewById(R.id.adjust_target_steps);

        ht = findViewById(R.id.actual_tv_enter_height);

        tvStep = findViewById(R.id.actual_tv_enter_steps);
        pedSteps = findViewById(R.id.tv_target_steps_pedometer);

        Cursor htData = databaseManager.getMyHeight();
        htData.moveToLast();
        ht.setText(htData.getString(1)+"cm");

        Cursor data = databaseManager.getTargetSteps();
        if (data.moveToLast()){
            tvStep.setText(data.getString(1));
        }
        else{
            tvStep.setText("6000");
        }

        height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHeightDialog();
            }
        });
        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStepDialog();
            }
        });
        target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTargetDialog();
            }
        });
        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAgeDialog();
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_workout:
                    Intent intent = new Intent(Settings.this,MyWorkout.class);
                    startActivity(intent);
                    break;

                case R.id.nav_history:
                    Intent intent2 = new Intent(Settings.this,history.class);
                    startActivity(intent2);
                    break;

                case R.id.nav_user:
                    Intent intent1 = new Intent(Settings.this,Profile.class);
                    startActivity(intent1);
                    break;

                case R.id.nav_pedometer:
                    Intent intent3 = new Intent(Settings.this, ped.class);
                    startActivity(intent3);
                    break;
            }
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,Profile.class);
        startActivity(intent);
    }

    private void openHeightDialog(){
        HeightDialogBox dialogBox = new HeightDialogBox();
        dialogBox.show(getSupportFragmentManager(),"Height Entry");
    }
    private  void openStepDialog(){
        StepTargetDialogBox dialogBox = new StepTargetDialogBox();
        dialogBox.show(getSupportFragmentManager(),"Step Target");
    }

    private void openTargetDialog(){
        TargetDialogBox dialogBox = new TargetDialogBox();
        dialogBox.show(getSupportFragmentManager(),"Target");
    }

    private void openAgeDialog(){
        AgeDialogBox dialogBox = new AgeDialogBox();
        dialogBox.show(getSupportFragmentManager(),"Age");
    }


    @Override
    public void applyDialogBox(String height) {
        boolean insert = databaseManager.addHeight(height);
        if (insert == true){
            //Toast.makeText(this, "Successfully added", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
        ht.setTextColor(Color.parseColor("#FFFFFF"));
        ht.setText(height + "cm");
        //Cursor data = databaseManager.getLastEnteredWeight();
    }

    @Override
    public void applyStepDialogBox(String steps) {
        //pedSteps.setText(" / "+ steps);
        databaseManager.addTargetSteps(""+(Integer.parseInt(steps)*1000));
        tvStep.setText(""+(Integer.parseInt(steps)*1000));

    }

    @Override
    public void appTarText(String target) {
        boolean insert = databaseManager.addTarget(target);
        targetVar.setText(target + " kg");
        targetVar.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void applyAgeDialogBox(String age) {
        boolean insert = databaseManager.addAge(age);
        ageVar.setText(age);

    }
}
