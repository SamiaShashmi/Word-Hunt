package com.example.wordhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class mainGrid extends AppCompatActivity {

    RecyclerView dataList;
    List<String> letters;
    Adapter adapter;
    String url;
    public static int totalLevelCount = 1;
    public static int lifeCount;
    public static boolean isAccepted = false;
    private static final long totalTime = 16000;
    private long timeLeftinMiliSec = totalTime;
    private TextView countdownText;
    public static CountDownTimer countDownTimer;
    public static boolean isFinished = false;

    private boolean timeRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);
        gridGenerator(totalLevelCount);
        lifeCount = 3;
        countdownText = findViewById(R.id.timer);
        startTimer();
        updateCountDownText();


    }
    public void gridGenerator(int totalLevelCount){
        dataList = findViewById(R.id.dataList);

        letters = new ArrayList<>();
        Random r = new Random();

        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String forDivByFive = "home";
        String forDivByFour = "plan";
        String forDivByThree = "goat";
        String forRest = "seat";
        for (int i = 0; i < 64; i++) {
            if(i == 10 || i == 20 || i == 40 || i == 50)
            {
                if(totalLevelCount % 5 == 0 && i == 10){
                    letters.add("" + "h");
                }
                else if(totalLevelCount % 5 == 0 && i == 20){
                    letters.add("" + "o");
                }
                else if(totalLevelCount % 5 == 0 && i == 40){
                    letters.add("" + "m");
                }
                else if(totalLevelCount % 5 == 0 && i == 50){
                    letters.add("" + "e");
                }
                else if(totalLevelCount % 4 == 0 && i == 10){
                    letters.add("" + "p");
                }
                else if(totalLevelCount % 4 == 0 && i == 20){
                    letters.add("" + "l");
                }
                else if(totalLevelCount % 4 == 0 && i == 40){
                    letters.add("" + "a");
                }
                else if(totalLevelCount % 4 == 0 && i == 50){
                    letters.add("" + "n");
                }
                else if(totalLevelCount % 3 == 0 && i == 10){
                    letters.add("" + "g");
                }
                else if(totalLevelCount % 3 == 0 && i == 20){
                    letters.add("" + "o");
                }
                else if(totalLevelCount % 3 == 0 && i == 40){
                    letters.add("" + "a");
                }
                else if(totalLevelCount % 3 == 0 && i == 50){
                    letters.add("" + "t");
                }
                else if(totalLevelCount % 2 == 0 && i == 10){
                    letters.add("" + "b");
                }
                else if(totalLevelCount % 2 == 0 && i == 20){
                    letters.add("" + "o");
                }
                else if(totalLevelCount % 2 == 0 && i == 40){
                    letters.add("" + "a");
                }
                else if(totalLevelCount % 2 == 0 && i == 50){
                    letters.add("" + "t");
                }
                else if(i == 10){
                    letters.add("" + "s");
                }
                else if(i == 20){
                    letters.add("" + "e");
                }
                else if(i == 40){
                    letters.add("" + "a");
                }
                else if(i == 50){
                    letters.add("" + "t");
                }
            }
            else
            {
                char rand = alphabet.charAt(r.nextInt(alphabet.length()));
                letters.add("" + rand);
            }

        }


        adapter = new Adapter(this, this, letters);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 8, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);
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
        if(seconds == 1)
        {
            newCountDown(seconds, timeLeft);
        }
        if(seconds <= 5)
        {
            clockBlink();
        }
        if(seconds <= 8)
        {
            bulbScale();
        }
    }

    public void levelChange()
    {
        totalLevelCount++;
        TextView textLevel = (TextView) findViewById(R.id.levelCount);
        String totalString = textLevel.getText().toString();
        String levelString = totalString.substring(0, totalString.length() - 3);
        int currentlevel = Integer.parseInt(levelString) + 1;
        String newLevel = Integer.toString(currentlevel) + "/50";
        textLevel.setText(newLevel);
        TextView prevWord = (TextView) findViewById(R.id.madeWord);
        prevWord.setText("");
        gridGenerator(totalLevelCount);
        resetTimer();
    }

    private void resetTimer()
    {
        countDownTimer.cancel();
        timeLeftinMiliSec = totalTime;
        startTimer();
        updateCountDownText();
    }

    public void decrementLife()
    {
        if(lifeCount == 3)
        {
            ImageView life3 = findViewById(R.id.life3);
            life3.setVisibility(View.INVISIBLE);
            lifeCount--;
        }
        else if(lifeCount == 2)
        {
            ImageView life2 = findViewById(R.id.life2);
            life2.setVisibility(View.INVISIBLE);
            lifeCount--;
        }

    }
    private void newCountDown(int seconds, String timeLeft)
    {
        timeLeft = String.format("%02d", seconds);
        countdownText.setText(timeLeft);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                levelChange();
                decrementLife();
            }
        }, 1000);
    }

    private void clockBlink()
    {
       /* Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clock_blink);
        ImageView clock = findViewById(R.id.clock);
        clock.startAnimation(animation);*/
        ImageView clock = findViewById(R.id.clock);
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(600); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        //animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        clock.startAnimation(animation); //to start animation

    }

    public void highlight(View view)
    {

        RecyclerView recyclerView = findViewById(R.id.dataList);
        View highlight1 = recyclerView.findViewHolderForAdapterPosition(10).itemView;
        View highlight2 = recyclerView.findViewHolderForAdapterPosition(20).itemView;
        View highlight3 = recyclerView.findViewHolderForAdapterPosition(40).itemView;
        View highlight4 = recyclerView.findViewHolderForAdapterPosition(50).itemView;
        /*Animation animation = new ScaleAnimation(
                1f, 1.2f, // Start and end values for the X axis scaling
                1f, 1.2f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 1f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        animation.setDuration(1000);
        animation.setRepeatCount(4);
        highlight1.startAnimation(animation);
        highlight2.startAnimation(animation);
        highlight3.startAnimation(animation);
        highlight4.startAnimation(animation);*/
        highlight1.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        highlight2.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        highlight3.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        highlight4.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        if(lifeCount == 3)
        {
            ImageView life3 = findViewById(R.id.life3);
            life3.setVisibility(View.INVISIBLE);
            lifeCount--;
        }
        else if(lifeCount == 2)
        {
            ImageView life2 = findViewById(R.id.life2);
            life2.setVisibility(View.INVISIBLE);
            lifeCount--;
        }
    }

    public void deleteMadeWord(View view) {
        TextView madeWordText = findViewById(R.id.madeWord);
        String madeWordString = madeWordText.getText().toString();
        madeWordString = madeWordString.substring(0, madeWordString.length() - 1);
        madeWordText.setText(madeWordString);
    }
    private void bulbScale()
    {
        ImageView bulb = findViewById(R.id.bulb);
        Animation animation = new ScaleAnimation(
                1f, 1.1f, // Start and end values for the X axis scaling
                1f, 1.1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 1f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        animation.setDuration(500);
        bulb.startAnimation(animation);
    }
    public void checkValidity(View view) throws InterruptedException {
        Dialog successWindow;
        successWindow = new Dialog(this);
        successWindow.setContentView(R.layout.success_pop_up);
        TextView meaning = successWindow.findViewById(R.id.meanings);
        DictionaryRequest dR = new DictionaryRequest(this, meaning, mainGrid.this);
        url = dictionaryEntries();
        dR.execute(url);
        //TimeUnit.SECONDS.sleep(5);
        //postCheckAction();
        //int time = 0;
        /*while (time < 10){
            TimeUnit.SECONDS.sleep(1);
            time++;
            if (isFinished == true) {
                isFinished = false;
                break;
            }
        }
        */
    }

    public void postCheckAction()
    {
        if(isAccepted == false)
        {
            decrementLife();
            levelChange();
        }
        else{
            levelChange();
            isAccepted = false;
        }
    }

    public void showSuccessPopUp(String str)
    {
        Dialog successWindow;
        successWindow = new Dialog(this);
        successWindow.setContentView(R.layout.success_pop_up);
        TextView meaning = successWindow.findViewById(R.id.meanings);
        meaning.setText(str);
        TextView madeword = findViewById(R.id.madeWord);
        TextView foundWord = successWindow.findViewById(R.id.foundWord);
        foundWord.setText(madeword.getText().toString());
        successWindow.show();
    }

    private String dictionaryEntries() {
        TextView madeword = findViewById(R.id.madeWord);
        /*Dialog successWindow;
        successWindow = new Dialog(this);
        successWindow.setContentView(R.layout.success_pop_up);
        TextView foundWord = successWindow.findViewById(R.id.madeWord);
        foundWord.setText(madeword.toString());*/
        final String language = "en-gb";
        final String word = madeword.getText().toString();
        final String fields = "definitions";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }
    public void successLevelChange(View view)
    {
        Dialog successWindow;
        successWindow = new Dialog(this);
        successWindow.setContentView(R.layout.success_pop_up);
        successWindow.dismiss();
        levelChange();
    }
    /*public void showMeaning(View view)
    {
        Dialog successWindow;
        successWindow = new Dialog(this);
        successWindow.setContentView(R.layout.success_pop_up);
        TextView meaning = successWindow.findViewById(R.id.meanings);
        meaning.setVisibility(View.VISIBLE);
    }*/
}