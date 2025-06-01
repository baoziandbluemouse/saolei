package com.explosion;

//工具类，存放静态参数，工具方法


import java.awt.*;

public class GameUtil {
    //初始的全设置为最高的，防止更改难度导致数组越界
    //地雷个数
    static int RAY_MAX = 100;
    //雷区的宽
    static int MAP_W = 36;
    //雷区的高
    static int MAP_H = 36;
    //雷区界面偏移量
    static int OFFSET = 45;
    //雷区中每个格子边长
    static int SQUARE_LEN = 50;
    //插旗数量
    static int FLAG_NUM = 0;
    //鼠标相关
    //坐标
    static int MOUSE_X;
    static int MOUSE_Y;
    //状态
    static boolean LEFT = false;
    static boolean RIGHT = false;

    //游戏状态 0 游戏中 1 胜利 2 失败 3表示选择难度中
    static int state = 3;
    //游戏难度
    static int level;
    //倒计时
    static long START_TIME;
    static long END_TIME;

    //底层元素 -1 雷 0 空 1-8表示对应数字,用于制作雷区
    static int[][] DATA_BOTTOM = new int[MAP_W + 2][MAP_H + 2];
    //顶层元素 -1 无覆盖 0 覆盖 1 插旗 2 插错旗
    static int[][] DATA_TOP = new int[MAP_W + 2][MAP_H + 2];
    //载入图片
    static Image Icon = Toolkit.getDefaultToolkit().
            getImage(GameUtil.class.getResource("/imgs/icon.gif"));
    static Image Lei = Toolkit.getDefaultToolkit().getImage(
            GameUtil.class.getResource("/imgs/mine.gif"));
    static Image Top = Toolkit.getDefaultToolkit().getImage(
            GameUtil.class.getResource("/imgs/blank.gif"));
    static Image Flag = Toolkit.getDefaultToolkit().getImage(
            GameUtil.class.getResource("/imgs/flag.gif"));
    static Image noFlag = Toolkit.getDefaultToolkit().getImage(
            GameUtil.class.getResource("/imgs/noflag.png"));

    static Image Gameface = Toolkit.getDefaultToolkit().getImage(
            GameUtil.class.getResource("/imgs/face0.gif"));
    static Image Winface = Toolkit.getDefaultToolkit().getImage(
            GameUtil.class.getResource("/imgs/face4.gif"));
    static Image Loseface = Toolkit.getDefaultToolkit().getImage(
            GameUtil.class.getResource("/imgs/face3.gif"));


    static Image Nums[] = new Image[10];


    static {
        for (int i = 0; i <= 8; i++) {
            Nums[i] = Toolkit.getDefaultToolkit().getImage(GameUtil.class.getResource("/imgs/" + i + ".gif"));
        }
    }

    static void drawWord(Graphics g, String word, int x, int y, int size, Color color) {
        g.setColor(color);
        g.setFont(new Font("TimesRoman", Font.BOLD, size));
        g.drawString(word, x, y);
    }
}
