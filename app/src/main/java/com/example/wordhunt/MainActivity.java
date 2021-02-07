package com.example.wordhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import static com.example.wordhunt.mainGrid.isOver;

public class MainActivity extends AppCompatActivity {

    Button NewGame;
    Button Resume;
    Button HighScore;
    Button Help;
    Dialog playerNameWindow;
    static Boolean sound_check = false;
    public MediaPlayer song;
    RadioGroup radioGroup;
    public int radioChecked = 1;
    ImageView muteimg;
    ImageView unmuteimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewGame=findViewById(R.id.newgame);
        Resume=findViewById(R.id.resume);
        /*HighScore=findViewById(R.id.highscore);
        Exit=findViewById(R.id.exit);
        Help=findViewById(R.id.help);*/


        playerNameWindow = new Dialog(MainActivity.this);
        playerNameWindow.setContentView(R.layout.player_info_pop_up);

        muteimg = findViewById(R.id.mute);
        unmuteimg = findViewById(R.id.unmute);
        unmuteimg.setVisibility(View.INVISIBLE);

        if(sound_check == false)
        {
            song = MediaPlayer.create(MainActivity.this,R.raw.song);
            song.start();
            sound_check = true;
        }


        NewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerNameWindow.show();
            }
        });

        ImageView Exit = findViewById(R.id.exitbtn);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
                System.exit(0);
            }
        });

    }
    public void openGame(View view)
    {
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
            sound_check = true;
            muteimg.setVisibility(View.VISIBLE);
            unmuteimg.setVisibility(View.INVISIBLE);
        }
    }
    public void resumeMainMenu(View view) {
        isOver = false;
        Intent intent = new Intent(MainActivity.this, mainGrid.class);
        startActivity(intent);
    }
}




