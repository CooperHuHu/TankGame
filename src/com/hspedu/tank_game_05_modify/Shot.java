package com.hspedu.tank_game_05_modify;

public class Shot implements Runnable {//射击子弹类
    int x;
    int y;
    int direction;
    int speed = 8;
    boolean isLive = true;

    public Shot(int x, int y, int direction) {//构造子弹
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public void run() {//子弹射击的线程

        while (true) {

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (direction) {//按照方向来进行射击，并且移动坐标
                case 0://向上
                    y -= speed;
                    break;
                case 1://向右
                    x += speed;
                    break;
                case 2://向下
                    y += speed;
                    break;
                case 3://向左
                    x -= speed;
                    break;
            }

            //测试用
            System.out.println("子弹坐标 x= " + x + ", y= " + y);

            //子弹退出的条件，超出屏幕或者isLive为false，用执行的条件取反
            if (!(x >= 0 && x <= 800 && y >= 0 && y <= 600) || !isLive) {
                isLive = false;
                System.out.println("子弹线程退出");
                break;
            }
        }
    }
}
