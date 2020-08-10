package com.wadhavekar.healthme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class InitialSetup extends AppCompatActivity  {
    DatabaseManager databaseManager = new DatabaseManager(this);
    Profile prof = new Profile();
    TextView done,cm;
    NumberPicker ht,np_kg,np_age,np_grams;
    TextView diff ;
    private Double target;
    EditText et_fullname;
    int x;
    DecimalFormat df = new DecimalFormat("0.0");
    RadioButton male,female,dec,inc;
    String initialsOfUser;
    private final String SHARED_PREF = "HealthAndMe";
    private final String USER_INIT = "User Initials";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_setup);
        ht = findViewById(R.id.tv_ini_set_height);
        np_age = findViewById(R.id.tv_ini_set_age);
        np_kg = findViewById(R.id.tv_ini_set_kg);
        np_grams = findViewById(R.id.tv_ini_set_grams);
        cm = findViewById(R.id.tv_cm);
        male = findViewById(R.id.rb_male);
        female = findViewById(R.id.rb_female);
        inc = findViewById(R.id.rb_inc);
        dec = findViewById(R.id.rb_dec);
        et_fullname = findViewById(R.id.et_fullname);

        diff = findViewById(R.id.diff);
        ht.setMaxValue(300);
        ht.setMinValue(30);
        ht.setValue(160);

        np_kg.setMaxValue(250);
        np_kg.setMinValue(15);
        np_kg.setValue(70);

        np_age.setMaxValue(100);
        np_age.setMinValue(1);
        np_age.setValue(25);

        np_grams.setMaxValue(9);
        np_grams.setMinValue(0);
        np_grams.setValue(0);



        //ht.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openDialogBox();
//            }
//        });

        done = findViewById(R.id.tv_done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((!male.isChecked() && !female.isChecked()) || (!inc.isChecked() && !dec.isChecked()) || et_fullname.getText().toString() == ""){
                    Toast.makeText(InitialSetup.this, "Please enter all information", Toast.LENGTH_SHORT).show();
                }
                else {
                    initialsOfUser = et_fullname.getText().toString().substring(0,1);
                        for (int i = 0 ; i < et_fullname.getText().toString().length() ; i++){

                            if (Character.isWhitespace(et_fullname.getText().toString().charAt(i))){
                                initialsOfUser = initialsOfUser + et_fullname.getText().toString().charAt(i+1);
                                initialsOfUser.toUpperCase();
                                saveDataToSharedPreference();
                                break;
                            }

                        }
                        databaseManager.addHeight(""+ht.getValue());
                        databaseManager.addAge(""+np_age.getValue());
                        databaseManager.addData(""+np_kg.getValue()+"."+"" +np_grams.getValue());
                        Intent i = new Intent(InitialSetup.this,Profile.class);
                        startActivity(i);
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                            .putBoolean("isFirstRun", false).apply();
                    }




            }
        });

//        ht.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                databaseManager.addHeight(""+newVal);
//            }
//        });

    }

    private void openDialogBox(){
        HeightDialogBox heightDialogBox = new HeightDialogBox();
        heightDialogBox.show(getSupportFragmentManager(),"Height Entry");
    }

//    public void applyDialogBox(String height) {
//        boolean insert = databaseManager.addHeight(height);
//        if (insert == true){
//            //Toast.makeText(this, "Successfully added", Toast.LENGTH_LONG).show();
//        }
//        else{
//            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
//        }
//        Toast.makeText(this, "hhh"+ height, Toast.LENGTH_SHORT).show();
//        Cursor htt = databaseManager.getMyHeight();
//        htt.moveToLast();
//        ht.setTextColor(Color.parseColor("#FFFFFF"));
//        ht.setText(htt.getString(1));
//        cm.setText("cm");
//        Cursor data = databaseManager.getLastEnteredWeight();
//
//    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();


        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb_male:
                if (checked) {

                        target = ht.getValue() * ht.getValue() * 22.5 / 10000;
                        databaseManager.addTarget(df.format(target));

                }
                    break;
            case R.id.rb_female:
                if (checked){

                        target = ht.getValue() * ht.getValue() * 21.0 / 10000;
                        databaseManager.addTarget(df.format(target));

                }
                    break;

        }
    }

    public void onRadioButtonGoalClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.rb_dec:
                if (checked){
                    x = 1;

                }
                break;
            case R.id.rb_inc:
                if (checked){
                    x = 2;
                }
                break;
        }
    }
    public int getX(){
        return x;
    }

    public void saveDataToSharedPreference(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_INIT,initialsOfUser);
        editor.apply();
        Log.i("SP","??????????"+sharedPreferences);
    }


}
