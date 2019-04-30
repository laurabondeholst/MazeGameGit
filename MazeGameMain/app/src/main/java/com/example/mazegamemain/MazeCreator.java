package com.example.mazegamemain;
//this class creates mazes

public class MazeCreator {
    public static int[][] mazeNu;

    public static MazeGame adaptMaze(int mazeNo) {
        MazeGame maze = null;
        if (mazeNo == 1) {
            maze = new MazeGame();
            mazeNu = new int[][]{
                    {0, 0, 0, 0, 0, 0, 1, 0, 0, 2},
                    {0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
                    {0, 0, 0, 0, 1, 0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 1, 0, 1, 1, 1, 0},
                    {0, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                    {0, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                    {0, 0, 0, 3, 1, 0, 0, 0, 1, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 1}};

            maze.setLines(mazeNu);
            maze.setStartPosition(6, 3);
            maze.setFinalPosition(0, 9);

        }
        
        if (mazeNo == 2) {
            maze = new MazeGame();
            mazeNu = new int[][]{
                    {1, 1, 1, 0, 0, 0, 1, 0, 0, 2},
                    {1, 1, 1, 0, 1, 0, 1, 0, 1, 0},
                    {1, 0, 0, 0, 1, 0, 1, 0, 0, 0},
                    {1, 0, 1, 1, 1, 0, 1, 1, 1, 0},
                    {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                    {1, 0, 1, 1, 1, 1, 1, 0, 0, 0},
                    {1, 0, 1, 0, 0, 0, 1, 1, 0, 1},
                    {1, 0, 1, 0, 1, 0, 0, 0, 0, 0},
                    {1, 0, 1, 0, 1, 1, 1, 1, 1, 0},
                    {3, 0, 0, 0, 0, 0, 1, 0, 0, 0}};

            maze.setLines(mazeNu);
            maze.setStartPosition(9, 0);
            maze.setFinalPosition(0, 9);

        }

        if (mazeNo == 3) {
            maze = new MazeGame();
            mazeNu = new int[][]{
                    {3, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 2},
                    {1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1},
                    {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1},
                    {1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1},
                    {1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0},
                    {1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0},
                    {1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
                    {0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0},
                    {0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}};

            maze.setLines(mazeNu);
            maze.setStartPosition(0, 0);
            maze.setFinalPosition(0, 13);
        }
        return maze;
    }

}
