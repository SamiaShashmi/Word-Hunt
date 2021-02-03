package com.example.wordhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RecyclerView dataList;
    List<String> letters;
    Adapter adapter;
    public static int totalLevelCount = 1;
    private static final long totalTime = 16000;
    private long timeLeftinMiliSec = totalTime;
    private TextView countdownText;
    private CountDownTimer countDownTimer;

    private boolean timeRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridGenerator();

        countdownText = findViewById(R.id.timer);
        startTimer();
        updateCountDownText();


    }
    public void gridGenerator(){
        dataList = findViewById(R.id.dataList);

        letters = new ArrayList<>();
        Random r = new Random();

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 64; i++) {
            char rand = alphabet.charAt(r.nextInt(alphabet.length()));
            letters.add("" + rand);
        }


        adapter = new Adapter(this, this, letters);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 8, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);
    }


    public void levelUp(View view) {
        totalLevelCount++;
        TextView textLevel = (TextView) findViewById(R.id.levelCount);
        String totalString = textLevel.getText().toString();
        String levelString = totalString.substring(0, totalString.length() - 4);
        int currentlevel = Integer.parseInt(levelString) + 1;
        String newLevel = Integer.toString(currentlevel) + "/100";
        textLevel.setText(newLevel);
        TextView prevWord = (TextView) findViewById(R.id.madeWord);
        prevWord.setText("");
        gridGenerator();
        resetTimer();
    }

    private void startTimer()
    {
        countDownTimer = new CountDownTimer(timeLeftinMiliSec, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftinMiliSec = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeRunning = false;
            }
        }.start();
        timeRunning = true;
    }

    private void updateCountDownText()
    {
        int seconds = (int) timeLeftinMiliSec / 1000;
        String timeLeft = String.format("%02d", seconds);
        countdownText.setText(timeLeft);
    }

    private void resetTimer()
    {
        countDownTimer.cancel();
        timeLeftinMiliSec = totalTime;
        startTimer();
        updateCountDownText();
    }
}