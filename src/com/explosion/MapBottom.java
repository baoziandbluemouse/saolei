package com.explosion;

import java.awt.*;

import static com.explosion.GameUtil.*;

/*
    底层地图
    绘制游戏相关组件
*/
public class MapBottom {
    BottomRay bottomRay = new BottomRay();
    BottomNum bottomNum = new BottomNum();

//    {
//        bottomRay.newRay();
//        bottomNum.newNum();
//    }

    //重置游戏
    void reGame() {
        for (int i = 1; i <= MAP_W; i++) {
            for (int j = 1; j <= MAP_H; j++) {
                DATA_BOTTOM[i][j] = 0;
            }
        }
        bottomRay.newRay();
        bottomNum.newNum();
    }


    //绘制方法
    void paintSelf(Graphics g) {
//        g.setColor(Color.red);
//        //循环绘制以构建出网格状的界面
//        //横坐标相等的画线，画出来的是横线
//        //纵坐标相等，竖线
//        //画竖线
//        for (int i = 0; i <= MAP_H; i++) {
//            g.drawLine(OFFSET + i * SQUARE_LEN, 3 * OFFSET,
//                    OFFSET + i * SQUARE_LEN, 3 * OFFSET + MAP_H * SQUARE_LEN);
//        }
//        //画横线
//        for (int i = 0; i <= MAP_W; i++) {
//            g.drawLine(OFFSET, 3 * OFFSET + i * SQUARE_LEN,
//                    OFFSET + MAP_W * SQUARE_LEN, 3 * OFFSET + i * SQUARE_LEN);
//        }
        //以上废弃

        //添加雷图片
        for (int i = 1; i <= MAP_W; i++) {
            for (int j = 1; j <= MAP_H; j++) {
                //首先，DATA_BOTTOM存储的坐标会比实际的坐标都要偏移一格
                //例如实际为(x,y),那么DATA_BOTTOM存储的就是(x+1,y+1)
                //这么做的考量是后续在判断一个格子周围的地雷数时更加方便
                //i-1，j-1，将坐标复原
                //其他的+1，-2是为了让地雷图片更适合方格
                if (DATA_BOTTOM[i][j] == -1) {
                    //如果有地雷，就放地雷进去
                    g.drawImage(Lei, OFFSET + (i - 1) * SQUARE_LEN + 1,
                            OFFSET * 3 + (j - 1) * SQUARE_LEN + 1,
                            SQUARE_LEN - 2, SQUARE_LEN - 2, null);
                }
            }
        }
        //添加数字图片
        for (int i = 1; i <= MAP_W; i++) {
            for (int j = 1; j <= MAP_H; j++) {
                if (DATA_BOTTOM[i][j] >= 0) {
                    //如果有地雷，就放地雷进去
                    g.drawImage(Nums[DATA_BOTTOM[i][j]], OFFSET + (i - 1) * SQUARE_LEN + 1,
                            OFFSET * 3 + (j - 1) * SQUARE_LEN + 1,
                            SQUARE_LEN - 2, SQUARE_LEN - 2, null);
                }
            }
        }

        //绘制数字 剩余雷数
        drawWord(g, "" + (RAY_MAX - FLAG_NUM),
                OFFSET, 2 * OFFSET, 30, Color.RED);
        //倒计时
        drawWord(g, "" + (END_TIME - START_TIME) / 1000,
                OFFSET + (MAP_W - 1) * SQUARE_LEN, 2 * OFFSET, 30, Color.RED);

        switch (state) {
            case 0:
                END_TIME = System.currentTimeMillis();
                g.drawImage(Gameface,
                        OFFSET + SQUARE_LEN * (MAP_W / 2),
                        OFFSET,
                        SQUARE_LEN, SQUARE_LEN,
                        null);
                break;
            case 1:
                g.drawImage(Winface,
                        OFFSET + SQUARE_LEN * (MAP_W / 2),
                        OFFSET,
                        SQUARE_LEN, SQUARE_LEN,
                        null);
                break;
            case 2:
                g.drawImage(Loseface,
                        OFFSET + SQUARE_LEN * (MAP_W / 2),
                        OFFSET,
                        SQUARE_LEN, SQUARE_LEN,
                        null);
                break;
            default:
        }
    }
}
