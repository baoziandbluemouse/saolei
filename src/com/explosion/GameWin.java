package com.explosion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.explosion.GameUtil.*;

//主界面类


public class GameWin extends JFrame {
    MapBottom mapbottom = new MapBottom();
    MapTop maptop = new MapTop();
    GameSelect gameselect = new GameSelect();
    Image offScreenImage = null;
    //是否开始游戏
    boolean begin = false;
    //根据雷区的大小，来确定整个游戏窗口的大小
    int wigth = 2 * OFFSET + SQUARE_LEN * MAP_W;
    int height = 4 * OFFSET + SQUARE_LEN * MAP_H;

    //游戏窗口搭建
    void lunch() {
        START_TIME = System.currentTimeMillis();
        this.setVisible(true);
        if (state == 3) {
            this.setSize(500, 500);
        } else {
            this.setSize(wigth, height);
        }
        this.setLocationRelativeTo(null);//窗口居中显示
        this.setTitle("扫雷");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(Icon);

        //鼠标事件
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switch (state) {
                    case 0:
                        if (e.getButton() == 1) {
                            //鼠标左键被点击
                            MOUSE_X = e.getX();
                            MOUSE_Y = e.getY();
                            LEFT = true;
                        }
                        if (e.getButton() == 3) {
                            //鼠标右键被点击
                            MOUSE_X = e.getX();
                            MOUSE_Y = e.getY();
                            RIGHT = true;
                        }
                    case 1:
                    case 2:
                        if (e.getButton() == 1) {
                            int a = e.getX();
                            int b = e.getY();
                            if (a > OFFSET + SQUARE_LEN * (MAP_W / 2)
                                    && a < OFFSET + SQUARE_LEN * (MAP_H / 2) + SQUARE_LEN
                                    && b > OFFSET
                                    && b < OFFSET + SQUARE_LEN) {
                                mapbottom.reGame();
                                maptop.reGameTop();
                                LEFT = false;
                                RIGHT = false;
                                state = 0;
                                FLAG_NUM = 0;
                                START_TIME = System.currentTimeMillis();
                            }
                        }
                        if (e.getButton() == 2) {
                            state = 3;
                            begin = true;
                        }
                        break;
                    case 3:
                        if (e.getButton() == 1) {
                            MOUSE_X = e.getX();
                            MOUSE_Y = e.getY();
                            begin = gameselect.hard();
                        }
                    default:
                        break;
                }
                if (e.getButton() == 1) {
                    //鼠标左键被点击
                    MOUSE_X = e.getX();
                    MOUSE_Y = e.getY();
                    LEFT = true;
                }
                if (e.getButton() == 3) {
                    //鼠标右键被点击
                    MOUSE_X = e.getX();
                    MOUSE_Y = e.getY();
                    RIGHT = true;
                }
            }
        });


        //让游戏界面不停更新
        while (true) {
            repaint();
            began();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void began() {
        if (begin) {
            //释放状态
            begin = false;
            gameselect.hard(level);
            dispose();//退出当前界面
            GameWin gamewin = new GameWin();
            START_TIME = System.currentTimeMillis();
            mapbottom.reGame();
            maptop.reGameTop();
            //重置旗数，避免出现游戏换模式后雷数不对的情况
            FLAG_NUM = 0;
            //重置鼠标X,Y，避免出现游戏刚开始就有格子被翻开的情况
            MOUSE_X = 0;
            MOUSE_Y = 0;
            gamewin.lunch();
        }
    }

    //在游戏窗口中绘制雷区
    @Override
    public void paint(Graphics g) {
        if (state == 3) {
            gameselect.paintself(g);
        } else {
            offScreenImage = this.createImage(wigth, height);
            Graphics gImage = offScreenImage.getGraphics();
            gImage.setColor(Color.gray);
            gImage.fillRect(0, 0, wigth, height);
            mapbottom.paintSelf(gImage);
            maptop.paintSelf(gImage);
            g.drawImage(offScreenImage, 0, 0, null);
        }

    }

    //主函数作为入口
    public static void main(String[] args) {
        GameWin gw = new GameWin();
        gw.lunch();
    }
}
