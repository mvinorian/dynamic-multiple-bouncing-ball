package com.dynamicmultiplebouncingball;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BallPanel extends JPanel implements KeyListener {
    
    private static final int REFRESH_RATE = 120;

    private List<Ball> balls;
    private BallArea box;
    private int areaWidth;
    private int areaHeight;
    private boolean onLoop;

    public BallPanel(int width, int height) {
        this.areaWidth = width;
        this.areaHeight = height;
        this.onLoop = false;
        this.setPreferredSize(new Dimension(this.areaWidth, this.areaHeight));
        
        this.balls = new ArrayList<Ball>();

        this.box = new BallArea(0, 0, width, height, new Color(40, 40, 40), Color.WHITE);
        this.addKeyListener(this);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                Component c = (Component)e.getComponent();
                Dimension dim = c.getSize();
                areaWidth = dim.width;
                areaHeight = dim.height;
                box.set(0, 0, areaWidth, areaHeight);
            }
        });
        startThread();
        this.setFocusable(true);
    }

    public void startThread() {
        Thread gameThread = new Thread() {
            public void run() {
                while (true) {
                    for (Ball ball : balls) {
                        onLoop = true;
                        ball.collide(box);
                        for (Ball b : balls) {
                            if (!ball.equals(b)) ball.collide(b);
                        }
                    }
                    onLoop = false;
                    repaint();
                    try {
                        Thread.sleep(1000 / REFRESH_RATE);
                    } catch (InterruptedException e) {}
                }
            }
        };
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        box.draw(g);
        for (Ball ball : balls) ball.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (onLoop) return;
        char key = e.getKeyChar();
        if (('0' <= key && key <= '9') || ('A' <= key && key <= 'Z')) {
            Random rand = new Random();
            boolean isCollide = true;
            int x = 0, y = 0, radius = 50, speed = 3, angle = 0;
            while (isCollide) {
                x = rand.nextInt(this.areaWidth - 2*radius - 20) + radius + 10;
                y = rand.nextInt(this.areaHeight - 2*radius - 20) + radius + 10;

                angle = rand.nextInt(360);

                Ball ball = new Ball(x, y, radius, speed, angle, new Color(0, 175, 175), key);
                
                isCollide = false;
                for (Ball b : balls) {
                    if (!ball.equals(b)) isCollide |= ball.isCollide(b);
                }
            }
            this.balls.add(new Ball(x, y, radius, speed, angle, new Color(rand.nextInt(100, 255), rand.nextInt(100, 255), rand.nextInt(100, 255)), key));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // do nothing
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // do nothing
    }
}
