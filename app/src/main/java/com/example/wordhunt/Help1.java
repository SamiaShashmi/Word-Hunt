package com.example.wordhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * This class is contains the first page of the 7 instructions pages
 *
 * @author Kashifa Hussain, 180041227
 */
public class Help1 extends AppCompatActivity {

    Button next;

    /**
     * this method is called when the activity is created
     *
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help1);
        next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent4 = new Intent(Help1.this, Help2.class);
                startActivity(intent4);
            }
        });

    }
}