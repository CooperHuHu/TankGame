package com.hspedu.tank_game_05;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    //判断敌方坦克是否碰撞，需要一个敌方坦克的集合，还有一个set方法，
    //来自MyPanel中的Vector<EnemyTank> enemyTanks
    Vector<EnemyTank> enemyTanks = null;

    public EnemyTank(int x, int y) {
        super(x, y);
        super.setDirect(2);
        //初始化敌方坦克时就给1颗子弹，并且启动子弹的线程
        shot = new Shot(x + 20, y + 60, getDirect());
        new Thread(shot).start();
        shots.add(shot);
    }
    //5.4 为了解决继续游戏时，发射子弹的方向，对构造器进行重载
    public EnemyTank(int x, int y, int direct) {
        super(x, y);
        super.setDirect(direct);
        //初始化敌方坦克时就给1颗子弹，并且启动子弹的线程
        shot = new Shot(x + 20, y + 60, direct);
        new Thread(shot).start();
        shots.add(shot);
    }

    //传入MyPanel中敌方坦克的集合
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //判断坦克是否碰撞
    public boolean isTouchEnemyTank() {
        //先根据自己的方向，然后再逐个取出敌方坦克，根据敌方坦克的方向，判断碰撞
        switch (this.getDirect()) {//判断自己的方向
            case 0:
                //取出敌方坦克，根据对方的方向来判断范围
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {//取出的不是自己，再判断
                        //1、自己坦克的端点是左上角、右上角
                        //2、对方的方向是上/下
                        //对方的X方向范围是getX(), getX()+40
                        //对方的Y方向范围是getY(), getY()+60
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //左上角getX(), getY()，是否进入区域
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //右上角getX() + 40, getY()，是否进入区域
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //对方的方向是左/右
                        //对方的X方向范围是getX(), getX()+60
                        //对方的Y方向范围是getY(), getY()+40
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //左上角getX(), getY()，是否进入区域
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //右上角getX() + 40, getY()，是否进入区域
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1:
                //取出敌方坦克，根据对方的方向来判断范围
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {//取出的不是自己，再判断
                        //1、自己坦克的端点是右下角、右上角
                        //2、对方的方向是上/下
                        //对方的X方向范围是getX(), getX()+40
                        //对方的Y方向范围是getY(), getY()+60
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //右下角getX() + 60, getY() +40，是否进入区域
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() +60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //右上角getX() + 60, getY()，是否进入区域
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //对方的方向是左/右
                        //对方的X方向范围是getX(), getX()+60
                        //对方的Y方向范围是getY(), getY()+40
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //右上角getX() + 60, getY()，是否进入区域
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //右下角getX() + 60, getY() +40，是否进入区域
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40<= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2:
                //取出敌方坦克，根据对方的方向来判断范围
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {//取出的不是自己，再判断
                        //1、自己坦克的端点是左下角、右下角
                        //2、对方的方向是上/下
                        //对方的X方向范围是getX(), getX()+40
                        //对方的Y方向范围是getY(), getY()+60
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //左下角getX(), getY()+60，是否进入区域
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //右下角getX() + 40, getY() + 60，是否进入区域
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //对方的方向是左/右
                        //对方的X方向范围是getX(), getX()+60
                        //对方的Y方向范围是getY(), getY()+40
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //左下角getX(), getY()+60，是否进入区域
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //右下角getX() + 40, getY() + 60，是否进入区域
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3:
                //取出敌方坦克，根据对方的方向来判断范围
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {//取出的不是自己，再判断
                        //1、自己坦克的端点是左上角、左下角
                        //2、对方的方向是上/下
                        //对方的X方向范围是getX(), getX()+40
                        //对方的Y方向范围是getY(), getY()+60
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //左上角getX(), getY()，是否进入区域
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //左下角getX(), getY() + 40，是否进入区域
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //对方的方向是左/右
                        //对方的X方向范围是getX(), getX()+60
                        //对方的Y方向范围是getY(), getY()+40
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //左上角getX(), getY()，是否进入区域
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //左下角getX(), getY() + 40，是否进入区域
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40<= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        //没有碰撞就返回false
        return false;
    }

    @Override
    public void run() {//坦克的随机移动和发射子弹
        while (isLive) {//坦克活着再移动
            //循环发子弹，当一颗子弹消亡后，再发一颗。坦克也存活
            if (shots.size() < 1) {
                //根据方向创建子弹，启动线程，保存到vector
                fire();
            }

            //坦克移动
            switch (getDirect()) {
                case 0://上
                    for (int i = 0; i < (int) (Math.random() * 600); i++) {
                        if (!isTouchEnemyTank()){
                            moveUP();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1://右
                    for (int i = 0; i < (int) (Math.random() * 800); i++) {
                        if (!isTouchEnemyTank()){
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2://下
                    for (int i = 0; i < (int) (Math.random() * 600); i++) {
                        if (!isTouchEnemyTank()){
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3://左
                    for (int i = 0; i < (int) (Math.random() * 800); i++) {
                        if (!isTouchEnemyTank()){
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
            //随机改变方向
            setDirect((int) (Math.random() * 4));
        }
    }
}
