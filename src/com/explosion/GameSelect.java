package com.explosion;

import java.awt.*;

import static com.explosion.GameUtil.*;

/*
    难度选择类
*/
public class GameSelect {

    boolean hard() {
        if (MOUSE_X > 100 && MOUSE_X < 400) {
            if (MOUSE_Y > 50 && MOUSE_Y < 150) {
                level = 1;
                state = 0;
                return true;
            }
            if (MOUSE_Y > 200 && MOUSE_Y < 300) {
                level = 2;
                state = 0;
                return true;
            }
            if (MOUSE_Y > 350 && MOUSE_Y < 450) {
                level = 3;
                state = 0;
                return true;
            }
        }
        return false;
    }

    void paintself(Graphics g) {
        g.drawRect(100, 50, 300, 100);
        drawWord(g, "Easy", 220, 100, 30, Color.BLACK);
        g.drawRect(100, 200, 300, 100);
        drawWord(g, "Normal", 220, 250, 30, Color.BLACK);
        g.drawRect(100, 350, 300, 100);
        drawWord(g, "Hard", 220, 400, 30, Color.BLACK);
    }

    void hard(int level) {
        switch (level) {
            case 1:
                RAY_MAX = 10;
                MAP_W = 9;
                MAP_H = 9;
                SQUARE_LEN = 50;
                break;
            case 2:
                RAY_MAX = 40;
                MAP_W = 16;
                MAP_H = 16;
                SQUARE_LEN = 30;
                break;
            case 3:
                RAY_MAX = 99;
                MAP_W = 25;
                MAP_H = 25;
                SQUARE_LEN = 20;
                break;
            default:
        }
    }
}
