package com.example.mazegamemain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;
import com.livelife.motolibrary.OnAntEventListener;

import static com.livelife.motolibrary.AntData.LED_COLOR_BLUE;
import static com.livelife.motolibrary.AntData.LED_COLOR_GREEN;
import static com.livelife.motolibrary.AntData.LED_COLOR_ORANGE;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;

/*
    Consist of "start game" and init of tiles

 */

public class MainActivity extends AppCompatActivity implements OnAntEventListener {

    MotoConnection connection;
    MotoSound sound;

    //buttons
    Button button_pairing;

    boolean isPairing = false;

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
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        connection.stopMotoConnection();
        connection.unregisterListener(MainActivity.this);
    }
}
