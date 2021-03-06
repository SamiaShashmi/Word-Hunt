package com.example.wordhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncAdapterType;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * activity of main game intent
 *
 * @author Samia Islam, 180041237
 */

public class mainGrid extends AppCompatActivity {

    RecyclerView dataList;
    List<String> letters;
    Adapter adapter;
    String url;
    public static int totalLevelCount;
    public static int lifeCount;
    public static boolean isAccepted = false;
    private static long totalTime;
    public static long timeLeftinMiliSec;
    private TextView countdownText;
    public static CountDownTimer countDownTimer;
    public static CountDownTimer countDownTimer2;
    public static boolean isFinished = false;
    public Dialog successWindow;
    public Dialog failureWindow;
    public Dialog gameOverWindow;
    public Dialog playerNameWindow;
    public Dialog pauseWindow;
    public Dialog resumeAlertWindow;
    public static int score;
    public static int intValue;
    private TextView scoreText;
    private TextView textLevel;
    public static boolean isOver = false;
    public static boolean isShared = false;
    public String nameString;
    DatabaseReference ref;
    private boolean timeRunning;
    Score scores;
    static MediaPlayer song;
    static MediaPlayer mainSong;
    static boolean isSong = false;
    static boolean isMainSong = false;
    static boolean iscount2 = false;
    static boolean iscount = false;
    long maxid = 0;
    MainActivity m;

    public static String remainingTime;
    public static long remTime;
    public static long actRemTime;
    public static String savedScore;
    public static String pausedGrid;
    public static String newGrid;

    ImageView mute;
    ImageView unmute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);

        int y = 0;
        y = getIntent().getExtras().getInt("share"); /// to be assured whether there is a saved game or not
        SharedPreferences sharedPreferences = mainGrid.this.getPreferences(Context.MODE_PRIVATE);
        if(y == 1){ ///initializes by values of saved game to resume
            totalTime = sharedPreferences.getLong("timer", 0);
            score = Integer.parseInt(sharedPreferences.getString("score", "score not found"));
            lifeCount = sharedPreferences.getInt("life", 0);
            totalLevelCount = sharedPreferences.getInt("level", 0);
            newGrid = sharedPreferences.getString("grid", "");
            nameString = sharedPreferences.getString("player", "ghost");
            totalTime = totalTime * 1000;
            System.out.println(totalTime);
            System.out.println(score);
            System.out.println(lifeCount);
            System.out.println(totalLevelCount);
            System.out.println(newGrid);
            if(totalTime <= 1000)
            {
                resumeAlertWindow = new Dialog(this);
                resumeAlertWindow.setContentView(R.layout.resume_alert);
                if(isSong)
                {
                    song.pause();
                    isSong = false;
                }
                resumeAlertWindow.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        resumeAlertWindow.cancel();
                        Intent intent5 = new Intent(mainGrid.this, MainActivity.class);
                        finish();
                        startActivity(intent5);

                    }
                }, 3000);
            }
            if(lifeCount == 2)
            {
                ImageView life3 = findViewById(R.id.life3);
                life3.setVisibility(View.INVISIBLE);

            }
            else if(lifeCount == 1)
            {
                ImageView life3 = findViewById(R.id.life3);
                life3.setVisibility(View.INVISIBLE);
                ImageView life2 = findViewById(R.id.life2);
                life2.setVisibility(View.INVISIBLE);
            }
            if(lifeCount == 0)
            {
                ImageView life3 = findViewById(R.id.life3);
                life3.setVisibility(View.INVISIBLE);
                ImageView life2 = findViewById(R.id.life1);
                life2.setVisibility(View.INVISIBLE);
                isOver = true;
            }

        }
        else{
            Intent mIntent = getIntent();
            intValue = mIntent.getIntExtra("level", 0);
            if(intValue==1)
            {
                totalTime = 21000;
            }
            if(intValue==2)
            {
                totalTime = 16000;
            }
            if(intValue==3)
            {
                totalTime = 11000;
            }
            score = 0;
            lifeCount = 3;
            totalLevelCount = 1;
            nameString = getIntent().getExtras().getString("playerName");
        }

        gridGenerator(totalLevelCount);

        song= MediaPlayer.create(mainGrid.this,R.raw.ticking);
        mainSong = MediaPlayer.create(mainGrid.this, R.raw.song);
        Boolean isprev = getIntent().getExtras().getBoolean("isSong");
        System.out.println(isprev);
        /*if(isprev)
        {
            mainSong.start();
            mainSong.setLooping(true);
            isMainSong = true;
        }*/
        if(!isMainSong)
        {
            mainSong.start();
            mainSong.setLooping(true);
            isMainSong = true;
        }

        timeLeftinMiliSec = totalTime;
        System.out.println(timeLeftinMiliSec);
        isOver = false;
        countdownText = findViewById(R.id.timer);
        startTimer();
        updateCountDownText();
        scoreText = findViewById(R.id.score);
        String scoreString = String.format("%02d", score);
        scoreText.setText(scoreString);


       textLevel = (TextView) findViewById(R.id.levelCount);
       textLevel.setText(Integer.toString(totalLevelCount));

        successWindow = new Dialog(this);
        successWindow.setContentView(R.layout.success_pop_up);

        failureWindow = new Dialog(this);
        failureWindow.setContentView(R.layout.failure_pop_up);

        gameOverWindow = new Dialog(this);
        gameOverWindow.setContentView(R.layout.game_over_pop_up);

        playerNameWindow = new Dialog(this);
        playerNameWindow.setContentView(R.layout.player_info_pop_up);

        pauseWindow = new Dialog(this);
        pauseWindow.setContentView(R.layout.pause_pop_up);

        ref = FirebaseDatabase.getInstance().getReference().child("Score");
        scores = new Score();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxid = (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Intent intent = getIntent();



    }

    /**
     * if the player press the back button of phone while the game is running, a pop up window will be opened
     *
     * @author Samia Islam, 180041237
     */
    @Override
    public void onBackPressed() {
        showPauseWindow();
//        super.onBackPressed();
    }

    /**
     * this function generates the grid of random letters, there will be random grids for each level
     *
     * @author Samia Islam, 180041237
     * @param totalLevelCount (integer) to make different suggestions for different levels
     */
    public void gridGenerator(int totalLevelCount){

        dataList = findViewById(R.id.dataList);

        letters = new ArrayList<>();
        Random r = new Random();

        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String forDivByFive = "home";
        String forDivByFour = "plan";
        String forDivByThree = "goat";
        String forRest = "seat";
        /*if(isOver == false && isShared == true){
            for(int k=0; k<56; k++){
                String a = String.valueOf(newGrid.charAt(k));
                letters.add("" + a);
            }
        } else*/{
            for (int i = 0; i < 56; i++) { //to show suggestion, some indices from the grid is reserved
                                            // to show a specific letter for each level
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
                else ///for other indiices, random letters will be shown
                {
                    char rand = alphabet.charAt(r.nextInt(alphabet.length()));
                    letters.add("" + rand);
                }
            }
        }
        adapter = new Adapter(this, this, letters);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 8, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);
    }

    /**
     * if the game is started once, the timer will start running
     *
     * @author Samia Islam, 180041237
     */
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
        iscount = true;
    }

    /**
     * This function will update the text where the time that is left is shown
     *
     * if the time reaches to 1 sec, new count down will be started
     * if the time reaches to 5 sec, the timer CLOCK will start blinking
     * if the time reaches to 8 sec, the SUGGESTION button will start highlighting
     *
     * @author Samia Islam, 180041237
     */

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

    /**
     * once a level is finished, the level will be incremented and the text will be updated
     * also the countdown will be started and new grid will be generated
     *
     * @author Samia Islam, 180041237
     */
    public void levelChange()
    {
        if(!isMainSong)
        {
            mainSong.start();
            mainSong.setLooping(true);
            isMainSong = true;
        }
        totalLevelCount++;
        String totalString = textLevel.getText().toString();
        int currentlevel = Integer.parseInt(totalString) + 1;
        String newLevel = Integer.toString(currentlevel);
        textLevel.setText(newLevel);
        TextView prevWord = (TextView) findViewById(R.id.madeWord);
        prevWord.setText("");
        gridGenerator(totalLevelCount);
        resetTimer();
    }

    /**
     * this funtion will reset the time based on the mode of the game
     *
     * @author Samia Islam, 180041237
     */

    private void resetTimer()
    {
        if(iscount)
        {
            countDownTimer.cancel();
            iscount = false;
        }
        if(iscount2)
        {
            countDownTimer2.cancel();
            iscount2 = false;
        }
        if(intValue==1)
        {
            totalTime = 21000;
        }
        if(intValue==2)
        {
            totalTime = 16000;
        }
        if(intValue==3)
        {
            totalTime = 11000;
        }
        timeLeftinMiliSec = totalTime;
        startTimer();
        updateCountDownText();
    }

    /**
     * this function will decrement a life if the player fails to choose a valid word
     * also it will decrement the images of heart
     *
     * @author Samia Islam, 180041237
     */

    public void decrementLife()
    {
        lifeCount--;
        if(lifeCount == 2)
        {
            ImageView life3 = findViewById(R.id.life3);
            life3.setVisibility(View.INVISIBLE);

        }
        else if(lifeCount == 1)
        {
            ImageView life2 = findViewById(R.id.life2);
            life2.setVisibility(View.INVISIBLE);
        }
        if(lifeCount == 0)
        {
            ImageView life2 = findViewById(R.id.life1);
            life2.setVisibility(View.INVISIBLE);
            isOver = true;
            showGameOverWindow();
        }

    }

    /**
     * if the game is over, a pop up window showing the total score will be opened for 3 seconds
     * and the score will be stored in database
     *
     * @author Samia Islam, 180041237
     */

    private void showGameOverWindow()
    {
        if(isSong)
        {
            song.pause();
            isSong = false;
        }

        TextView totalScore = gameOverWindow.findViewById(R.id.totalScore);
        totalScore.setText(scoreText.getText().toString());
        gameOverWindow.show();
        if(iscount)
        {
            countDownTimer.cancel();
            iscount = false;
        }
        if(iscount2)
        {
            countDownTimer2.cancel();
            iscount2 = false;
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                gameOverWindow.cancel();
               Intent intent5 = new Intent(mainGrid.this, MainActivity.class);
               finish();
               startActivity(intent5);

            }
        }, 3000);

        storeScore();
    }

    /**
     * this function will store the score in database
     *
     * @author Samia Islam, 180041237
     */
    public void storeScore()
    {
        String scoreString = scoreText.getText().toString();
        scores.setName(nameString);
        scores.setScore(scoreString);
        ref.child("player" + String.valueOf(maxid + 1)).setValue(scores);

    }
    /*public void cancelGOPopUp()
    {
        gameOverWindow.cancel();
        Intent intent = new Intent(mainGrid.this, MainActivity.class);
        startActivity(intent);
    }*/

    /**
     * if the player fails to find any word, new count down will be started for next level
     * @param seconds
     * @param timeLeft
     */
    private void newCountDown(int seconds, String timeLeft)
    {
        song.pause();
        timeLeft = String.format("%02d", seconds);
        countdownText.setText(timeLeft);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                showFailurePopUp();
                decrementLife();
            }
        }, 1000);
    }

    /**
     * if the remaining time for a level reaches to 5 seconds, the CLOCK will sart blinking
     * and a clock ticking sound will start playing at the background
     */
    private void clockBlink()
    {
       /* Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clock_blink);
        ImageView clock = findViewById(R.id.clock);
        clock.startAnimation(animation);*/
        if(isMainSong)
        {
            mainSong.pause();
            isMainSong = false;
        }
        isSong = true;
        if(iscount)
        {
            song.start();
        }
        if(iscount2)
        {
            song.start();
        }

        ImageView clock = findViewById(R.id.clock);
        /**
         * @author Kashifa K. Hossain, 180041227
         */
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(600); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        //animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        clock.startAnimation(animation); //to start animation

    }

    /**
     * if the SUGGESTION button is pressed, it will highlight the boxes by which a valid word can be made
     * and a life will be decremented
     *
     * @author Samia Islam, 180041237
     * @param view (imageview)
     */

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
        decrementLife();
        /*if(lifeCount == 3)
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
        }*/
    }

    /**
     * if a player accidently press any wrong character, by pressing the DELETE button s(he) can delete the character
     *
     * @author Samia Islam, 180041237
     * @param view
     */

    public void deleteMadeWord(View view) {
        TextView madeWordText = findViewById(R.id.madeWord);
        String madeWordString = madeWordText.getText().toString();
        madeWordString = madeWordString.substring(0, madeWordString.length() - 1);
        madeWordText.setText(madeWordString);
    }

    /**
     * when the remaining time will reach to 8 seconds, the SUGGESTION button will start highlighting
     *
     * @author Samia Islam, 180041237
     */

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

    /**
     * the funtion will check if the word is valid or not by using an object of DictionaryRequest class
     *
     * @author Samia Islam, 180041237
     * @param view (button)
     * @throws InterruptedException
     */
    public void checkValidity(View view) throws InterruptedException {
        if(iscount)
        {
            countDownTimer.cancel();
            iscount = false;
        }
        if(iscount2)
        {
            countDownTimer2.cancel();
            iscount2 = false;
        }
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

    /**
     * if the player succeeds to find a valid word within the given time, he/she will gain the score same as the length of the word
     * and a pop up window will be opened containing the definition of that word
     *
     * @author Samia Islam, 180041237
     * @param str the definition of that word
     */

    public void showSuccessPopUp(String str)
    {
        if(isSong)
        {
            song.pause();
            isSong = false;
        }
        TextView meaning = successWindow.findViewById(R.id.meanings);
        meaning.setText(str);
        TextView madeword = findViewById(R.id.madeWord);
        TextView foundWord = successWindow.findViewById(R.id.foundWord);
        foundWord.setText(madeword.getText().toString());
        score += madeword.getText().toString().length();
        String scoreString = String.format("%02d", score);
        scoreText.setText(scoreString);
        successWindow.show();
    }

    /**
     * if the player fails to find any valid word, it will show a pop up window for 3 seconds
     *
     * @author Samia Islam, 180041237
     */
    public void showFailurePopUp(){

        if(isSong)
        {
            song.pause();
            isSong = false;
        }
        if(isOver == false)
        {
            failureWindow.show();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                failureWindow.cancel();
                if(isOver == false)
                {
                    levelChange();
                }

            }
        }, 3000);

    }

    /**
     * this function makes a URL to get the definition of the word made by the player
     *
     * @author Samia Islam, 180041237
     * @return URL
     */

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

    /**
     *shuts down the pop up window showing the definition of the word
     *
     * @author Samia Islam, 180041237
     * @param view (button)
     */
    public void successLevelChange(View view)
    {
        successWindow.cancel();
        levelChange();
    }

    /**
     * if the player wants to go to the main menu while the game is running and presses the MAIN MENU button
     * the values of the current game will be saved and main menu will be opened
     *
     * @author Shah Jawad Islam, 180041223
     * @param view (button)
     */
    public void gotoMainMenu(View view)
    {
        mainSong.stop();
        song.stop();
        isMainSong = false;
        isSong = false;

        savedScore = scoreText.getText().toString();
        System.out.println(savedScore);
        pausedGrid = "";
        for(int j = 0; j < 56; j++)
            pausedGrid = pausedGrid + letters.get(j);

        SharedPreferences sharedPreferences = mainGrid.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong("timer", remTime);
        editor.putString("score", savedScore);
        editor.putInt("life", lifeCount);
        editor.putString("grid", pausedGrid);
        editor.putInt("level", totalLevelCount);
        editor.putString("player", nameString);
        editor.apply();


        isShared = true;
        Intent intent = new Intent(mainGrid.this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    /**
     * if the player press the back button when the game is running, a pop up window will be opened
     * and the timer will be paused
     *
     * @author Shah Jawad Islam, 180041223
     */
    public void showPauseWindow()
    {
        remainingTime = countdownText.getText().toString();
        remTime = Long.parseLong(remainingTime);
        System.out.println(remTime);
        if(iscount)
        {
            countDownTimer.cancel();
            iscount = false;
        }
        if(iscount2)
        {
            countDownTimer2.cancel();
            iscount2 = false;
        }
        pauseWindow.show();
    }

    /**
     * if the player clicks the RESUME button, the game will start at its previous state
     *
     * @author Shah Jawad Islam, 180041223
     * @param view (button)
     */
    public void resumeGame(View view)
    {
        pauseWindow.cancel();
        countDownTimer.cancel();
//        countDownTimer.start();
        actRemTime = remTime * 1000;
        countDownTimer2 = new CountDownTimer(actRemTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                actRemTime = millisUntilFinished;
                int seconds = (int) actRemTime / 1000;
                String timeLeft = String.format("%02d", seconds);
                countdownText.setText(timeLeft);

                if(seconds == 1)
                    newCountDown(seconds, timeLeft);

                if(seconds <= 5)
                    clockBlink();

                if(seconds <= 8)
                    bulbScale();

            }
            @Override
            public void onFinish() {
                timeRunning = false;
            }
        }.start();
        iscount2 = true;
        timeRunning = true;


    }


}