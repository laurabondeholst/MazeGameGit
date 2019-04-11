package com.example.mazegamemain;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;
import com.livelife.motolibrary.OnAntEventListener;

import static com.livelife.motolibrary.AntData.LED_COLOR_BLUE;
import static com.livelife.motolibrary.AntData.LED_COLOR_GREEN;
import static com.livelife.motolibrary.AntData.LED_COLOR_ORANGE;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;

/*
    Consist of "start game" and init of tiles

    So Yichen is doing the layout on the tablet, Laura is doing the edge detection
    and Michael is doing user interference.

 */

public class MainActivity extends AppCompatActivity implements OnAntEventListener, View.OnClickListener {

    MotoConnection connection;
    MotoSound sound;

    //buttons
    Button button_pairing;
    Button startGame;
    Button exitGame;

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

        //start game and exit game button
        startGame = findViewById(R.id.startGame);
        exitGame = findViewById(R.id.exitGame);
        startGame.setOnClickListener(this);
        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.exitGame: //when press the exit game button, exit the game
                        finish();
                        break;
                    case R.id.startGame: //when press the start game button, player can choose three levels
                        String[] levels = {"Maze 1", "Maze 2", "Maze 3"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle(getString(R.string.levelSelect));
                        builder.setItems(levels, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    Intent game = new Intent(MainActivity.this, Game.class); //create an Intent to launch the game activity
                                    MazeGame maze = MazeCreator.getMaze(item+1); //use class to create the maze
                                    game.putExtra("maze", maze); //add the maze to the intent which we'll retrieve in the maze activity
                                    startActivity(game);
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                }
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

    @Override
    public void onClick(View v) {

    }
}
