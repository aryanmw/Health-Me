package com.wadhavekar.healthme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ped extends AppCompatActivity implements SensorEventListener,MyServiceListener,StepListener {
    TextView pedometer,date,target,distance,calories;
    SensorManager sensorManager;
    private StepDetector simpleStepDetector;
    ProgressBar prg;
    int numSteps = 0;


    boolean isRunning = true;
    int steps = 0;
    int textViewTargetValue=6000;
    double footLength = 0;
    DatabaseManager databaseManager;
    //int currentStepCount = 0;
    SensorEvent event;
    public static final String SHARED_PREFS = "HealthyMePref";
    public static final String STEP_COUNT = "STEP_COUNT";

    DecimalFormat df = new DecimalFormat("0.00");

    SharedPreferences sharedPreferences;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);
        distance = findViewById(R.id.tv_distance);
        databaseManager =  new DatabaseManager(this);
        calories = findViewById(R.id.tv_calories);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);
        isRunning = true;

        Cursor data = databaseManager.getMyHeight();
        data.moveToLast();
        footLength =(Double.parseDouble(data.getString(1)) /660)+ 0.2;





        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 2);
        }

//
//
//
//
//
//
//
//
//




        target = findViewById(R.id.tv_target_steps_pedometer);


        pedometer = findViewById(R.id.tv_pedometer);



        prg = findViewById(R.id.progressBar);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (countSensor != null){
            sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
            Log.i("ped","%%%%%%%%"+countSensor);
        }
        else{
            sensorManager.registerListener(this,accel,SensorManager.SENSOR_DELAY_FASTEST);
            Log.i("PED","()()() register listener accelerometer" + accel);
        }





        // currentStepCount = Integer.parseInt(pedometer.getText().toString());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
         bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        //Toast.makeText(this, ""+pedometer.getText(), Toast.LENGTH_SHORT).show();

        Cursor dataTarget = databaseManager.getTargetSteps();
        if(dataTarget.moveToLast()){
            textViewTargetValue = Integer.parseInt(dataTarget.getString(1));
            target.setText(""+ textViewTargetValue +" steps");




            //df.setMaximumFractionDigits(2);

        }


        //int i = getCurrentStepCount();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_user:
                    Intent intent = new Intent(ped.this,Profile.class);
                    startActivity(intent);
                    break;

                case R.id.nav_workout:
                    Intent intent2 = new Intent(ped.this,MyWorkout.class);
                    startActivity(intent2);
                    break;

                case R.id.nav_history:
                    Intent intent1 = new Intent(ped.this,history.class);
                    startActivity(intent1);
            }
            return true;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY,12);
//        cal.set(Calendar.MINUTE,35);
//        cal.set(Calendar.SECOND,00);
//        cal.set(Calendar.MILLISECOND,0);
//
//        setAlarm(cal.getTimeInMillis());
//        Date now = new Date();
//        cal.setTime(now);
//
//        if (cal.get(Calendar.HOUR_OF_DAY) == 23 && cal.get(Calendar.MINUTE) == 59 && cal.get(Calendar.SECOND) == 59) {
//            int steps = Integer.parseInt(pedometer.getText().toString());
//            databaseManager.addStepsToDB(steps);
//
//        }
        //Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        numSteps = sp.getInt("stepCount",0);
        pedometer.setText(""+numSteps);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null){
            sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = true;
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,30);
        cal.set(Calendar.MILLISECOND,0);
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("stepCount",Integer.parseInt(pedometer.getText().toString()));
        edit.apply();


            saveDataToSharedPreference();

        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.format(d1);

        Cursor dateData = databaseManager.getSteps();
        if (dateData.getCount() == 0){
                setAlarm(cal.getTimeInMillis());
        }
        else{
            dateData.moveToLast();
            if (dateData.getString(0).equals(""+d1)){
                //Toast.makeText(this, "Nopeee", Toast.LENGTH_SHORT).show();
            }
            else{
                setAlarm(cal.getTimeInMillis());
                numSteps=0;
            }
        }

        




        Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accel != null){
            sensorManager.registerListener(this,accel,SensorManager.SENSOR_DELAY_FASTEST);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
       if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

           

       }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Cursor data = databaseManager.getSteps();

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
        else{
            if (isRunning){

                if (data.getCount() == 0) {
                    steps = Math.round(event.values[0]);
                    //saveDataToSharedPreference();
                    pedometer.setText(""+(steps));
                    int progress = Math.round(100*steps/textViewTargetValue);
                    prg.setProgress(progress);
                    double displacement = steps*footLength/1000;
                    double c = steps*0.05;
                    //Toast.makeText(this, "kkk  "+steps, Toast.LENGTH_SHORT).show();
                    distance.setText(""+df.format(displacement) + " km");
                    calories.setText(""+df.format(c) + " cals");
                }
                else {
                    int sumOfSteps = 0;
                    while(data.moveToNext()){
                        sumOfSteps+=Integer.parseInt(data.getString(1));
                    }
                    data.moveToLast();

                    //int lastEntry = Integer.parseInt(data.getString(1));

                    steps = Math.round(event.values[0] - sumOfSteps);
                    pedometer.setText("" + steps);
                    int progress = Math.round(100 * steps / textViewTargetValue);
                    prg.setProgress(progress);
                    double displacement = steps*footLength/1000;
                    distance.setText(""+df.format(displacement) + " km");
                    double c = steps*0.05;
                    calories.setText(""+df.format(c) + " cals");
                   // Toast.makeText(this, ""+data.getString(1) + data.getCount(), Toast.LENGTH_SHORT).show();

                    //
                }

            }

        }





    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void midnightResetCounter() {
        int stepsT = Integer.parseInt(pedometer.getText().toString());
        databaseManager.addStepsToDB(stepsT);
        pedometer.setText("0");
        Toast.makeText(this, "Alarm took place", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm(long milli){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,MyService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,milli,24*60*60*1000,pendingIntent);
        //Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }
//    public int getStepsFromPed(){
//        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
//        if (countSensor != null){
//            sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
//        }
//        //return Integer.parseInt(pedometer.getText().toString());
//        return 8;
//    }

//    public float getCurrentStepCount(){
//        Log.d("StepCount"," inside getCurrentStepCount() "+currentStepCount);
//        return currentStepCount;
//
//    }
    public void saveDataToSharedPreference(){
        sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(STEP_COUNT,Integer.parseInt(pedometer.getText().toString()));
        editor.apply();
        Log.i("SP","??????????"+sharedPreferences);
    }
    public int loadDataFromSharedPreference(){
        Log.i("SP","before call {{{{{{{");
        sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        Log.i("SP","==========="+sharedPreferences);
        return sharedPreferences.getInt(STEP_COUNT,0);
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        pedometer.setText(""+numSteps);
        int progress = Math.round(100 * numSteps / textViewTargetValue);
        prg.setProgress(progress);
        double displacement = numSteps*footLength/1000;
        distance.setText(""+df.format(displacement) + " km");
        double c = steps*0.05;
        calories.setText(""+df.format(c) + " cals");
        //Toast.makeText(this, "Hi", Toast.LENGTH_SHORT).show();

    }

}
