package com.explosion;

import java.util.HashSet;

import static com.explosion.GameUtil.*;

//初始化地雷的类


public class BottomRay {
    //生成地雷的具体方法
    void newRay() {
        //注意随机生成时，可能会出现坐标重复的问题，要进行判断，防止地雷重合的问题
        //采用Set存放地雷的横、竖坐标
        //Set作为局部变量，才不会在重启游戏时没有清空地雷
        HashSet<Pair<Integer, Integer>> set = new HashSet<Pair<Integer, Integer>>();
        //x,y用于代表地雷坐标
        int x, y;
        int count = 0;
        //count达到RAY_MAX才会停止随机生成
        while (count < RAY_MAX) {
            //随机生成x,y坐标，范围为1-11
            //random生成一个0-1.0的浮点数（1取不到）
            x = (int) (Math.random() * MAP_W + 1);
            y = (int) (Math.random() * MAP_H + 1);

            Pair<Integer, Integer> p = new Pair<>(x, y);
            //将生成坐标赋值到Set中
            //如果已经有了，那就不赋值了
            if (!set.contains(p)) {
                set.add(p);
                //将地雷添加到二维数组中，后续用于制作雷区
                //-1代表有雷
                DATA_BOTTOM[x][y] = -1;
                count++;
            }
        }
    }
}


//自定义一个pair类
class Pair<K, V> {
    public K first;
    public V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    //HashSet用于判断重复的方法是使用equals方法
    //为此我们要重写pair类的equal函数
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return first.equals(pair.first) && second.equals(pair.second);
    }

    @Override
    public int hashCode() {
        return first.hashCode() * 31 + second.hashCode();
    }
}
