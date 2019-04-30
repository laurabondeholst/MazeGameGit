package com.example.mazegamemain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static com.livelife.motolibrary.AntData.EVENT_PRESS;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;
import com.livelife.motolibrary.OnAntEventListener;

/*
    This draws the maze and traces movement of the ball

 */

public class MazeGameActivity extends AppCompatActivity implements OnAntEventListener {





    MotoConnection connection = MotoConnection.getInstance();
    MotoSound sound = MotoSound.getInstance();
    MazeGame mazeGame;

    LinearLayout gameTypeContainer;

    GameView gameView;

    private int width, height, lineWidth; //width and height of the whole maze and width of blocks
    private int mazeSizeX, mazeSizeY; //size of the maze, number of blocks in it
    float blockWidth, blockHeight; //width and height of cells in the maze
    float totalBlockWidth, totalBlockHeight; //store result of final width and height of blocks respectively

    float maze_to_screen_x,maze_to_screen_y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mazeGame = new MazeGame();

        connection.registerListener(MazeGameActivity.this);

        MazeCreator.adaptMaze(1);


        maze_to_screen_x = 1500f/10f;
        maze_to_screen_y = 1500f/10f;

        int hej = 0;
        for(final GameType gt: mazeGame.getGameTypes())
        {
            if (hej == 1)
            {
                mazeGame.selectedGameType = gt;
                sound.playStart();
                mazeGame.startGame();
            }
            hej ++;
        }

        if (1 == 1) // should compare the chosen gametype to the various avaible
        {
            MazeCreator.adaptMaze(1);
        }

        mazeSizeX = mazeGame.getMazeWidth();
        mazeSizeY = mazeGame.getMazeHeight();

        maze_to_screen_x = 1500f/mazeSizeX;
        maze_to_screen_y = 1500f/mazeSizeY;

        gameView=new GameView(MazeGameActivity.this);

        setContentView(gameView);

        mazeGame.setOnGameEventListener(new Game.OnGameEventListener() {
            @Override
            public void onGameTimerEvent(int i) {

            }

            @Override
            public void onGameScoreEvent(int i, int i1) {
                //gameView.drawSprite();

            }

            @Override
            public void onGameStopEvent() {

            }

            @Override
            public void onSetupMessage(String s) {

            }

            @Override
            public void onGameMessage(String s) {

            }

            @Override
            public void onSetupEnd() {

            }
        });


    }


    @Override
    public void onMessageReceived(byte[] bytes, long l) {
        mazeGame.addEvent(bytes); // forwarding events
    }

    @Override
    public void onAntServiceConnected() {

    }

    @Override
    public void onNumbersOfTilesConnected(int i) {

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        connection.unregisterListener(this);
    }


    //here writes movement of the ball
   /*

        if (traced){ //as the ball moved, we have to redraw the maze
            invalidate();
            if (maze.checkGoalReached()){ // if reach the goal, show finish window
                AlertDialog.Builder builder = new AlertDialog.Builder(context); //show texts and press button
                builder.setTitle(context.getText(R.string.finished_title)); //show finish texts
                LayoutInflater inflater = context.getLayoutInflater();
                View view = inflater.inflate(R.layouut.finish, null);
                builder.setView(view);
                View exitGame = view.findViewById(R.id.exitGame);
                exitGame.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View clicked) {
                        if (clicked.getId()==R.id.exitGame){
                            context.finish();
                        }
                    }
                });
                AlertDialog finishDialog = builder.create();
                finishDialog.show();

            }
        }
        return true;
    }
    */


    class GameView extends View {
        private int mazeFinalX, mazeFinalY; //the finishing point of the maze
        //private MazeGame maze;
        private Activity context;
        private Paint block, text, background;
        private Canvas canvas_game;

        public GameView(Context context) {
            super(context);
            mazeFinalX = mazeGame.getFinalX();
            mazeFinalY = mazeGame.getFinalY();
            mazeSizeX = mazeGame.getMazeWidth();
            mazeSizeY = mazeGame.getMazeHeight();
            block = new Paint();
            block.setColor(Color.BLACK); //color of the maze (blocks)
            text = new Paint();
            text.setColor(Color.RED); //color of the finishing indicator
            background = new Paint();
            background.setColor(Color.WHITE); //color of the background
        }

 
        @Override
        protected  void onDraw(Canvas canvas) { // Yichen
            //super.onDraw(canvas);
            canvas_game = canvas;
            canvas_game.drawRect(0, 0, width, height, background); //fill in the background
            

            //iterate over the blocks to draw the mze
            int [][] mazeNu = MazeCreator.mazeNu;
            for (int i = 0; i < mazeSizeX; i++) {
                for (int j = 0; j < mazeSizeY; j++) {
                    if(mazeNu[i][j]  == 1)
                    {
                        line.setStyle(Paint.Style.FILL);
                        canvas_game.drawRect((float)j*totalBlockWidth, (float)i*totalBlockHeight, (float)(j*totalBlockWidth+totalBlockWidth), (float)(i*totalBlockHeight+totalBlockHeight), block);
                        // draw black squares when the value is 1
                    }
                }
            }

            //draw a text at the finishing point
            canvas_game.drawText("F",
                    (mazeFinalY * totalBlockWidth) + (blockWidth * 0.25f),
                    (mazeFinalX * totalBlockHeight) + (blockHeight * 0.75f),
                    text);

        }
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) { // Yichen, calculations make the maze expnds to the full screen
            width = (w < h)?w:h;
            height = width;         //for now square mazes
            lineWidth = 1;          //for now 1 pixel wide walls
            blockWidth = (width - ((float)mazeSizeX*lineWidth)) / mazeSizeX;
            totalBlockWidth = blockWidth+lineWidth; //the final width of the block
            blockHeight = (height - ((float)mazeSizeY*lineWidth)) / mazeSizeY;
            totalBlockHeight = blockHeight+lineWidth;
            text.setTextSize(blockHeight*0.75f);
            super.onSizeChanged(w, h, oldw, oldh);
        }


    }




}

