package com.example.handcricket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class Loading_Screen extends AppCompatActivity {
    ProgressBar pb;
    int c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        pb();
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Loading_Screen.this, Toss.class);
            startActivity(intent);
            finish(); // Close the Loading_Screen activity
        }, 5000); // Adjust the delay time as needed (e.g., 5000 milliseconds or 5 seconds)
    }


    public void pb(){
        pb=(ProgressBar)(findViewById(R.id.progressBar));
        final Timer t=new Timer();
        TimerTask tt=new TimerTask() {
            @Override
            public void run() {
                c++;
                pb.setProgress(c);
                if(c==100)
                {
                    t.cancel();

                }

            }
        };
        t.schedule(tt,0,50);

    }

}