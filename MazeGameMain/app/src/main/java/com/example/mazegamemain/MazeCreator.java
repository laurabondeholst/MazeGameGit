package com.example.mazegamemain;
//this class creates mazes

public class MazeCreator {
    public static int[][] mazeNu;

    public static MazeGame adaptMaze(int mazeNo) {
        MazeGame maze = null;
        //int[][] mazeNu = new int[][];
        if (mazeNo == 1) {
            maze = new MazeGame();
            mazeNu = new int[][]{
                    {0, 0, 0, 0, 0, 0, 1, 0, 0, 2},
                    {0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
                    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 1, 1, 1, 1, 1, 0},
                    {0, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 3, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

//            boolean[][] vLines = new boolean[][]{
//                    {true ,false,false,false,true ,false,false},
//                    {true ,false,false,true ,false,true ,true },
//                    {false,true ,false,false,true ,false,false},
//                    {false,true ,true ,false,false,false,true },
//                    {true ,false,false,false,true ,true ,false},
//                    {false,true ,false,false,true ,false,false},
//                    {false,true ,true ,true ,true ,true ,false},
//                    {false,false,false,true ,false,false,false}
//            };
//            boolean[][] hLines = new boolean[][]{
//                    {false,false,true ,true ,false,false,true ,false},
//                    {false,false,true ,true ,false,true ,false,false},
//                    {true ,true ,false,true ,true ,false,true ,true },
//                    {false,false,true ,false,true ,true ,false,false},
//                    {false,true ,true ,true ,true ,false,true ,true },
//                    {true ,false,false,true ,false,false,true ,false},
//                    {false,true ,false,false,false,true ,false,true }
        }
        // maze.setVerticalLines(vLines);
        //maze.setHorizontalLines(hLines);
        maze.setLines(mazeNu);
        maze.setStartPosition(6, 3);
        maze.setFinalPosition(0, 9);

        if (mazeNo == 2) {
            maze = new MazeGame();
            mazeNu = new int[][]{
                    {1, 0, 1, 0, 0, 0, 1, 0, 0, 2},
                    {1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                    {1, 0, 0, 0, 1, 0, 1, 0, 0, 0},
                    {1, 0, 1, 1, 1, 0, 1, 1, 1, 0},
                    {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                    {1, 0, 1, 1, 1, 1, 1, 0, 0, 0},
                    {1, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                    {1, 1, 1, 0, 1, 0, 1, 1, 0, 1},
                    {1, 0, 1, 0, 1, 0, 0, 0, 0, 0},
                    {3, 0, 0, 0, 0, 0, 1, 0, 0, 0}};

//            boolean[][] vLines = new boolean[][]{
//                    {false,false,false,true ,false,false,false},
//                    {false,false,true ,false,true ,false,false},
//                    {false,false,true ,true ,false,false,false},
//                    {false,false,true ,true ,true ,false,false},
//                    {false,false,true ,false,true ,false,false},
//                    {true ,false,false,true ,false,true ,false},
//                    {true ,false,true ,true ,false,false,false},
//                    {false,false,true ,false,false,false,true }
//            };
//            boolean[][] hLines = new boolean[][]{
//                    {false,true ,true ,true ,false,true ,true ,true },
//                    {true ,true ,false,false,true ,true ,true ,false},
//                    {false,true ,true ,false,false,false,true ,true },
//                    {true ,true ,false,false,false,true ,true ,false},
//                    {false,true ,true ,true ,true ,false,true ,false},
//                    {false,false,true ,false,false,true ,true ,true },
//                    {false,true ,false,false,true ,true ,false,false}
//            };
        }
        // maze.setVerticalLines(vLines);
        //maze.setHorizontalLines(hLines);
        maze.setLines(mazeNu);
        maze.setStartPosition(10, 0);
        maze.setFinalPosition(0, 10);

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

//           boolean[][] vLines = new boolean[][]{
//                    {false,false,true ,false,false,false,true ,false,false,false,false,false},
//                    {false,true ,false,false,false,true ,false,false,false,false,true ,true },
//                    {true ,false,false,false,false,true ,false,false,false,false,true ,true },
//                    {true ,true ,false,false,false,true ,true ,true ,false,false,true ,true },
//                    {true ,true ,true ,false,false,true ,true ,false,true ,false,true ,true },
//                    {false,true ,true ,true ,false,true ,false,false,false,true ,false,false},
//                    {false,false,false,true ,false,true ,false,true ,false,false,false,false},
//                    {false,false,true ,false,true ,false,true ,true ,false,true ,false,false},
//                    {true ,true ,true ,true ,false,true ,true ,false,false,true ,false,false},
//                    {false,false,false,true ,false,false,true ,true ,false,true ,true ,false},
//                    {false,false,true ,false,true ,false,true ,false,false,false,false,false},
//                    {true ,true ,true ,true ,true ,true ,true ,false,false,true ,false,false},
//                    {false,false,true ,false,false,true ,false,false,false,false,true ,false}
//            };
//            boolean[][] hLines = new boolean[][]{
//                    {true ,false,false,true ,true ,false,false,false,true ,true ,true ,true ,false},
//                    {false,true ,true ,true ,true ,true ,true ,true ,true ,true ,false,false,false},
//                    {false,false,true ,true ,true ,false,false,true ,true ,true ,true ,false,false},
//                    {false,false,false,true ,true ,true ,false,false,false,true ,false,false,false},
//                    {false,false,false,false,true ,false,false,true ,true ,true ,false,false,false},
//                    {true ,true ,false,false,false,true ,true ,true ,true ,false,true ,true ,true },
//                    {false,true ,true ,true ,true ,true ,false,false,false,true ,true ,true ,false},
//                    {true ,false,false,false,true ,false,true ,false,true ,false,false,true ,true },
//                    {false,true ,false,false,false,true ,false,true ,true ,true ,true ,true ,false},
//                    {true ,true ,false,true ,false,true ,true ,false,false,true ,false,true ,false},
//                    {false,true ,true ,false,true ,false,false,true ,true ,false,true ,true ,true },
//                    {false,true ,false,false,true ,false,false,true ,true ,true ,false,false,true }
//            };
//          maze.setVerticalLines(vLines);
            //maze.setHorizontalLines(hLines);
            maze.setLines(mazeNu);
            maze.setStartPosition(0, 0);
            maze.setFinalPosition(0, 13);
        }
        return maze;
    }

}
