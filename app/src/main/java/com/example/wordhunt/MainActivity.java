package com.example.wordhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    Button NewGame;
    Button Resume;
    Button HighScore;
    Button Exit;
    Button Help;
    Dialog playerNameWindow;
    static Boolean sound_check = false;
    public MediaPlayer song;
    RadioGroup radioGroup;
    public int radioChecked = 1;

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
        /*HighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        /*Resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, FromResume.class);
                startActivity(intent2);
            }
        });

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GoAway = new Intent(getApplicationContext(),MainActivity.class);
                GoAway.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                GoAway.putExtra("EXIT",true);
                startActivity(GoAway);

                finish();
                System.exit(0);
            }
        });*/
        /*Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity.this, Help1.class);
                startActivity(intent4);
            }
        });*/

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

    public void exitGame(View view) {
        finish();
        System.exit(0);
    }
    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);


    }
}




