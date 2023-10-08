package com.example.handcricket;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Toss extends AppCompatActivity {
    private int choice = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toss);
    }

    public void heads(View v1) {
        performToss(1, 0);
    }

    public void tails(View v2) {
        performToss(0, 1);
    }

    public void bat(View v3) {
        // User chooses to bat
        Toast.makeText(this, "You chose to bat", Toast.LENGTH_SHORT).show();
        choice = 1; // 0 represents "bat"

        // Start the MainActivity and pass the choice
        startMainActivity();
    }

    public void bowl(View v4) {
        // User chooses to bowl
        Toast.makeText(this, "You chose to bowl", Toast.LENGTH_SHORT).show();
        choice = 0; // 1 represents "bowl"

        // Start the MainActivity and pass the choice
        startMainActivity();
    }

    private void performToss(int yourChoice, int computerChoice) {
        disableButtons();
        Random random = new Random();
        final int tossResult = random.nextInt(2); // 0 for tails, 1 for heads
        LottieAnimationView toss = findViewById(R.id.toss);
        toss.bringToFront();
        toss.setVisibility(View.VISIBLE);
        toss.setAnimation(R.raw.toss);
        toss.playAnimation();
        toss.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                toss.setVisibility(View.GONE);
            }
        });
        Toast.makeText(this, (tossResult == 1) ? "Heads" : "Tails", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> {
            if (yourChoice == tossResult) {
                Toast.makeText(Toss.this, "You won the toss", Toast.LENGTH_SHORT).show();
                hideButtons();

                // Allow the user to choose bat or bowl
                findViewById(R.id.textView6).setVisibility(View.VISIBLE);
                findViewById(R.id.button10).setVisibility(View.VISIBLE);
                findViewById(R.id.button11).setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(Toss.this, "You lost the toss", Toast.LENGTH_SHORT).show();
                int computerDecision = random.nextInt(2); // 0 for bat, 1 for bowl
                Toast.makeText(Toss.this, (computerDecision == 0) ? "Computer chose to bat" : "Computer chose to bowl", Toast.LENGTH_SHORT).show();
                choice = computerDecision;
                new Handler().postDelayed(() -> chooseAction(computerDecision), 1000);
            }
        }, 3000); // 1 second delay
    }

    private void hideButtons() {
        findViewById(R.id.button8).setVisibility(View.INVISIBLE);
        findViewById(R.id.button7).setVisibility(View.INVISIBLE);
        findViewById(R.id.textView6).setVisibility(View.INVISIBLE);
        findViewById(R.id.button10).setVisibility(View.VISIBLE);
        findViewById(R.id.button11).setVisibility(View.VISIBLE);
    }

    private void chooseAction(int chosenAction) {
        Toast.makeText(this, (chosenAction == 0) ? "You chose to bowl" : "You chose to bat", Toast.LENGTH_SHORT).show();
        choice = chosenAction;

        new Handler().postDelayed(this::startMainActivity, 1000); // 1 second delay
    }

    private void startMainActivity() {
        Intent intent = new Intent(Toss.this, MainActivity.class);
        intent.putExtra("choice", choice); // Put the data into the intent
        startActivity(intent);
        finish();
    }
    public void disableButtons(){
        findViewById(R.id.button7).setEnabled(false);
        findViewById(R.id.button8).setEnabled(false);

    }
}