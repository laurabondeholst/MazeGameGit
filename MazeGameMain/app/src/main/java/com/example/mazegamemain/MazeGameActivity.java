package com.example.mazegamemain;
// comment

import android.animation.ObjectAnimator;
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

    ImageView spriteImage;

    private int width, height, lineWidth; //width and height of the whole maze and width of lines which make the walls
    private int mazeSizeX, mazeSizeY; //size of the maze i.e. number of cells in it
    float cellWidth, cellHeight; //width and height of cells in the maze
    float totalCellWidth, totalCellHeight; //store result of cellWidth+lineWidth and cellHeight+lineWidth respectively

    int animation_duration;
    float maze_to_screen_x,maze_to_screen_y;
    
    int colorARGB = Color.parseColor("#ff669900");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze_game);

        mazeGame = new MazeGame();

        connection.registerListener(MazeGameActivity.this);

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

        initAnimator();


        gameView=new GameView(MazeGameActivity.this);

        //setContentView(gameView);



        mazeGame.setOnGameEventListener(new Game.OnGameEventListener() {
            @Override
            public void onGameTimerEvent(int i) {

            }

            @Override
            public void onGameScoreEvent(int i, int i1) {
                moveSprite();
                Canvas canvas;

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

   public void moveSprite() // Laura and Michael
    {
        if(mazeGame.player[0]!=mazeGame.player_old_pos[0]) // if x pos is changed
        {
            ObjectAnimator animator =ObjectAnimator.ofFloat(spriteImage,"x",mazeGame.player[0]*totalCellWidth);
            animator.setDuration(animation_duration).start();
        }
        else // if y pos is changed
        {
            ObjectAnimator animator =ObjectAnimator.ofFloat(spriteImage,"y",mazeGame.player[1]*totalCellHeight);
            animator.setDuration(animation_duration).start();
        }

    }

    public void initAnimator() // LAura and Michael
    {
        spriteImage = findViewById(R.id.spriteImage);

        if(MainActivity.SPRITE_COLOR == 1) {
            colorARGB = Color.parseColor("#ffcc0000");
        }
        else if(MainActivity.SPRITE_COLOR == 2) {
            colorARGB = Color.parseColor("#ff0099cc");
        }
        else if(MainActivity.SPRITE_COLOR == 3) {
            colorARGB = Color.parseColor("#ff669900");
        }
        spriteImage.setColorFilter(colorARGB, PorterDuff.Mode.SRC_ATOP);

        animation_duration = 200;

        spriteImage.setX((float)(mazeGame.getCurrentY())*totalCellWidth);
        spriteImage.setY((float)mazeGame.getCurrentX()*totalCellWidth);

    }
    



    class GameView extends View {
        private int mazeFinishX, mazeFinishY; //the finishing point of the maze
        //private MazeGame maze;
        private Activity context;
        private Paint line, red, background;
        private Canvas canvas_og;

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



        @Override
        protected  void onDraw(Canvas canvas) { // Yichen
            canvas_og = canvas;
            canvas_og.drawRect(0, 0, width, height, background); //fill in the background
            //boolean[][] hLines = maze.getHorizontalLines();
            //boolean[][] vLines = maze.getVerticalLines();


            //iterate over the boolean arrays to draw walls
            int [][] mazeNu = mazeGame.getLines();
            for (int i = 0; i < mazeSizeX; i++) {
                for (int j = 0; j < mazeSizeY; j++) {
                    if(mazeNu[i][j]  == 1)
                    {
                        line.setStyle(Paint.Style.FILL);
                        canvas_og.drawRect(i, j, i+1, j+1, line);
                        // draw square;
                    }
                }
            }
            int currentX = mazeGame.getCurrentX();
            int currentY = mazeGame.getCurrentY();
            //draw the ball

            canvas_og.drawCircle((currentX * totalCellWidth) + (cellWidth / 2),   //x of center
                    (currentY * totalCellHeight) + (cellWidth / 2),  //y of center
                    (cellWidth * 0.45f),                           //radius
                    red);
            //draw the finishing point indicator
            canvas_og.drawText("GOAL",
                    (mazeFinishX * totalCellWidth) + (cellWidth * 0.25f),
                    (mazeFinishY * totalCellHeight) + (cellHeight * 0.75f),
                    red);

        }
        @Override
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
        public void drawMaze(int[][] maze)
        {
            for (int i = 0; i < mazeSizeX; i++) {
                for (int j = 0; j < mazeSizeY; j++) {
                    if (maze[i][j] == 1) {
                        line.setStyle(Paint.Style.FILL);
                        canvas_og.drawRect(i, j, i + 1, j + 1, line);
                        // draw square;
                    }
                }
            }

        }

    }
}
