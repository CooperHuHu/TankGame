package com.hspedu.tank_game_05;

import java.util.Vector;

public class Hero extends Tank {//玩家的坦克

    public Hero(int x, int y) {

        super(x, y);
    }

    public void shotEnemyTank() {//创建子弹，并且启动线程

        if (shots.size() == 5) {//同时有5颗子弹以上，就不能再打了
            return;
        }
        fire();
    }
}
