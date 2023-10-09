package com.example.handcricket;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ImageButton button;
    private int innings=1;
    int Total=0,Total1=0,Target=0;
    Dialog dialog1;
    Dialog dialog2;
    Intent i1=getIntent();
    VideoView video2;


    private int choice = 1;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog1=new Dialog(MainActivity.this);
        dialog2=new Dialog(MainActivity.this);

        // Retrieve the choice data from the Intent
        Intent i1 = getIntent();
        if (i1 != null) {
            choice = i1.getIntExtra("choice", 1);
        }

    }
    public void button1(View view1) throws InterruptedException {
        Hand(1);

        ;
    }
    public void button2(View view2) throws InterruptedException {
        Hand(2);

    }
    public void button3(View view3) throws InterruptedException {
        Hand(3);

    }
    public void button4(View view4) throws InterruptedException {
        Hand(4);

    }
    public void button5(View view5) throws InterruptedException {
        Hand(5);

    }
    public void button6(View view6) throws InterruptedException {
        Hand(6);

    }



    @SuppressLint("SetTextI18n")
    public void Hand(int a) throws InterruptedException {
        disableButtons();
        Random random = new Random();
        int b = (random.nextInt(6)) + 1;
        playAnimationplayer(a);
        playAnimationComputer(b);
        if (innings == 1) {
            if (a == b) {
                videoout();

                Toast.makeText(this, choice == 1 ? "YOU ARE OUT!!" : "COMPUTER IS OUT!!", Toast.LENGTH_SHORT).show();

                Target = (choice == 1 ? Total : Total1) + 1;
                innings = 2;
                Toast.makeText(this, choice == 1 ? "NOW ITS COMPUTERS TURN" : "NOW IT'S YOUR TURN", Toast.LENGTH_SHORT).show();
                findViewById(R.id.textView5).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.textView5)).setText("Target-" + Target);
                ((TextView) findViewById(R.id.textView4)).setText("Total Score-0");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            enableButtons();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, 3000); // 3000 milliseconds (3 seconds)

            } else {
                if (choice == 1) {
                    Total += a;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                enableButtons();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, 3000); // 3000 milliseconds (3 seconds)

                } else {
                    Total1 += b;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                enableButtons();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, 3000); // 3000 milliseconds (3 seconds)

                }
                ((TextView) findViewById(R.id.textView4)).setText("Total Score-" + (choice == 1 ? Total : Total1));
            }
        } else if (innings == 2) {
            if (a == b) {
                videoout();
                Toast.makeText(this, choice == 1 ? "COMPUTER IS OUT!!" : "YOU ARE OUT!!", Toast.LENGTH_SHORT).show();
                if ((choice == 1 ? Total1 : Total) >= Target) {
                    findViewById(R.id.textView9).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.textView9)).setText(choice == 1 ? "YOU LOSE THE GAME" : "YOU WIN THE GAME");
                    disableButtons();
                } else {
                    findViewById(R.id.textView9).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.textView9)).setText(choice == 1 ? "YOU WIN THE GAME" : "YOU LOSE THE GAME");
                    disableButtons();
                }
            } else {
                if (choice == 1) {
                    Total1 += b;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                enableButtons();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, 3000); // 3000 milliseconds (3 seconds)

                } else {
                    Total += a;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                enableButtons();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, 3000); // 3000 milliseconds (3 seconds)

                }
                ((TextView) findViewById(R.id.textView4)).setText("Total Score-" + (choice == 1 ? Total1 : Total));
                if ((choice == 1 ? Total1 : Total) >= Target) {
                    findViewById(R.id.textView9).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.textView9)).setText(choice == 1 ? "YOU LOSE THE GAME" : "YOU WIN THE GAME");
                    disableButtons();
                }
            }
        }
    }
    public void Reset(View adk) throws InterruptedException {
        Target=0;
        Total=0;
        Total1=0;
        ((TextView)findViewById(R.id.textView9)).setVisibility(View.INVISIBLE);
        innings=1;
        ((TextView)findViewById(R.id.textView5)).setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.textView4)).setText("Total Score-");

        enableButtons();
    }
   public void pause(View v2){
       if(dialog1!=null){
        dialog1.setContentView(R.layout.customlayout);
        Objects.requireNonNull(dialog1.getWindow()).getAttributes().windowAnimations=R.style.animation;
        dialog1.setCancelable(false);
        dialog1.show();
   }}
    public void resume(View v1){
        if(dialog1!=null&& dialog1.isShowing()){
        dialog1.dismiss();
        Toast.makeText(this,"resumed",Toast.LENGTH_SHORT);
    }}
    public void act2(View v3){
        if(dialog2!=null){
            dialog2.setContentView(R.layout.activity_2);
            Objects.requireNonNull(dialog2.getWindow()).getAttributes().windowAnimations=R.style.animation;
            dialog2.setCancelable(false);
            dialog2.show();
        }}
    public void exit1(View v4){
        if(dialog2!=null&& dialog2.isShowing()){
            dialog2.dismiss();

        }}


        public void showCredits(View view) {
            if (dialog2 != null) {

                dialog1.dismiss();

                showCreditsDialog();
            }
        }

        private void showCreditsDialog() {
            if (dialog2 != null) {
                dialog2.setContentView(R.layout.credits_layout);
                Objects.requireNonNull(dialog2.getWindow()).getAttributes().windowAnimations = R.style.animation;
                dialog2.setCancelable(true);
                dialog2.show();
            }
        }

    public void startNewGame(View view) {
        Intent intent = new Intent(this, Toss.class);
        startActivity(intent);
        finish();
    }
    public void videoout() {
        VideoView out1 = (VideoView) findViewById(R.id.videoView);
        out1.bringToFront();
        out1.setVisibility(View.VISIBLE);
        String path = "android.resource://com.example.handcricket/" + R.raw.out;
        Uri u = Uri.parse(path);
        out1.setVideoURI(u);
        out1.start();
        out1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                out1.setVisibility(View.GONE);
            }
        });
    }
    public void playAnimationplayer(int a) throws InterruptedException {
        LottieAnimationView player = findViewById(R.id.player);
        player.bringToFront();
        player.setVisibility(View.VISIBLE);
        switch (a) {
            case 1:
                player.setAnimation(R.raw.one);
                break;
            case 2:
                player.setAnimation(R.raw.two);
                break;
            case 3:
                player.setAnimation(R.raw.three);
                break;
            case 4:
                player.setAnimation(R.raw.four);
                break;
            case 5:
                player.setAnimation(R.raw.five);
                break;
            case 6:
                player.setAnimation(R.raw.six);
                break;
        }
        player.playAnimation();
        player.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                player.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void playAnimationComputer(int b)throws InterruptedException{

        LottieAnimationView computer = findViewById(R.id.computer);
        computer.bringToFront();
        computer.setVisibility(View.VISIBLE);
        switch(b){
            case 1:
                computer.setAnimation(R.raw.one);
                break;
            case 2:
                computer.setAnimation(R.raw.two);
                break;
            case 3:
                computer.setAnimation(R.raw.three);
                break;
            case 4:
                computer.setAnimation(R.raw.four);
                break;
            case 5:
                computer.setAnimation(R.raw.five);
                break;
            case 6:
                computer.setAnimation(R.raw.six);
                break;
        }
        computer.playAnimation();
        computer.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                computer.setVisibility(View.GONE);
            }
        });
    }
    public void disableButtons(){
        findViewById(R.id.button1).setEnabled(false);
        findViewById(R.id.button2).setEnabled(false);
        findViewById(R.id.button3).setEnabled(false);
        findViewById(R.id.button4).setEnabled(false);
        findViewById(R.id.button5).setEnabled(false);
        findViewById(R.id.button6).setEnabled(false);
        findViewById(R.id.imageButton).setEnabled(false);

    }
    public void enableButtons() throws InterruptedException{
        findViewById(R.id.button1).setEnabled(true);
        findViewById(R.id.button2).setEnabled(true);
        findViewById(R.id.button3).setEnabled(true);
        findViewById(R.id.button4).setEnabled(true);
        findViewById(R.id.button5).setEnabled(true);
        findViewById(R.id.button6).setEnabled(true);
        findViewById(R.id.imageButton).setEnabled(true);
    }
    public void delay()throws InterruptedException{
        TimeUnit.SECONDS.sleep(3);
    }
    }

