package com.example.mazegamemain;
// comment

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

    private int width, height, lineWidth; //width and height of the whole maze and width of lines which make the walls
    private int mazeSizeX, mazeSizeY; //size of the maze i.e. number of cells in it
    float cellWidth, cellHeight; //width and height of cells in the maze
    float totalCellWidth, totalCellHeight; //store result of cellWidth+lineWidth and cellHeight+lineWidth respectively


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maze_game); // will be overwritten anyway

        mazeGame = new MazeGame();

        connection.registerListener(MazeGameActivity.this);

        gameView=new GameView(MazeGameActivity.this);

        //gameView.setBackgroundColor(Color.RED);
        setContentView(gameView);

        mazeGame.setOnGameEventListener(new Game.OnGameEventListener() {
            @Override
            public void onGameTimerEvent(int i) {

            }

            @Override
            public void onGameScoreEvent(int i, int i1) {

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

       // createGameButtons();
    }


    public void createGameButtons()
    {
        gameTypeContainer = findViewById(R.id.gameTypeContainer);
        if(gameTypeContainer.getChildCount() > 0)
        {
            gameTypeContainer.removeAllViews();
        }
        for(final GameType gt: mazeGame.getGameTypes())
        {
            Button b = new Button(this);
            b.setOnClickListener(new View.OnClickListener()
                                 { // this action creates a function for the button, and defineres the gt for this button
                                     @Override
                                     public void onClick(View view)
                                     {
                                         mazeGame.selectedGameType = gt;
                                         sound.playStart();
                                         mazeGame.startGame();
                                         // this way we start a game, but now the game is a new listener.
                                     }
                                 }
            );
            b.setText(gt.getName());
            gameTypeContainer.addView(b);
        }
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
    @Override
    public boolean press(int tile_id){ //when press tiles, the ball moves
        boolean traced = false;
            switch (tile_id) {
                case tile_id == UP: // michael, define each direction
                    traced = maze.trackDirection(MazeGame.DIR_UP);
                    break;
                case tile_id == DOWN:
                    traced = maze.trackDirection(MazeGame.DIR_DOWN);
                    break;
                case tile_id == LEFT:
                    traced = maze.trackDirection(MazeGame.DIR_LEFT);
                    break;
                case tile_id == RIGHT:
                    traced = maze.trackDirection(MazeGame.DIR_RIGHT);
                    break;
            }

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
        private int mazeFinishX, mazeFinishY; //the finishing point of the maze
        //private MazeGame maze;
        private Activity context;
        private Paint line, red, background;

        public GameView(Context context) {
            super(context);

            mazeFinishX = mazeGame.getFinalX();
            mazeFinishY = mazeGame.getFinalY();
            mazeSizeX = mazeGame.getMazeWidth();
            mazeSizeY = mazeGame.getMazeHeight();
            line = new Paint();
            line.setColor(Color.BLACK);
            line.setStrokeWidth(1);
            red = new Paint();
            red.setColor(Color.RED);
            //red.setColor(getResources().getColor(R.color.position));
            background = new Paint();
            background.setColor(Color.WHITE);
            //background.setColor(getResources().getColor(R.color.game_bg));


        }

        // Yichen
        //super(context);
        //this.context = (Activity)context;
        //this.maze = maze;




        protected void onDraw(Canvas canvas) { // Yichen

            canvas.drawRect(0, 0, width, height, background); //fill in the background
            //boolean[][] hLines = maze.getHorizontalLines();
            //boolean[][] vLines = maze.getVerticalLines();
            //iterate over the boolean arrays to draw walls
            int [][] mazeNu = mazeGame.getLines();
            for (int i = 0; i < mazeSizeX; i++) {
                for (int j = 0; j < mazeSizeY; j++) {
                    if(mazeNu[i][j]  == 1)
                    {
                        line.setStyle(Paint.Style.FILL);
                        canvas.drawRect(i, j, i+1, j+1, line);
                        // draw square;
                    }
                    /* // this is already blank
                    else if (mazeNu[i][j] == 0)
                    {
                        background.setStyle(Paint.Style.FILL);
                        //canvas.drawRect(i, j, i+1, j+1, background);
                        // draw space;
                    }
                    else if (mazeNu[i][j] == 3) //start point
                    {
                        background.setStyle(Paint.Style.FILL);
                        //canvas.drawRect(i, j, i+1, j+1, background);
                        // draw space;
                    }*/

                    //                float x = j * totalCellWidth;
                    //                float y = i * totalCellHeight;
                    //                if (j < mazeSizeX - 1 && vLines[i][j]) {
                    //                    //we'll draw a vertical line
                    //                    canvas.drawLine(x + cellWidth,   //start X
                    //                            y,               //start Y
                    //                            x + cellWidth,   //stop X
                    //                            y + cellHeight,  //stop Y
                    //                            line);
                    //                }
                    //                if (i < mazeSizeY - 1 && hLines[i][j]) {
                    //                    //we'll draw a horizontal line
                    //                    canvas.drawLine(x,               //startX
                    //                            y + cellHeight,  //startY
                    //                            x + cellWidth,   //stopX
                    //                            y + cellHeight,  //stopY
                    //                            line);
                    //                }
                    //
                }
            }
            int currentX = mazeGame.getCurrentX();
            int currentY = mazeGame.getCurrentY();
            //draw the ball
            canvas.drawCircle((currentX * totalCellWidth) + (cellWidth / 2),   //x of center
                    (currentY * totalCellHeight) + (cellWidth / 2),  //y of center
                    (cellWidth * 0.45f),                           //radius
                    red);
            //draw the finishing point indicator
            canvas.drawText("GOAL",
                    (mazeFinishX * totalCellWidth) + (cellWidth * 0.25f),
                    (mazeFinishY * totalCellHeight) + (cellHeight * 0.75f),
                    red);
        }



        protected void onSizeChanged(int w, int h, int oldw, int oldh) { // Yichen
            width = (w < h)?w:h;
            height = width;         //for now square mazes
            lineWidth = 1;          //for now 1 pixel wide walls
            cellWidth = (width - ((float)mazeSizeX*lineWidth)) / mazeSizeX;
            totalCellWidth = cellWidth+lineWidth;
            cellHeight = (height - ((float)mazeSizeY*lineWidth)) / mazeSizeY;
            totalCellHeight = cellHeight+lineWidth;
            red.setTextSize(cellHeight*0.75f);
            super.onSizeChanged(w, h, oldw, oldh);
        }
    }

}


// the maze is probably in the corner atm
