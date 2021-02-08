package com.example.wordhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Help4 extends AppCompatActivity {

    /**
     * This class is contains the fourth page of the 7 instructions pages
     *
     * @author Kashifa Hussain, 180041227
     */
    Button next;

    /**
     * this method is called when the activity is created
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help4);
        next=findViewById(R.id.next4);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent4 = new Intent(Help4.this, Help5.class);
                startActivity(intent4);
            }
        });

    }
}