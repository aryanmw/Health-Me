package com.wadhavekar.healthme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    private void setToggleEvent(GridLayout mainGrid) {
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == 1){
                        Intent intent = new Intent (MainActivity.this,Workout.class);
                        startActivity(intent);
                    }
                    else if(finalI == 0){
                        Intent intent0 = new Intent (MainActivity.this,Profile.class);
                        startActivity(intent0);
                    }
                }
            });
        }
    }

    private void setSingleEvent (GridLayout gridLayout){
        for (int i =0 ; i<gridLayout.getChildCount();i++){
            CardView cardview = (CardView)gridLayout.getChildAt(i);
            final int finalI = i;
            cardview.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    if (finalI == 1){
                        Intent intent1 = new Intent (MainActivity.this,Workout.class);
                        startActivity(intent1);
                    }
                    else if(finalI == 0){
                        Intent intent0 = new Intent (MainActivity.this,Profile.class);
                        startActivity(intent0);
                    }
                }
            });
        }
    }


}
