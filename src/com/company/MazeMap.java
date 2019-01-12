package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class MazeMap {

    int[][] map;
    int mapXSize, mapYSize;
    int mapXOffset, mapYOffset;
    int XEndpoint, YEndpoint;
    int movementSpeed;

    int tileSize;
//    ImageIcon pathImg, wallImg;
    Player player;


    // Initialize
    public MazeMap(int xSize, int ySize, Player player){

        // Initialize basic vars that are needed of calculations
        this.mapXSize = xSize * 4 + 1;
        this.mapYSize = ySize * 2 + 1;
        this.tileSize = 64;
        this.player = player;
        this.movementSpeed = this.player.getSpeed();

        // these vars are here to keep track of the maze relative to the point of view of the camera
        mapXOffset = player.getXOffset() - tileSize; // x point of view
        mapYOffset = player.getYOffset() - tileSize; // y poi=nt of view

//        generation of the maze for the map
         map = new  MazeMaker(xSize, ySize).generateMaze();

         // calculating were the end point of the maze should be relative to the size of the maze
         XEndpoint = mapXSize - 2;
         YEndpoint = mapYSize - 2;
         map[XEndpoint][YEndpoint] = 2; // inputting the endpoint into the map
//
//        pathImg = new ImageIcon("C:/Users/LJ206478/eclipse-workspace/GraphicsFUN/src/img/grass.png");
//        wallImg = new ImageIcon("C:/Users/LJ206478/eclipse-workspace/GraphicsFUN/src/img/rock.png");
    }


// drawing the maze to the screen
    public void drawMap(Graphics g){

//        going through each cube in the array, finding if its a wall, walkable area or endpoint (one endpoint per map)
        for (int x = 0; x < mapXSize; x++){
            for (int y = 0; y < mapYSize; y++){

                if ( map[x][y] == 0){
                    // for when the cube/square is a walkable area (indicated by a cyan color)
                    g.setColor(Color.cyan);
                    // draws square relative to the point of view of the camera
                    g.fillRect(x * tileSize + mapXOffset,y  * tileSize + mapYOffset,tileSize,tileSize);

                } else if ( map[x][y] == 1){
                    // for when the cube/square is a wall (indicated by a black color, looks like the background)
                    g.setColor(Color.black);
                    // draws square relative to the point of view of the camera
                    g.fillRect(x * tileSize + mapXOffset,y  * tileSize + mapYOffset,tileSize,tileSize);

                } else if ( map[x][y] == 2){
                    // for when the cube/square is a endpoint (indicated by a red color)
                    g.setColor(Color.red);
                    // draws square relative to the point of view of the camera
                    g.fillRect(x * tileSize + mapXOffset,y  * tileSize + mapYOffset,tileSize,tileSize);
                }
            }
        }
    }

//    calculates the player movement and collision by moving the map around the player
    public void movePlayer(KeyEvent e){

        // setting up vars that help keep track of the players position relative to the map, moving around it
        int playerTop =  player.getYOffset();
        int playerBottom =  playerTop + player.getHeight();
        int playerLeft = player.getXOffset();
        int playerRight = playerLeft + player.getWidth();

        int key = e.getKeyCode(); // get the key code of the key that was typed

        // for when the player presses the left or "A"  keys to move the player left
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {

            // moves map right so player moves left
            mapXOffset += movementSpeed;

            // looping through all the cubes in the map, checking if its a wall and if the player colliding with it
            for (int x = 0; x < mapXSize; x++){
                for (int y = 0; y < mapYSize; y++) {

                    // setting up position vars for the relative cube relative to the camera's point of view
                    int cubeTop = y * tileSize + mapYOffset;
                    int cubeBottom = y * tileSize + tileSize + mapYOffset;
                    int cubeLeft = x * tileSize + mapXOffset;
                    int cubeRight = x * tileSize + tileSize + mapXOffset;

                    // Check if the right side of the player is colliding with the left side of the relative cube
                    if (map[x][y] == 1 && playerLeft < cubeRight && playerRight > cubeLeft){
                        if ((cubeTop < playerBottom && playerBottom <= cubeBottom) || (playerTop < cubeBottom && playerTop >= cubeTop)){

                            // if true, sets the right side of the player to the left side of the relative cube
                            mapXOffset = player.getXOffset() - ((x + 1) * tileSize);
                            }
                        }
                    }
                }
            }

        // for when the player presses the right or "D"  keys to move the player left
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {

            // moves map left so player moves right
            mapXOffset -= movementSpeed ;

            // looping through all the cubes in the map, checking if its a wall and if the player colliding with it
            for (int x = 0; x < mapXSize; x++){
                for (int y = 0; y < mapYSize; y++) {

                    // setting up position vars for the relative cube relative to the camera's point of view
                    int cubeTop = y * tileSize + mapYOffset;
                    int cubeBottom = y * tileSize + tileSize + mapYOffset;
                    int cubeLeft = x * tileSize + mapXOffset;
                    int cubeRight = x * tileSize + tileSize + mapXOffset;

                    // Check if the left side of the player is colliding with the right side of the relative cube
                    if (map[x][y] == 1 && playerRight > cubeLeft && playerLeft < cubeRight){
                        if ((cubeTop<playerBottom && playerBottom<=cubeBottom) || (playerTop<cubeBottom && playerTop >= cubeTop)){

                            // if true, sets the left side of the player to the right side of the relative cube
                            mapXOffset = player.getXOffset() - (x * tileSize) + player.getWidth();
                        }
                    }
                }
            }
        }

        // for when the player presses the up or "W"  keys to move the player left
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {

            // moves map down so player moves up
            mapYOffset += movementSpeed;

            // looping through all the cubes in the map, checking if its a wall and if the player colliding with it
            for (int x = 0; x < mapXSize; x++){
                for (int y = 0; y < mapYSize; y++) {

                    // setting up position vars for the relative cube relative to the camera's point of view
                    int cubeTop = y * tileSize + mapYOffset;
                    int cubeBottom = y * tileSize + tileSize + mapYOffset;
                    int cubeLeft = x * tileSize + mapXOffset;
                    int cubeRight = x * tileSize + tileSize + mapXOffset;

                    // Check if the top side of the player is colliding with the bottom side of the relative cube
                    if (map[x][y] == 1 && playerTop < cubeBottom && playerBottom > cubeTop){
                        if ((cubeLeft<playerRight && playerRight<=cubeRight) || (playerLeft<cubeRight && playerLeft >= cubeLeft)){

                            // if true, sets the top side of the player to the bottom side of the relative cube
                            mapYOffset = player.getYOffset() - ((y + 1) * tileSize);
                        }
                    }
                }
            }
        }

        // for when the player presses the down or "S"  keys to move the player left
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {

            // moves map up so player moves down
            mapYOffset -= movementSpeed;

            // looping through all the cubes in the map, checking if its a wall and if the player colliding with it
            for (int x = 0; x < mapXSize; x++){
                for (int y = 0; y < mapYSize; y++) {

                    // setting up position vars for the relative cube relative to the camera's point of view
                    int cubeTop = y * tileSize + mapYOffset;
                    int cubeBottom = y * tileSize + tileSize + mapYOffset;
                    int cubeLeft = x * tileSize + mapXOffset;
                    int cubeRight = x * tileSize + tileSize + mapXOffset;

                    // Check if the bottom side of the player is colliding with the top side of the relative cube
                    if (map[x][y] == 1 && playerBottom > cubeTop && playerTop < cubeBottom){
                        if ((cubeLeft < playerRight && playerRight <= cubeRight) || (playerLeft < cubeRight && playerLeft >= cubeLeft)){

                            // if true, sets the bottom side of the player to the top side of the relative cube
                            mapYOffset = player.getYOffset() - (y * tileSize) + player.getHeight();
                        }
                    }
                }
            }
        }
    }

    // give a boolean weather the player is in the endpoint of not
    public boolean  isAtEndpoint() {

        // setting up vars that help keep track of the players position relative to the map, moving around it
        int playerTop = player.getYOffset();
        int playerBottom = playerTop + player.getHeight();
        int playerLeft = player.getXOffset();
        int playerRight = playerLeft + player.getWidth();

        // setting x and y to the endpoints position
        int x = XEndpoint;
        int y = YEndpoint;

        // setting up position vars for the endpoint relative to the camera's point of view
        int cubeTop = y * tileSize + mapYOffset;
        int cubeBottom = y * tileSize + tileSize + mapYOffset;
        int cubeLeft = x * tileSize + mapXOffset;
        int cubeRight = x * tileSize + tileSize + mapXOffset;

        // Check if the right side of the player is colliding with the left side of the endpoint
        if (map[x][y] == 2 && playerLeft < cubeRight && playerRight > cubeLeft) {
            if ((cubeTop < playerBottom && playerBottom <= cubeBottom) || (playerTop < cubeBottom && playerTop >= cubeTop)) {

                // if yes colliding with endpoint
                return true;
            }
        }

        // Check if the left side of the player is colliding with the right side of the endpoint
        if (map[x][y] == 2 && playerRight > cubeLeft && playerLeft < cubeRight) {
            if ((cubeTop < playerBottom && playerBottom <= cubeBottom) || (playerTop < cubeBottom && playerTop >= cubeTop)) {

                // if yes colliding with endpoint
                return true;
            }
        }

        // Check if the bottom side of the player is colliding with the top side of the endpoint
        if (map[x][y] == 2 && playerTop < cubeBottom && playerBottom > cubeTop) {
            if ((cubeLeft < playerRight && playerRight <= cubeRight) || (playerLeft < cubeRight && playerLeft >= cubeLeft)) {

                // if yes colliding with endpoint
                return true;
            }
        }

        // Check if the top side of the player is colliding with the bottom side of the endpoint
        if (map[x][y] == 2 && playerBottom > cubeTop && playerTop < cubeBottom) {
            if ((cubeLeft < playerRight && playerRight <= cubeRight) || (playerLeft < cubeRight && playerLeft >= cubeLeft)) {

                // if yes colliding with endpoint
                return true;
            }
        }

        // if not colliding with endpoint
        return false;
    }
}

//    MY ALGORITHM THAT I MADE FOR CREATING A MAZE PATH BUT WAS VERY INCONSISTENT
//
//    void createPath(int positionX, int positionY){
//
//        map[positionX][positionY] = 1;
//
//        ArrayList<Integer[]> possiblePath = new ArrayList<Integer[]>();
//
////            1 up
//            try{
//                System.out.println("1 LEFT: " + map[positionX - 1][positionY]);
//
//                if ( map[positionX - 1][positionY] == 0) {
//                    possiblePath.add(new Integer[]{positionX - 1, positionY});
//                }
//
//            } catch (ArrayIndexOutOfBoundsException e){
//                System.out.println("1 LEFT: FALSE");
//            }
//
//
//
//        try{
//            System.out.println("1 RIGHT: " + map[positionX + 1][positionY]);
//
//            if ( map[positionX + 1][positionY] == 0) {
//                possiblePath.add(new Integer[]{positionX + 1, positionY});
//            }
//
//        } catch (ArrayIndexOutOfBoundsException e){
//            System.out.println("1 RIGHT: FALSE");
//        }
//
//
//
//        try{
//            System.out.println("1 UP: " + map[positionX][positionY - 1]);
//
//            if ( map[positionX][positionY - 1] == 0) {
//                possiblePath.add(new Integer[]{positionX, positionY - 1});
//            }
//
//        } catch (ArrayIndexOutOfBoundsException e){
//            System.out.println("1 UP: FALSE");
//        }
//
//
//
//        try{
//            System.out.println("1 DOWN: " + map[positionX][positionY + 1]);
//
//            if ( map[positionX][positionY + 1] == 0) {
//                possiblePath.add(new Integer[]{positionX, positionY + 1});
//            }
//
//
//        } catch (ArrayIndexOutOfBoundsException e){
//            System.out.println("1 DOWN: FALSE");
//        }
//
////        for (Integer[] possPaths: possiblePath) {
////            for (Integer[] wall : walls) {
////                if (possPaths[0] == wall[0] && possPaths[1] == wall[1]) {
////                    possiblePath.remove(possPaths);
////                }
////            }
////        }
//
//        if (possiblePath.size() > 0) {
//
////            try {
////                for(Integer[] possPaths : possiblePath) {
////                    for(Integer[] wall : walls) {
////                        if (possPaths[0] == wall[0] && possPaths[1] == wall[1]) {
////                            possiblePath.remove(possPaths);
////                        }
//////                    }
//////                }
////            } catch (ConcurrentModificationException | IllegalArgumentException e){
////
////            }
//
//            System.out.println("Number of possible titles: " + possiblePath.size());
//
//            Random rand = new Random();
//            int randIndex = rand.nextInt(possiblePath.size());
//
//
//            Integer[] newPath = possiblePath.get(randIndex );
//            possiblePath.remove(randIndex);
//
//            possiblePath.forEach((newWall) -> map[newPath[0]][newPath[1]] = 2);
//
//            createPath(newPath[0],newPath [1], walls);
//        }
//
//    }