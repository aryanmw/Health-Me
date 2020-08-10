package com.wadhavekar.healthme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class history extends AppCompatActivity {
    DatabaseManager myDb = new DatabaseManager(this);
    ArrayList<Records> myList;
    ListView myListView;
    Records myrecords;
    String[] tp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        myList = new ArrayList<>();

        Cursor data = myDb.retrieveData();
        int numRows = data.getCount();
        if (numRows == 0){
            Toast.makeText(this, "No entries added yet", Toast.LENGTH_SHORT).show();
        }
        else{
            while (data.moveToNext()){
                myrecords = new Records(data.getString(1),data.getString(2));
                myList.add(myrecords);
                TwoCol_ListAdapter adapter = new TwoCol_ListAdapter(this,R.layout.list_adapter,myList);
                myListView = (ListView) findViewById(R.id.TheListView);
                myListView.setAdapter(adapter);
            }

        }
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_workout:
                    Intent intent = new Intent(history.this,MyWorkout.class);
                    startActivity(intent);
                    break;

                case R.id.nav_pedometer:
                    Intent intent2 = new Intent(history.this,ped.class);
                    startActivity(intent2);
                    break;

                case R.id.nav_user:
                    Intent intent1 = new Intent(history.this,Profile.class);
                    startActivity(intent1);
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.clear_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.clearData){
            myDb.deleteAllData();
            Intent intent = new Intent(history.this,Profile.class);
            startActivity(intent);
            Toast.makeText(this, "All your data was cleared", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
