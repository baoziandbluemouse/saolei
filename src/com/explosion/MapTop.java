package com.explosion;

import java.awt.*;

import static com.explosion.GameUtil.*;

/*
  顶层地图类
  绘制顶层组件
  判断逻辑
*/
public class MapTop {

    //格子位置,通过公式将鼠标点击得到的坐标转换为格子坐标
    int temp_x;
    int temp_y;
    int endflag = -1;

    //重置游戏
    void reGameTop() {
        for (int i = 1; i <= MAP_W; i++) {
            for (int j = 1; j <= MAP_H; j++) {
                DATA_TOP[i][j] = 0;
            }
        }

    }

    //判断逻辑
    void logic() {
        if (endflag == 1) {
            return;
        }
        temp_y = 0;
        temp_x = 0;
        if (MOUSE_X > OFFSET && MOUSE_Y > OFFSET * 3) {
            temp_x = (MOUSE_X - OFFSET) / SQUARE_LEN + 1;
            temp_y = (MOUSE_Y - OFFSET * 3) / SQUARE_LEN + 1;
            //符合条件的x，y就会开始改变
            if (temp_x >= 1 && temp_y >= 1
                    && temp_x <= MAP_W && temp_y <= MAP_H) {
                if (LEFT) {
                    if (DATA_TOP[temp_x][temp_y] == 0) {
                        DATA_TOP[temp_x][temp_y] = -1;
                        //翻开
                    }
                    spaceOpen(temp_x, temp_y);
                    LEFT = false;
                } else if (RIGHT) {
                    //覆盖则插旗,插旗则取消
                    if (DATA_TOP[temp_x][temp_y] == 0) {
                        DATA_TOP[temp_x][temp_y] = 1;
                        FLAG_NUM++;
                    } else if (DATA_TOP[temp_x][temp_y] == 1) {
                        DATA_TOP[temp_x][temp_y] = 0;
                        FLAG_NUM--;
                    } else if (DATA_TOP[temp_x][temp_y] == -1) {
                        numOpen(temp_x, temp_y);
                        //右键已经翻开的格子的功能
                    }
                    RIGHT = false;
                }
            }
        }
        if (islose()) {
            endflag = 1;
        }
        if (isvictory()) {
            endflag = 1;
        }
    }

    //失败判定
    boolean islose() {
        if (FLAG_NUM == RAY_MAX) {
            for (int i = 1; i <= MAP_W; i++) {
                for (int j = 1; j <= MAP_H; j++) {
                    if (DATA_TOP[i][j] == 0) {
                        DATA_TOP[i][j] = -1;
                    }
                }
            }
        }
        for (int i = 1; i <= MAP_W; i++) {
            for (int j = 1; j <= MAP_H; j++) {
                if (DATA_BOTTOM[i][j] == -1 && DATA_TOP[i][j] == -1) {
                    state = 2;
                    seeBoom();
                    return true;
                }
            }
        }
        return false;
    }

    //失败显示所有雷
    void seeBoom() {
        for (int i = 1; i <= MAP_W; i++) {
            for (int j = 1; j <= MAP_H; j++) {
                //底层是雷，顶层未翻开且不是旗时
                if (DATA_BOTTOM[i][j] == -1 && DATA_TOP[i][j] != -1) {
                    DATA_TOP[i][j] = -1;
                }
                //底层不是雷，顶层是旗时
                if (DATA_BOTTOM[i][j] != -1 && DATA_TOP[i][j] == 1) {
                    DATA_TOP[i][j] = 2;
                }
            }
        }
    }

    //胜利判断
    boolean isvictory() {
        //统计未打开格子数
        int count = 0;
        for (int i = 1; i <= MAP_W; i++) {
            for (int j = 1; j <= MAP_H; j++) {
                if (DATA_TOP[i][j] != -1) {
                    count++;
                }
            }
        }
        if (count == RAY_MAX) {
            state = 1;
            for (int i = 1; i <= MAP_W; i++) {
                for (int j = 1; j <= MAP_H; j++) {
                    //未翻开，变成旗
                    if (DATA_TOP[i][j] == 0) {
                        DATA_TOP[i][j] = 1;
                    }
                }
            }
            return true;
        }
        return false;
    }

    //假如有一个格子在DATA_BOTTOM中为0，则他的周围都是没有雷的，可以直接翻开
    //递归翻开方法实现
    void spaceOpen(int x, int y) {
        if (DATA_BOTTOM[x][y] == 0) {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    //未被翻开才递归
                    if (DATA_TOP[i][j] != -1) {
                        //如果有旗子，要释放
                        if (DATA_TOP[i][j] == 1) {
                            FLAG_NUM--;
                        }
                        DATA_TOP[i][j] = -1;
                        //必须在雷区当中才能递归
                        if (i >= 1 && i <= MAP_W && j >= 1 && j <= MAP_H) {
                            spaceOpen(i, j);
                        }
                    }
                }
            }
        }
    }

    void numOpen(int x, int y) {
        //记录旗子数
        int count = 0;
        if (DATA_BOTTOM[x][y] > 0) {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (DATA_TOP[i][j] == 1) {
                        count++;
                    }
                }
            }
            if (count == DATA_BOTTOM[x][y]) {
                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (DATA_TOP[i][j] != 1) {
                            DATA_TOP[i][j] = -1;
                        }
                        //如果翻出来个空格，还要再递归翻
                        if (i >= 1 && i <= MAP_W && j >= 1 && j <= MAP_H) {
                            spaceOpen(i, j);
                        }
                    }
                }
            }

        }
    }

    //绘制方法
    void paintSelf(Graphics g) {
        logic();
        for (int i = 1; i <= MAP_W; i++) {
            for (int j = 1; j <= MAP_H; j++) {
                //覆盖
                if (DATA_TOP[i][j] == 0) {
                    g.drawImage(Top, OFFSET + (i - 1) * SQUARE_LEN + 1,
                            OFFSET * 3 + (j - 1) * SQUARE_LEN + 1,
                            SQUARE_LEN - 2, SQUARE_LEN - 2, null);
                }
                //插旗
                if (DATA_TOP[i][j] == 1) {
                    g.drawImage(Flag, OFFSET + (i - 1) * SQUARE_LEN + 1,
                            OFFSET * 3 + (j - 1) * SQUARE_LEN + 1,
                            SQUARE_LEN - 2, SQUARE_LEN - 2, null);
                }
                //插错旗
                if (DATA_TOP[i][j] == 2) {
                    g.drawImage(noFlag, OFFSET + (i - 1) * SQUARE_LEN + 1,
                            OFFSET * 3 + (j - 1) * SQUARE_LEN + 1,
                            SQUARE_LEN - 2, SQUARE_LEN - 2, null);
                }
            }
        }

    }
}
