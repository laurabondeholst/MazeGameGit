package com.example.mazegamemain;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;
import com.livelife.motolibrary.OnAntEventListener;

import static com.livelife.motolibrary.AntData.EVENT_PRESS;
import static com.livelife.motolibrary.AntData.LED_COLOR_BLUE;
import static com.livelife.motolibrary.AntData.LED_COLOR_GREEN;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;
import static com.livelife.motolibrary.AntData.LED_COLOR_ORANGE;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;

public class MainActivity extends AppCompatActivity implements OnAntEventListener {
    //Michael

    MazeGame mazeGame;
    MotoConnection connection;
    MotoSound sound;
    boolean isPairing = false;
    public static byte SPRITE_COLOR = 3;

    //buttons
    Button button_pairing;
    Button button_calibrate;
    Button button_easy;
    Button button_medium;
    Button button_hard;
    Button button_colorRed;
    Button button_colorGreen;
    Button button_colorBlue;
    
    ArrayList<GameType> allGameTypes = new ArrayList<GameType>(); //Laura


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mazeGame = new MazeGame();

        // connecting to the tiles
        connection = MotoConnection.getInstance();
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

       allGameTypes = mazeGame.getGameTypes();
        
        //button for starting the game on easy difficulty
        button_easy = findViewById(R.id.button_easy);
        button_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, MazeGameActivity.class); //create an Intent to launch the game activity

                
                mazeGame.selectedGameType = allGameTypes.get(1); //Laura
                MazeCreator.adaptMaze(1);

                sound.playStart();
                connection.unregisterListener(MainActivity.this);


                startActivity(i);


            }
        });

        //button for starting the game on medium difficulty
        button_medium = findViewById(R.id.button_medium);
        button_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MazeGameActivity = new Intent(MainActivity.this, MazeGameActivity.class); //create an Intent to launch the game activity

                mazeGame.selectedGameType = allGameTypes.get(2);  
                MazeCreator.adaptMaze(2);                

                sound.playStart();
                startActivity(MazeGameActivity);

            }
        });

        //button for starting the game on hard difficulty
        button_hard = findViewById(R.id.button_hard);
        button_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MazeGameActivity = new Intent(MainActivity.this, MazeGameActivity.class); //create an Intent to launch the game activity

                mazeGame.selectedGameType = allGameTypes.get(3);
                MazeCreator.adaptMaze(3);
                
                sound.playStart();
                startActivity(MazeGameActivity);

            }
        });
    }

        /*//start game and exit game button
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





    }*/

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
