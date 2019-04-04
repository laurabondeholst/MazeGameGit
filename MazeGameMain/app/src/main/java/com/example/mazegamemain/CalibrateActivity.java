package com.example.mazegamemain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.OnAntEventListener;

public class CalibrateActivity extends AppCompatActivity implements OnAntEventListener {

    MotoConnection connection;
    MazeGame mazeGame;

    //UI text
    TextView calibrate_instructions;

    //buttons
    Button button_EndCalibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);

        mazeGame = new MazeGame();

        button_EndCalibrate = findViewById(R.id.button_calibrate);
        button_EndCalibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connection.unregisterListener(CalibrateActivity.this);
                Intent i = new Intent(CalibrateActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        mazeGame.setDescription("Press on the tile you want to be up");
        calibrate_instructions = findViewById(R.id.calibrate_instructions);
        calibrate_instructions.setText(mazeGame.getDescription());

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
