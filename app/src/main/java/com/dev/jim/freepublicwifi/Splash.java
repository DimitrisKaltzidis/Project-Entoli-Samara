package com.dev.jim.freepublicwifi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

      /*  TextView tx = (TextView)findViewById(R.id.textView);


        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .playOn(findViewById(R.id.imageView));

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/MagdaClean.otf");

        tx.setTypeface(custom_font);
*/
        new Timer().schedule(new TimerTask() {
            public void run() {
                startActivity(new Intent(Splash.this, NavigationMap.class));
            }
        }, 2500 /*amount of time in milliseconds before execution*/);
    }
}
