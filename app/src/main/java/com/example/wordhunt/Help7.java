package com.example.wordhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * This class is contains the final page of the 7 instructions pages
 *
 * @author Kashifa Hussain, 180041227
 */


public class Help7 extends AppCompatActivity {


    Button next;

    /**
     * this method is called when the activity is created
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help7);
        next=findViewById(R.id.next7);

        /**
         * if the FINISH button is pressed, it will close the activity and opens the main menu
         *
         * @author Kashifa K. Hossain, 180041227
         */
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent4 = new Intent(Help7.this, MainActivity.class);
                startActivity(intent4);
            }
        });

    }
}