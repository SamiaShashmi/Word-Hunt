package com.example.wordhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button NewGame;
    Button Resume;
    Button HighScore;
    Button Exit;
    Button Help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewGame=findViewById(R.id.newgame);
        Resume=findViewById(R.id.resume);
        HighScore=findViewById(R.id.highscore);
        Exit=findViewById(R.id.exit);
        Help=findViewById(R.id.help);
        NewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, mainGrid.class);
                startActivity(intent);
            }
        });
        /*Resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, FromResume.class);
                startActivity(intent2);
            }
        });
        HighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, FromHighScore.class);
                startActivity(intent3);
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
        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity.this, Help1.class);
                startActivity(intent4);
            }
        });

    }
}