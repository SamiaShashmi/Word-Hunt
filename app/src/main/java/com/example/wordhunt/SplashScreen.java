package com.example.wordhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 *this class shows an animation of the text 'Word Hun' while opening the app
 *
 * @author Samia Islam, 180041237
 */
public class SplashScreen extends AppCompatActivity implements Animation.AnimationListener {

    LinearLayout linearLayout;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        linearLayout = findViewById(R.id.title);
        animation = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.splash_anim);
        animation.setAnimationListener(this);
        linearLayout.startAnimation(animation);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            /**
             * this method shows the animation for 2 seconds and then closes the splash screen and opens the main menu
             *
             * @author Samia Islam, 180041237
             */
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}