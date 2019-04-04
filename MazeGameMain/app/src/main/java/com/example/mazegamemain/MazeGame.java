package com.example.mazegamemain;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;

/*
    This is where the magic happens
    The actual maze game is programmed here and called from mazegameactivity

 */

public class MazeGame extends Game
{
    int x = 100; // width of screen
    int y = 100; // length of screen
    int step = 1; // the length of one step

    int[] player = new int[3]; // contains players position (x,y) and number of hit in walls
    int[] player_next_pos = new int[2]; // contains players next position (x,y)
    int[][] maze = new int[x][y];

    @Override
    public void onGameStart()
    {
        super.onGameStart();

        // initialising the maze to all zeros (no walls)
        for(int i = 0; i < x; i++)
        {
            for (int j = 0; j < y; j++)
            {
                maze[i][j] = 0;
            }
        }
    }

    @Override
    public void onGameUpdate(byte[] message)
    {
        super.onGameUpdate(message);
        int event = AntData.getCommand(message);

    }

    @Override
    public void onGameEnd()
    {
        super.onGameEnd();

    }

    public void checkMazeEdge() // Laura
    {
        int i = 0;
        int dir_x =0;
        while (i < step)
        {
            if(maze[player_next_pos[0]-i][player_next_pos[1]-i] != 0) // if next position of player equals a wall, change next position
            {
                player_next_pos[0]-= step;
                player_next_pos[1]-= step;
                player[2] ++;
            }
            i++;
        }
        if(maze[player_next_pos[0]][player_next_pos[1]] != 0) // if next position of player equals a wall, change next position
        {
            player_next_pos[0]-= step;
            player_next_pos[1]-= step;
            player[2] ++;
        }
        movePlayer();
    }

    public void movePlayer() //
    {
        player[0] = player_next_pos[0];

    }
}
