package com.wadhavekar.healthme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Pedometer extends AppCompatActivity {

    TextView pedometer;
    double previousMagnitude = 0;
    Integer stepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        pedometer = findViewById(R.id.tv_pedometer);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);




        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event != null) {
                    float x_acc = event.values[0];
                    float y_acc = event.values[1];
                    float z_acc = event.values[2];

                    double currentMagnitude = Math.sqrt(x_acc * x_acc * y_acc * y_acc * z_acc * z_acc);
                    double delta = currentMagnitude - previousMagnitude;
                    previousMagnitude = currentMagnitude;

                    if (delta >= 15) {
                        stepCount++;
                        pedometer.setText(stepCount.toString());
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }

    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }
    protected void onResume(){
        super.onResume();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount",0);
    }

}

//
