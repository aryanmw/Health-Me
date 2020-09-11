package com.wadhavekar.healthme.Days;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wadhavekar.healthme.DatabaseManager;
import com.wadhavekar.healthme.MyWorkout;
import com.wadhavekar.healthme.DialogBoxes.PersonalisedDialogBox;
import com.wadhavekar.healthme.DialogBoxes.PersonalisedWorkoutDialogBoxListener;
import com.wadhavekar.healthme.R;
import com.wadhavekar.healthme.WorkoutPackage.WorkoutCheckedList;

import java.util.ArrayList;

public class DayTuesdayWorkout extends AppCompatActivity implements PersonalisedWorkoutDialogBoxListener {
    DatabaseManager myDb = new DatabaseManager(this);
    ArrayList<WorkoutCheckedList> myList;
    ListView myListView1;
    WorkoutCheckedList wcl;
    String workoutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_tuesday_workout);
        myListView1 = (ListView) findViewById(R.id.MonListView);

        myList = new ArrayList<>();

        Cursor data = myDb.getTuesdayData();
        int numRows = data.getCount();
        if (numRows == 0){
            //Toast.makeText(this, "The database is empty", Toast.LENGTH_SHORT).show();
        }
        else{
            while (data.moveToNext()){
                wcl = new WorkoutCheckedList(data.getString(1),data.getString(2),data.getString(3));
                myList.add(wcl);
                DayAdapter adapter = new DayAdapter(this,R.layout.personalised_workout_list_adapter,myList);
                myListView1.setAdapter(adapter);
            }

        }
        myListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv =  myListView1.getChildAt(position).findViewById(R.id.tv_name);
                workoutName = tv.getText().toString();
                openPDialogBox();
            }
        });
    }
    private void openPDialogBox(){
        PersonalisedDialogBox dialogBox = new PersonalisedDialogBox();
        dialogBox.show(getSupportFragmentManager(),"Remove Exercise");
    }

    @Override
    public void doWork() {
        myDb.removeRecord(workoutName,"Tuesday");
        Cursor wData = myDb.getTuesdayData();
        myList = new ArrayList<>();
        if(wData.getCount() == 0){
            startActivity(new Intent(this, MyWorkout.class));
        }

        while (wData.moveToNext()) {
            wcl = new WorkoutCheckedList(wData.getString(1), wData.getString(2), wData.getString(3));
            myList.add(wcl);
            DayAdapter adapter = new DayAdapter(this, R.layout.personalised_workout_list_adapter, myList);
            myListView1 = (ListView) findViewById(R.id.MonListView);
            myListView1.setAdapter(adapter);
        }
    }
}
