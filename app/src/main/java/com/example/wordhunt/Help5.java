package com.example.wordhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * This class contains the fifth page of the 7 instructions pages
 *
 * @author Kashifa Hussain, 180041227
 */

public class Help5 extends AppCompatActivity {


    Button next;

    /**
     * this method is called when the activity is created
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help5);
        next=findViewById(R.id.next5);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent4 = new Intent(Help5.this, Help6.class);
                startActivity(intent4);
            }
        });

    }
}