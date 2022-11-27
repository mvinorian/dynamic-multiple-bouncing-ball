package com.dynamicmultiplebouncingball;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.FontMetrics;

public class Ball {

    float x, y;
    float speedX, speedY;
    float radius;
    private Color color;
    private char key;

    public Ball(float x, float y, float radius, float speed, float angle, Color color, char key) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = speed * (float)Math.cos(Math.toRadians(angle));
        this.speedY = -speed * (float)Math.sin(Math.toRadians(angle));
        this.color = color;
        this.key = key;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int)(x-radius), (int)(y-radius), (int)(2*radius), (int)(2*radius));

        g.setColor(color.darker().darker());
        g.setFont(new Font("Monospaced", Font.BOLD, 30));

        FontMetrics metrics = g.getFontMetrics();
        int stringPosX = (int)this.x - metrics.stringWidth(String.format("%c", key))/2;
        int stringPosY = (int)this.y - metrics.getHeight()/2 + metrics.getAscent();
        g.drawString(String.format("%c", key), stringPosX, stringPosY);
    }

    public boolean isCollide(Ball ball) {
        float dX = ball.x - this.x;
        float dY = ball.y - this.y;
        float r = ball.radius + this.radius;
    
        return dX*dX + dY*dY < r*r;
    }

    public void collide(Ball ball) {
        if (!this.isCollide(ball)) return;

        float dX = ball.x - this.x;
        float dY = ball.y - this.y;

        float d = (float)Math.sqrt(dX*dX + dY*dY);
        float nX = dX / d;
        float nY = dY / d;
        float p = this.speedX*nX + this.speedY*nY - ball.speedX*nX - ball.speedY*nY;

        float vX1 = this.speedX - p*nX;
        float vY1 = this.speedY - p*nY;
        float vX2 = ball.speedX + p*nX;
        float vY2 = ball.speedY + p*nY;

        this.speedX = vX1;
        this.speedY = vY1;
        ball.speedX = vX2;
        ball.speedY = vY2;
    }

    public void collide(BallArea box) {
        float ballMinX = box.minX + this.radius;
        float ballMinY = box.minY + this.radius;
        float ballMaxX = box.maxX - this.radius;
        float ballMaxY = box.maxY - this.radius;

        this.x += this.speedX;
        this.y += this.speedY;

        if (this.x < ballMinX) {
            this.speedX = -this.speedX;
            this.x = ballMinX;
        } else if (this.x > ballMaxX) {
            this.speedX = -this.speedX;
            this.x = ballMaxX;
        }

        if (this.y < ballMinY) {
            this.speedY = -this.speedY;
            this.y = ballMinX;
        } else if (this.y > ballMaxY) {
            this.speedY = -this.speedY;
            this.y = ballMaxY;
        }
    }
}
