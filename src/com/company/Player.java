package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {

//    private ImageIcon playerImg;
    private int XOffset, YOffset;
    private int height, width, speed;

    // Initialize
    public Player(Dimension frameSize){

        // Initialize basic vars that are needed of calculations
        height = 50;
        width = 50;
        speed = 12;

        // Setting up the player being in the middle of the screen
        XOffset = (int)frameSize.getWidth() / 2 - width / 2;
        YOffset = (int)frameSize.getHeight() / 2 - height / 2;
    }

    // method to draw the play object
    public void paintPlayer(Graphics g){
//        playerImg.paintIcon(comp,g,mapXOffset,mapYOffset);
        // draw the player in the middle of the window in a white color
        g.setColor(Color.white);
        g.fillOval(XOffset, YOffset, width, height);
    }


//    << returning Value functions >>

    public int getSpeed() {
        return speed;
    }

    public int getXOffset() {
        return XOffset;
    }

    public int getYOffset() {
        return YOffset;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}