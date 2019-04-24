package com.example.mazegamemain;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;

import static com.livelife.motolibrary.AntData.EVENT_PRESS;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;

/*
    This is where the magic happens
    The actual maze game is programmed here and called from mazegameactivity

 */


public class MazeGame extends Game
{
    static byte DIR_DOWN = 0;
    static byte DIR_UP = 1;
    static byte DIR_RIGHT = 2;
    static byte DIR_LEFT = 3;
    static byte SPRITE_COLOR = 3;

    int x; // width of screen
    int y; // length of screen
    int step = 1; // the length of one step

    public int getMazeWidth(){
        return x;
    }

    public int getMazeHeight(){
        return y;
    }


    int[] player = new int[3]; // contains players position (x,y) and number of hit in walls
    int[] player_next_pos = new int[2]; // contains players next position (x,y)
    int[][] maze = new int[x][y]; // 0 means empty field, 1 means wall, 2 means goal, 3 means start pos

    int finalX, finalY; // stores the finishing of maze
    boolean checkGoalReached;


    MotoConnection connection = MotoConnection.getInstance();
    MotoSound sound = MotoSound.getInstance();


    MazeGame()  // Laura
    {
        setName("MAZE - The Game");
        setDescription("Escape the maze! Move your sprite via the tiles and make your way out!");

        // change the gametypes to easy, medium and hard with different mazes, when the program needs to function
        GameType gt = new GameType(1, GameType.GAME_TYPE_SPEED,1, "No time restriction",1);
        addGameType(gt);

        GameType gt2 = new GameType(1, GameType.GAME_TYPE_TIME,60, "Time limit: 60 sec",1);
        addGameType(gt2);

    }

    @Override
    public void onGameStart()
    {
        super.onGameStart();

        connection.setAllTilesIdle(LED_COLOR_OFF);
        sound.playStart();

        initView();

        adaptMaze(1);

        displayMaze();
        initSprite();
    }

    @Override
    public void onGameUpdate(byte[] message)
    {
        super.onGameUpdate(message);
        int event = AntData.getCommand(message);

        if(event == EVENT_PRESS)
        {
            int tile_id = AntData.getId(message);
            connection.setTileColor(SPRITE_COLOR,tile_id);
            trackDirection(tile_id);
            if(checkGameEdge() && checkMazeEdge() )
            {
                if (checkGoalReached())
                {
                    onGameEnd();
                }
                updatePlayerSprite();
            }
        }

    }

    @Override
    public void onGameEnd()
    {
        super.onGameEnd();

    }

    public boolean checkMazeEdge() // Laura, returns false if player hits maze edge
    {                           // this method assumes that player_next_pos is within boundaries
        int i = 0;

        int dir_x = player_next_pos[0]-player[0];
        int dir_y = player_next_pos[1]-player[1];

        //finding the direction as 1 (up/right) or -1 (down/left)
        dir_x = (int) java.lang.Math.signum(dir_x);
        dir_y = (int) java.lang.Math.signum(dir_y);

        while (i < step) // for each number in step, check for wall
        {
            if(maze[player_next_pos[0]-i*dir_x][player_next_pos[1]-i*dir_y] == 1) // if next position of player equals a wall, change next position
            {
                // if player hit a wall, go back to original pos
                player_next_pos[0] = player[0];
                player_next_pos[1] = player[1];
                player[2] ++;
                sound.playError();
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean checkGameEdge() // Laura, returns false if the player hits the game edge
    {
        if(player_next_pos[0] < 0 || player_next_pos[0] >= x || player_next_pos[1] < 0 || player_next_pos[1] >= y)
        {
            player[2] ++;
            sound.playError();
            return true;
        }

        return true;
    }


    public boolean trackDirection(int tile_id) // Laura, what a hardcore method wauw
    {
        boolean tracked = false;
        if(tile_id == DIR_UP)
        {
            player_next_pos[0]++;
            tracked = true;
        }
        if(tile_id == DIR_DOWN)
        {
            player_next_pos[0]--;
            tracked = true;
        }
        if(tile_id == DIR_LEFT)
        {
            player_next_pos[1]--;
            tracked = true;
        }
        if(tile_id == DIR_RIGHT)
        {
            player_next_pos[1]++;
            tracked = true;
        }
        if (tracked)
        {
            if(maze[player_next_pos[0]][player_next_pos[1]] == 2)
            {
                sound.playStart();
                checkGoalReached = true;
            }
        }
        return tracked;
    }

    public boolean checkGoalReached() // Laura, if goal reached, return true
    {
        return checkGoalReached;
    }

    public void initView() // Yichen, initialising the maze into array
    {

    }

    private void displayMaze() // Yichen
    {
        // when displaying the maze, please be aware of x/y orientation.
    }

    public void initZeros() // Laura
    {
        // initialising the maze to all zeros (no walls)
        for(int i = 0; i < x; i++)
        {
            for (int j = 0; j < y; j++)
            {
                maze[i][j] = 0;
            }
        }

    }


    public void updatePlayerSprite()  // Yichen
    {
        player[0] = player_next_pos[0];
        player[1] = player_next_pos[1];
    }

    public void initSprite()
    {
        for(int i = 0; i < x; i++)
        {
            for (int j = 0; j < y; j++)
            {
                if (maze[i][j] == 3)
                {
                    player_next_pos[0] = i;
                    player_next_pos[1] = j;
                    updatePlayerSprite();
                    return;
                }
            }
        }
    }


    public void adaptMaze(int number)
    {
        if(number == 1 ) {
//            int[][] maze1 = {
//                    {0, 0, 0, 0, 0, 0, 1, 0, 0, 2},
//                    {0, 0, 0, 0, 0, 0, 1, 0, 1, 1},
//                    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
//                    {0, 0, 0, 0, 0, 0, 1, 1, 1, 0},
//                    {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
//                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                    {0, 0, 0, 3, 0, 0, 0, 0, 0, 0},
//                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
            MazeGame maze1 = MazeCreator.adaptMaze(1);
        }
        if (number == 2)
        {
//            int[][] maze2 = {
//                    {1, 0, 1, 0, 0, 0, 1, 0, 0, 2},
//                    {1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
//                    {1, 0, 0, 0, 1, 0, 1, 0, 0, 0},
//                    {1, 0, 1, 1, 1, 0, 1, 1, 1, 0},
//                    {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
//                    {1, 0, 1, 1, 1, 1, 1, 0, 1, 0},
//                    {1, 0, 0, 3, 0, 0, 1, 0, 0, 0},
//                    {1, 1, 1, 0, 1, 0, 1, 1, 1, 1},
//                    {1, 0, 1, 0, 1, 0, 0, 0, 0, 0},
//                    {1, 0, 1, 0, 1, 1, 1, 1, 1, 0},
//                    {1, 0, 0, 0, 0, 0, 1, 0, 0, 0}};
   //        maze = maze2;
            MazeGame maze2 = MazeCreator.adaptMaze(2);
        }
        if (number == 3)
        {
            MazeGame maze3 = MazeCreator.adaptMaze(3);
        }
    }
    
     public int getFinalX(){ // Yichen
        return finalX;
    }

    public int getFinalY(){// Yichen
        return finalY;
    }

    public int getCurrentX(){// Yichen
        return player[0];
    }

    public int getCurrentY(){// Yichen
        return player[1];
    }

    public void setStartPosition(int i, int j){// Yichen
        player[0] = i;
        player[1] = j;
        maze[i][j] = 3;
    }

    public void setFinalPosition(int i, int j){// Yichen
        finalX = i;
        finalY = j;
        maze[i][j] = 2;
    }

    public void setLines(int[][] lines){
        maze = lines;
    }

    public int[][] getLines(){
        return maze;
    }

}
