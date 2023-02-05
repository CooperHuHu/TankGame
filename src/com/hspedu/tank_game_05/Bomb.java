package com.hspedu.tank_game_05;

public class Bomb {//炸弹，实现坦克爆炸的效果
    int x;
    int y;
    int life = 9; //持续时长
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifeDown() {//炸弹生命周期减少

        if (life > 0) {
            life--;
        } else {
            isLive = false;
        }

    }
}
