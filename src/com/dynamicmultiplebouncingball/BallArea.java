package com.dynamicmultiplebouncingball;

import java.awt.Color;
import java.awt.Graphics;

public class BallArea {
    
    int minX;
    int minY;
    int maxX;
    int maxY;
    private Color filledColor;
    private Color borderColor;

    public BallArea(int x, int y, int width, int height, Color filledColor, Color bordeColor) {
        this.minX = x;
        this.minY = y;
        this.maxX = x + width - 1;
        this.maxY = y + height - 1;
        this.filledColor = filledColor;
        this.borderColor = bordeColor;
    }
    
    public void set(int x, int y, int width, int height) {
        this.minX = x;
        this.minY = y;
        this.maxX = x + width - 1;
        this.maxY = y + height - 1;
    }

    public void draw(Graphics g) {
        g.setColor(filledColor);
        g.fillRect(minX, minY, maxX-minX-1, maxY-minY-1);
        g.setColor(borderColor);
        g.drawRect(minX, minY, maxX-minX-1, maxY-minY-1);
    }
}
