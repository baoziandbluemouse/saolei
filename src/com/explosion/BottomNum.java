package com.explosion;

import static com.explosion.GameUtil.*;

//底层数字类

public class BottomNum {
    //遍历DATA_BOTTOM中的所有雷点，将雷点附近3x3的非雷点方格全部+1
    void newNum() {

        for (int i = 1; i <= MAP_W; i++) {
            for (int j = 1; j <= MAP_H; j++) {
                if (DATA_BOTTOM[i][j] == -1) {
                    for (int k = i - 1; k <= i + 1; k++) {
                        for (int l = j - 1; l <= j + 1; l++) {
                            if (DATA_BOTTOM[k][l] >= 0) {
                                DATA_BOTTOM[k][l]++;
                            }
                        }
                    }
                }
            }
        }

    }
}
