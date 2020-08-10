package com.wadhavekar.healthme;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Profile extends AppCompatActivity implements DialogBoxListener, NavigationView.OnNavigationItemSelectedListener {

    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    EditText weightEdit,ht_dialog;
    Button add;
    DatabaseManager databaseManager = new DatabaseManager(this);
    DialogBox dialogBox;
    TextView currentWeight,bmi,targetVar,changeVar,userinits;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy");

    LineGraphSeries<DataPoint> series;

    DrawerLayout drawerLayout;
    NavigationView navView;
    ImageView menu;
    androidx.appcompat.widget.Toolbar toolbar;



    ArrayList<String> theList = new ArrayList<>();
    Records wtRecords;
    private final String SHARED_PREF = "HealthAndMe";
    private final String USER_INIT = "User Initials";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        Cursor dataGraph = databaseManager.getLastEnteredWeight();
        DataPoint[] dataPoint = new DataPoint[dataGraph.getCount()];

        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.ham_view);
        toolbar = findViewById(R.id.toolbar);
        navView.bringToFront();

        userinits = findViewById(R.id.tv_userinits);
        SharedPreferences sp = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        String user = sp.getString(USER_INIT,"U");
        userinits.setText(user);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        for (int i = 0;i<dataGraph.getCount();i++){
            dataGraph.moveToNext();
            Double yVal = Double.parseDouble(dataGraph.getString(1));
            dataPoint[i] = new DataPoint(i,yVal);




        }
        series = new LineGraphSeries<>(dataPoint);
        series.setDrawDataPoints(true);

        graph.addSeries(series);

        if (dataGraph.getCount()>=13) {
            graph.getGridLabelRenderer().setNumHorizontalLabels(12);
            graph.getGridLabelRenderer().setNumVerticalLabels(5);// only 4 because of the space
        } else {
            graph.getGridLabelRenderer().setNumHorizontalLabels(dataGraph.getCount());
            graph.getGridLabelRenderer().setNumVerticalLabels(dataGraph.getCount());
        }


        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setScrollable(true);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);


        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf,nf));
        //graph.getViewport().setScrollable(true); // enables horizontal scrolling
        //graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true);

        graph.getGridLabelRenderer().setHumanRounding(false);

        //==============================================================================


        //ht = findViewById(R.id.et_height);
        bmi = findViewById(R.id.tv_bmi);
        //ht_dialog = findViewById(R.id.et_height_dialog);
        //weightEdit = (EditText) findViewById(R.id.edittext1);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        currentWeight = findViewById(R.id.tv_current);
        //comm = findViewById(R.id.tv_comment);

        targetVar = findViewById(R.id.tv_target);
        changeVar = findViewById(R.id.tv_change);

        Cursor data = databaseManager.getLastEnteredWeight();
        if (data.moveToLast()) {
            currentWeight.setText(data.getString(1) + " kg");
        }

        if (data.moveToLast() && data.moveToFirst()){
            DecimalFormat df = new DecimalFormat("0.0");
            data.moveToFirst();
            Double first = Double.parseDouble(data.getString(1));
            data.moveToLast();
            Double last = Double.parseDouble(data.getString(1));
            Double total = last - first;

            if (total<0){
                changeVar.setTextColor(Color.parseColor("#000000"));
                changeVar.setText(""+df.format(total) + " kg");
            }
            else if (total>0){
                changeVar.setTextColor(Color.parseColor("#000000"));
                changeVar.setText("+"+df.format(total) + " kg");
            }
            else{
                changeVar.setTextColor(Color.parseColor("#000000"));
                changeVar.setText(""+df.format(total) + " kg");
            }

        }

        Cursor dataHt = databaseManager.getMyHeight();

        Cursor dataTarget = databaseManager.getTarget();
        if (dataTarget.moveToLast()){
            targetVar.setText(dataTarget.getString(1) + " kg");
            targetVar.setTextColor(Color.parseColor("#000000"));
        }

        if(data.moveToLast() && dataHt.moveToLast()){

          bmiSetter( data.getString(1),dataHt.getString(1));
        }
        //------------------------------------------------------------------------------------------------------------------------------------



        //-------------------------------------------------------------------------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });

        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_workout:
                    Intent intent = new Intent(Profile.this,MyWorkout.class);
                    startActivity(intent);
                    break;

                case R.id.nav_pedometer:
                    Intent intent2 = new Intent(Profile.this,ped.class);
                    startActivity(intent2);
                    break;

                case R.id.nav_history:
                    Intent intent1 = new Intent(Profile.this,history.class);
                    startActivity(intent1);
                    break;
            }
            return true;
        }
    };


    private void openDialog(){
        DialogBox dialogBox = new DialogBox();
        dialogBox.show(getSupportFragmentManager(),"Weight Entry");
    }

    private void openHeightDialog(){
        HeightDialogBox dialogBox = new HeightDialogBox();
        dialogBox.show(getSupportFragmentManager(),"Height Entry");
    }

//    private void openTargetDialog(){
//        TargetDialogBox dialogBox = new TargetDialogBox();
//        dialogBox.show(getSupportFragmentManager(),"Target Entry");
//    }


    public String getDate(){
        Calendar calendar = Calendar.getInstance();
        String date = DateFormat.getDateInstance().format(calendar.getTime());
        return date;
    }


    @Override
    public void applyText(String wt) {
        boolean insert = databaseManager.addData(wt);
        if (insert == true){
            Toast.makeText(this, "Successfully added", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
        currentWeight.setText(wt + " kg");
        Cursor dataHeight = databaseManager.getMyHeight();
        if (dataHeight.moveToLast()){
            bmiSetter(wt,dataHeight.getString(1));
        }
        Cursor data = databaseManager.getLastEnteredWeight();

        DecimalFormat df = new DecimalFormat("0.0");
        data.moveToFirst();
        Double first = Double.parseDouble(data.getString(1));
        data.moveToLast();
        Double last = Double.parseDouble(data.getString(1));
        Double total = last - first;
        if (total<0){
            changeVar.setTextColor(Color.parseColor("#008000"));
            changeVar.setText(""+df.format(total) + " kg");
        }
        else if (total>0){
            changeVar.setTextColor(Color.parseColor("#FF0000"));
            changeVar.setText("+"+df.format(total) + " kg");
        }
        else{
            changeVar.setTextColor(Color.parseColor("#FFFFFF"));
            changeVar.setText(""+df.format(total) + " kg");
        }
        Intent intento = new Intent(Profile.this,Profile.class);
        startActivity(intento);


//
    }



    @Override
    public void appTarText(String target) {
        boolean insert = databaseManager.addTarget(target);
        targetVar.setText(target + " kg");
        targetVar.setTextColor(Color.parseColor("#FFFFFF"));

    }

    public void bmiSetter(String weight, String height){
        double bwt = Double.parseDouble(weight);
        double bht = Double.parseDouble(height);
        double bhtSqrd = bht * bht;
        double bmiTotal = 10000*bwt/bhtSqrd;
        DecimalFormat df = new DecimalFormat("0.0");

        bmi.setText(""+df.format(bmiTotal));
        if(bmiTotal > 25){
            bmi.setTextColor(Color.parseColor("#FF0000"));
        }
        else if (bmiTotal>=20 && bmiTotal<=25){
            bmi.setTextColor(Color.parseColor("#008000"));
        }
        else{
            bmi.setTextColor(Color.parseColor("#F0D808"));
        }
    }

    private void getData(){
//        Cursor data = databaseManager.retrieveData();
//        DataPoint[] dataPt = new DataPoint[data.getCount()];
//        double xVal,yVal;
//        for(int i = 0 ; i<data.getCount() ; i++){
//            data.moveToNext();
//            yVal = Double.parseDouble(data.getString(1));
//            xVal = Double.parseDouble(data.getString(2));
//            dataPt[i] = new DataPoint(xVal,yVal);
//        }
//        return dataPt;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ham_settings:
                Intent intent = new Intent(Profile.this,Settings.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
