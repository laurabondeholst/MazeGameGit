package com.example.mazegamemain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;
import com.livelife.motolibrary.OnAntEventListener;

import static com.livelife.motolibrary.AntData.EVENT_PRESS;
import static com.livelife.motolibrary.AntData.LED_COLOR_BLUE;
import static com.livelife.motolibrary.AntData.LED_COLOR_GREEN;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;
import static com.livelife.motolibrary.AntData.LED_COLOR_ORANGE;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;

/*
    Consist of "start game" and init of tiles

    So Yichen is doing the layout on the tablet, Laura is doing the edge detection
    and Michael is doing user interference.

 */

public class MainActivity extends AppCompatActivity implements OnAntEventListener {

    MotoConnection connection;
    MotoSound sound;
    boolean isPairing = false;
    static byte SPRITE_COLOR = 3;

    //buttons
    Button button_pairing;
    Button button_calibrate;
    Button button_easy;
    Button button_medium;
    Button button_hard;
    Button button_colorRed;
    Button button_colorGreen;
    Button button_colorBlue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connecting to the tiles
        connection=MotoConnection.getInstance();
        connection.startMotoConnection(MainActivity.this);
        connection.saveRfFrequency(56); //(Group No.)*10+6
        connection.setDeviceId(5); //Your group number
        connection.registerListener(MainActivity.this);

        // init sound
        sound = MotoSound.getInstance();
        sound.initializeSounds(this);

        // pairing button
        button_pairing = findViewById(R.id.button_pairing);
        button_pairing.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!isPairing){
                    connection.pairTilesStart();
                    button_pairing.setText("Stop pairing");
                } else {
                    connection.pairTilesStop();
                    button_pairing.setText("Start pairing");
                }
                isPairing = !isPairing;
            }
        });

        //calibrate button for swapping to CalibrateActivity
        button_calibrate = findViewById(R.id.button_calibrate);
        button_calibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connection.unregisterListener(MainActivity.this);
                Intent i = new Intent(MainActivity.this, CalibrateActivity.class);
                startActivity(i);
            }
        });

        //button for picking red colored sprite
        button_colorRed = findViewById(R.id.button_colorRed);
        button_colorRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPRITE_COLOR = LED_COLOR_RED;
            }
        });

        //button for picking green colored sprite
        button_colorGreen = findViewById(R.id.button_colorGreen);
        button_colorGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPRITE_COLOR = LED_COLOR_GREEN;
            }
        });

        //button for picking blue colored sprite
        button_colorBlue = findViewById(R.id.button_colorBlue);
        button_colorBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPRITE_COLOR = LED_COLOR_BLUE;
            }
        });
    }


    @Override
    public void onMessageReceived(byte[] bytes, long l)
    {

    }

    @Override
    public void onAntServiceConnected() {

    }

    @Override
    public void onNumbersOfTilesConnected(int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        connection.registerListener(MainActivity.this);
        connection.registerListener(this);
        connection.startMotoConnection(MainActivity.this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        connection.stopMotoConnection();
        connection.unregisterListener(MainActivity.this);
    }
}
