package com.hspedu.tank_game_05_modify;

import java.util.Vector;

public class Tank {//定义一个坦克的父类，其他坦克继承他
    private int x;
    private int y;
    private int direct;
    private int speed = 2;
    boolean isLive = true;
    //敌人坦克的子弹，需要一个Vector来保存
    Vector<Shot> shots = new Vector<>();
    Shot shot = null;

    //发射子弹
    public void fire() {
        switch (direct) {
            case 0://向上
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1://向右
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2://向下
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3://向左
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }
        //子弹保存到Vector中
        shots.add(shot);
        //启动线程
        new Thread(shot).start();
    }

    //移动并且加入碰撞判断
    public void moveUP() {
        if (y > 0 ) {
            y -= speed;
        }
    }

    public void moveRight() {
        if (x + 60 < 800) {
            x += speed;
        }
    }

    public void moveDown() {
        if (y + 60 < 600) {
            y += speed;
        }
    }

    public void moveLeft() {
        if (x > 0) {
            x -= speed;
        }
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
