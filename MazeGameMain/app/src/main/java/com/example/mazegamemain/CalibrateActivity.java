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

public class CalibrateActivity extends AppCompatActivity implements OnAntEventListener {

    MotoConnection connection;
    CalibrateActivity calibrateActivity;

    //UI text
    TextView calibrate_instructions;

    //buttons
    Button button_StartCalibrate;
    Button button_EndCalibrate;
    int currentTile;
    boolean isCalibrating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);

        calibrateActivity = new CalibrateActivity();

        button_EndCalibrate = findViewById(R.id.button_EndCalibrate);
        button_EndCalibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CalibrateActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


        /*button_StartCalibrate.findViewById(R.id.button_StartCalibrate);
        button_StartCalibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isCalibrating){
                    button_StartCalibrate.setText("Press on the tile you want to be up, right, down, left");
                    int tile = EVENT_PRESS;
                    for (int t: connection.connectedTiles) {
                        if (tile==t) {
                        connection.setTileNumLeds(AntData.LED_COLOR_GREEN,tile,3); //LED Arrows

                        }
                    }
                    currentTile = tile;
                }

                else {
                    button_StartCalibrate.setText("Calibration done");
                }
                isCalibrating = !isCalibrating;
            }
        });*/

        calibrate_instructions = findViewById(R.id.calibrate_instructions);
        calibrate_instructions.setText("Press on 'Start Calibration', then press on tiles up, right, down, left and 'End Calibration'");

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
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        connection.stopMotoConnection();
        connection.unregisterListener(CalibrateActivity.this);
    }
}
