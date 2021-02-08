package com.example.wordhunt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import static com.example.wordhunt.mainGrid.isOver;

public class MainActivity extends AppCompatActivity {

    ImageView NewGame;
    Button Resume;
    Button HighScore;
    Button Help;
    Button startGame;
    Dialog playerNameWindow;
    Dialog exitWindow;
    static Boolean sound_check = false;
    public static MediaPlayer song;
    RadioGroup radioGroup;
    public int radioChecked = 1;
    ImageView muteimg;
    ImageView unmuteimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewGame=findViewById(R.id.newgame);
        //Resume=findViewById(R.id.resume);

        /*HighScore=findViewById(R.id.highscore);
        Exit=findViewById(R.id.exit);
        Help=findViewById(R.id.help);*/


        playerNameWindow = new Dialog(MainActivity.this);
        playerNameWindow.setContentView(R.layout.player_info_pop_up);

        exitWindow = new Dialog(MainActivity.this);
        exitWindow.setContentView(R.layout.exit_pop_up);

        muteimg = findViewById(R.id.mute);
        unmuteimg = findViewById(R.id.unmute);
        unmuteimg.setVisibility(View.INVISIBLE);

        startGame = playerNameWindow.findViewById(R.id.startBtn);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e = playerNameWindow.findViewById(R.id.playerName);
                String playerNameFromPopUp = e.getText().toString();
                playerNameWindow.dismiss();
                Intent intent = new Intent(MainActivity.this, mainGrid.class);
                intent.putExtra("playerName", playerNameFromPopUp);
                intent.putExtra("level", radioChecked);
                song.pause();
                sound_check = false;
                startActivity(intent);
            }
        });

        song = MediaPlayer.create(MainActivity.this,R.raw.song);

        if(sound_check == false)
        {

            song.start();
            song.setLooping(true);
            sound_check = true;
        }


        NewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerNameWindow.show();
            }
        });



    }

    public void exitYes(View view)
    {
        finish();
        System.exit(0);
    }
    public void openGame(View view)
    {
        EditText e = playerNameWindow.findViewById(R.id.playerName);
        String playerNameFromPopUp = e.getText().toString();
        playerNameWindow.dismiss();
        Intent intent = new Intent(MainActivity.this, mainGrid.class);
        intent.putExtra("playerName", playerNameFromPopUp);
        intent.putExtra("level", radioChecked);
        intent.putExtra("isSong", sound_check);
        System.out.println(sound_check);
        song.pause();
        sound_check = false;
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkButton(View view)
    {
        radioGroup = playerNameWindow.findViewById(R.id.radio);
        int radioId = 0;
        radioId = radioGroup.getCheckedRadioButtonId();
        switch (radioId) {
            case R.id.easy:
                radioChecked = 1;
                break;
            case R.id.medium:
                radioChecked = 2;
                break;
            case R.id.hard:
                radioChecked = 3;
                break;
        }
        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void openleaderBoard(View view)
    {
        Intent intent3 = new Intent(MainActivity.this, HighScore.class);
        startActivity(intent3);
    }
    public void openHelp(View view)
    {
        Intent intent3 = new Intent(MainActivity.this, Help1.class);
        startActivity(intent3);
    }


    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);


    }
    public void mute(View view)
    {
        if(sound_check == true)
        {
            song.pause();
            sound_check = false;
            muteimg.setVisibility(View.INVISIBLE);
            unmuteimg.setVisibility(View.VISIBLE);
        }

    }
    public void unmute(View view)
    {
        if(sound_check == false)
        {
            song.start();
            song.setLooping(true);
            sound_check = true;
            muteimg.setVisibility(View.VISIBLE);
            unmuteimg.setVisibility(View.INVISIBLE);
        }
    }
    public void resumeMainMenu(View view) {
        isOver = false;
        int x = 1;
        Intent intent = new Intent(MainActivity.this, mainGrid.class);
        intent.putExtra("share", x);
        intent.putExtra("level", radioChecked);
        intent.putExtra("isSong", sound_check);
        System.out.println(sound_check);
        song.pause();
        sound_check = false;
        startActivity(intent);
    }
    public void exit(View view)
    {
        exitWindow.show();
    }
    public void exitNo(View view)
    {
        exitWindow.dismiss();
    }

}




