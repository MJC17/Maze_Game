package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//              Name: Marcus J Cameron (mc17)
//              Date: 2018-12-20 09:15
//              Project Name: MazeAssignment
//              Course: ISC3U1
//              About:

public class MazeGame extends JPanel implements KeyListener {

    public static MazeGame game;
    JFrame frame;
    Player player;
    MazeMap map;
    int mapXSize, mapYSize;

    public static void main(String[] args){
        game = new MazeGame(); // starting the game by Initialize the game object
    }

    // Initialize for the game object
    public MazeGame() {

        // initializing the window that the game will be in
        frame = new JFrame();
        frame.setTitle("Maze Game");
        frame.setSize(1400,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(this); // adding the game into the window

        // adding a key listener to the window to get input data from the keyboard
        addKeyListener(this);
        // setting the window to be in focus
        setFocusable(true);

        // setting the size of the map
        mapXSize = 10;
        mapYSize = 5;

        player = new Player(frame.getSize());
        map = new MazeMap(mapXSize,mapYSize, player);

        this.setBackground(Color.black); // setting the background color of the window
    }

    // when the player reaches the endpoint of the map, the game will create a hard level
    void resetGame(){
        // increasing the size of the new map so the map gets hard each level
        mapXSize += 5;
        mapYSize += 5;
        // creating the new and harder map
        map = new MazeMap(mapXSize,mapYSize, player);
    }

    // drawing the object in the window
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // drawing the map in the window
        map.drawMap(g);
        // drawing the player in the window, over top of the map
        player.paintPlayer(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // passes through the keys the are pressed
    @Override
    public void keyPressed(KeyEvent e) {
        // moves the player in the map when keys are pressed
        map.movePlayer(e);
        // redraws the objects in the window
        repaint();

        // when the player reachs the enters the endpoint the game creates a new map
        if (map.isAtEndpoint()){ resetGame(); }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }



}