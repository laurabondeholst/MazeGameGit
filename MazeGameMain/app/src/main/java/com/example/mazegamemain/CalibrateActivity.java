package com.example.mazegamemain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.OnAntEventListener;

import static com.livelife.motolibrary.AntData.EVENT_PRESS;
import static com.livelife.motolibrary.AntData.LED_COLOR_GREEN;
import static com.livelife.motolibrary.AntData.LED_COLOR_WHITE;

public class CalibrateActivity extends AppCompatActivity implements OnAntEventListener {
    //Michael

    MotoConnection connection;

    //UI text
    TextView calibrate_instructions;

    //buttons
    Button button_StartCalibrate;
    Button button_EndCalibrate;
    boolean isCalibrating = false;
    byte SELECTED_SPRITE_COLOR = MainActivity.SPRITE_COLOR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);

        connection = MotoConnection.getInstance();
        connection.registerListener(this);

        button_EndCalibrate = findViewById(R.id.button_EndCalibrate);
        button_EndCalibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CalibrateActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


        button_StartCalibrate = findViewById(R.id.button_StartCalibrate);
        button_StartCalibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isCalibrating){

                    for (int t = 0; t < 5; t++) {
                            connection.setTileNumLeds(SELECTED_SPRITE_COLOR,t,3); //LED Arrows
                    }
                }

                else {
                    button_StartCalibrate.setText("Calibration done");
                }
                isCalibrating = !isCalibrating;
            }
        });

        calibrate_instructions = findViewById(R.id.calibrate_instructions);
        calibrate_instructions.setText("Press on 'Start Calibration', then place the tiles so the LEDs point up, right, down, left and then press 'End Calibration'");

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
        connection.registerListener(CalibrateActivity.this);
        connection.startMotoConnection(CalibrateActivity.this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        connection.stopMotoConnection();
        connection.unregisterListener(CalibrateActivity.this);
    }
}
