package com.example.wordhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.wordhunt.mainGrid.isOver;

/**
 *this is the main activity class that controls all the functionalities of the components used in it
 *
 * @author Samia Islam, 180041237
 */
public class MainActivity extends AppCompatActivity {

    Button NewGame;
    Button Resume;
    Button HighScore;
    Button Exit;
    Button Help;
    Dialog playerNameWindow;
    static Boolean sound_check = false;
    MediaPlayer song;

    /**
     * this method is called when the activity is created
     *
     * @param savedInstanceState
     */
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

        if(!sound_check)
        {
            song = MediaPlayer.create(MainActivity.this,R.raw.song);
            song.start();
            sound_check = true;
        }


        NewGame.setOnClickListener(new View.OnClickListener() {
            /**
             * this method controls what happens when the New game button is pressed.
             * it opens up the window where the player is prompted to give their name
             *
             * @param view
             */
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

    /**
     * this method takes the name as input and then starts the game
     *
     * @param view
     */
    public void openGame(View view)
    {
        EditText e = playerNameWindow.findViewById(R.id.playerName);
        String playerNameFromPopUp = e.getText().toString();
        playerNameWindow.dismiss();
        Intent intent = new Intent(MainActivity.this, mainGrid.class);
        intent.putExtra("playerName", playerNameFromPopUp);
        startActivity(intent);
    }

    /**
     * this method controls what happens when the leaderboard button is pressed by displaying
     * it shows the leaderboard
     *
     * @param view
     */
    public void openleaderBoard(View view)
    {
        Intent intent3 = new Intent(MainActivity.this, HighScore.class);
        startActivity(intent3);
    }

    /**
     * this method redirects the user to the instructions that are required to play the game
     *
     * @param view
     */
    public void openHelp(View view)
    {
        Intent intent3 = new Intent(MainActivity.this, Help1.class);
        startActivity(intent3);
    }

    /**
     * this method resumes the game from a previously saved state if the game had not been over yet
     *
     * @param view
     */
    public void resumeMainMenu(View view) {
        isOver = false;
        Intent intent = new Intent(MainActivity.this, mainGrid.class);
        startActivity(intent);
    }
}




