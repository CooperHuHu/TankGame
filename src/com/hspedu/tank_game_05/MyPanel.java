package com.hspedu.tank_game_05;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {

    Hero hero = null;
    //敌方坦克很多，以后也要用多线程，用一个vector数组来定义
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemySize = 3;
    //爆炸效果，也用Vector来保存
    Vector<Bomb> bombs = new Vector<>();
    //三张图片，爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    //5.4 接收myRecord.txt中的nodes
    Vector<Node> nodes = null;
    //构造器初始化玩家坦克、敌方坦克、子弹、爆炸图片
    public MyPanel(String key) throws IOException {
        this.hero = new Hero(400, 400);
        hero.setSpeed(2);//调整速度
        //调用Recorder.setEnemyTanks()，把敌方坦克集合地址传入，方便保存
        Recorder.setEnemyTanks(enemyTanks);
        //5.4根据key来创建新游戏或者继续上一轮游戏
        switch (key){
            case "1"://创建新游戏
                //增加敌方坦克，用for循环添加，并且x坐标和序号关联
                for (int i = 0; i < enemySize; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    //创建敌方坦克后，调用EnemyTank.setEnemyTanks()，把敌方坦克集合的地址传进去。
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setSpeed(2);
                    //启动坦克移动的线程
                    new Thread(enemyTank).start();
                    //把敌方坦克加到敌方坦克列表
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2"://继续上一轮游戏
                //5.4 加载myRecord.txt中的nodes和AllEnemyNum
                nodes = Recorder.getNodesAndEnemyNum();
                //5.4 从nodes中，加载敌方坦克信息，并且初始化坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY(), node.getDirect());
                    //5.1创建敌方坦克后，调用EnemyTank.setEnemyTanks()，把敌方坦克集合的地址传进去。
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setSpeed(2);
                    //启动坦克移动的线程
                    new Thread(enemyTank).start();
                    //把敌方坦克加到敌方坦克列表
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("输入有误~");
        }
        //初始化爆炸图片
        image1 = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Administrator\\IdeaProjects\\tankGame05\\out\\production\\bomb_3.gif");
        image2 = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Administrator\\IdeaProjects\\tankGame05\\out\\production\\bomb_2.gif");
        image3 = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Administrator\\IdeaProjects\\tankGame05\\out\\production\\bomb_1.gif");
//        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //填充一块黑色的幕布
        g.fillRect(0, 0, 800, 600);
        showInfo(g);//计分区
        //判断我方坦克是否被击中
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            beHit(enemyTank.shots, hero);
        }
        //如果hero存活，才画
        if (hero != null && hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
        }
        //hero的子弹可以多颗以后，需要循环来绘制
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (/*shot != null &&*/ shot.isLive) {//之前用hero.shot.isLive，必须5颗都空才能继续打
                drawShot(shot.x, shot.y,g,0);
            } else {//如果不满足画的条件，就从vector中去掉
                hero.shots.remove(shot);
            }

        }
        //循环画炸弹
        try {//延时一下，给第一个时间
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            //让bomb的life递减下
            bomb.lifeDown();
            //如果life为0了，就从vector中去掉
            if (bomb.life <= 0) {
                bombs.remove(bomb);
            }
        }
        //遍历Vector，取出敌方坦克和子弹画图
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //循环画子弹，敌方坦克死没死，打出去的子弹都应该有
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                //如果子弹存活就画，不存活就remove
                if (shot.isLive) {
                    drawShot(shot.x, shot.y,g,1);
                } else {
                    enemyTank.shots.remove(shot);
                }
            }
            beHit(hero.shots, enemyTank);//判断是否被击中
            if (!enemyTank.isLive) {//如果此坦克已经死了，就继续
                continue;
            }
            drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);

        }


    }

    /**
     * 子弹有颜色，所以定义方法画
     * @param x
     * @param y
     * @param g
     * @param type
     */
    public void drawShot(int x, int y, Graphics g, int type){
        int width = 2;
        int height = 2;
        //子弹的阵营
        switch (type) {
            case 1:
                g.setColor(Color.cyan);
                break;
            case 0:
                g.setColor(Color.yellow);
                break;
        }
        g.draw3DRect(x, y, width, height, false);
    }
    /**
     * @param x      坦克横坐标
     * @param y      坦克纵坐标
     * @param g      画笔
     * @param direct 方向 向上0 向右1 向下2 向左3
     * @param type   坦克类型  我方0 敌方1
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        //坦克的阵营
        switch (type) {
            case 1:
                g.setColor(Color.cyan);
                break;
            case 0:
                g.setColor(Color.yellow);
                break;
        }
        //坦克方向
        switch (direct) {
            case 0://坦克向上
                g.fill3DRect(x, y, 10, 60, false);//左轮
                g.fill3DRect(x + 30, y, 10, 60, false);//右轮
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//身子
                g.fillOval(x + 10, y + 20, 20, 20);//炮塔
                g.drawLine(x + 20, y + 30, x + 20, y);//炮管
                break;
            case 1://坦克向右
                g.fill3DRect(x, y, 60, 10, false);//上轮
                g.fill3DRect(x, y + 30, 60, 10, false);//下轮
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//身子
                g.fillOval(x + 20, y + 10, 20, 20);//炮塔
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//炮管
                break;
            case 2://坦克向下
                g.fill3DRect(x, y, 10, 60, false);//左轮
                g.fill3DRect(x + 30, y, 10, 60, false);//右轮
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//身子
                g.fillOval(x + 10, y + 20, 20, 20);//炮塔
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//炮管
                break;
            case 3://坦克向左
                g.fill3DRect(x, y, 60, 10, false);//上轮
                g.fill3DRect(x, y + 30, 60, 10, false);//下轮
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//身子
                g.fillOval(x + 20, y + 10, 20, 20);//炮塔
                g.drawLine(x + 30, y + 20, x, y + 20);//炮管
                break;
            default:
                System.out.println("暂时不处理");
        }


    }

    //编写方法，显示我方击毁敌方坦克的数量
    public void showInfo(Graphics g){
        //设置黑色
        g.setColor(Color.BLACK);
        //设置字体
        Font font = new Font("宋体", Font.BOLD, 15);
        g.setFont(font);
        //显示文字
        g.drawString("您击毁的坦克数量",820,30);
        //坦克图标
        drawTank(820,60,g,0,1);
        //计分区
        g.setColor(Color.BLACK);//画坦克时改了颜色，要重设下画笔颜色
        font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString(Recorder.getAllEnemyTankNum()+"",890,100);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //调整方向和移动
        if (e.getKeyCode() == KeyEvent.VK_W) {//向上
            hero.setDirect(0);
            hero.moveUP();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//向右
            hero.setDirect(1);
            hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//向下
            hero.setDirect(2);
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//向左
            hero.setDirect(3);
            hero.moveLeft();
        }
        //按下J键，调用Hero中的shotEnemyTank方法，创建子弹，启动线程
        if (e.getKeyCode() == KeyEvent.VK_J) {
//            if (hero.shot==null||!hero.shot.isLive){//锁定一次只能打一颗子弹
//                hero.shotEnemyTank();
//            }
            hero.shotEnemyTank();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每隔100毫秒重绘一下，放在线程中
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }
    //判断坦克是否被多颗子弹击中
    public void beHit(Vector<Shot> vector, Tank tank) {
        for (int i = 0; i < vector.size(); i++) {
            Shot shot = vector.get(i);
            if (shot != null && shot.isLive) {//这个判断条件可能影响第几颗子弹才能打到坦克
                //子弹落在了坦克的坐标区域，把isLive都设为false，坦克的Vector里移除tank
                switch (tank.getDirect()) {
                    case 0://向上，坦克是纵向的
                    case 2://向下
                        if (shot.x > tank.getX()
                                && shot.x < tank.getX() + 40
                                && shot.y > tank.getY()
                                && shot.y < tank.getY() + 60) {
                            shot.isLive = false;
                            tank.isLive = false;
                            enemyTanks.remove(tank);
                            //判断击中的坦克是否是EnemyTank的实例，如果是，Record.allEnemyTankNum加1
                            if(tank instanceof EnemyTank){
                                Recorder.addAllEnemyTankNum();
                            }
                            //击中以后创建一个Bomb，存到bombs中
                            Bomb bomb = new Bomb(tank.getX(), tank.getY());
                            bombs.add(bomb);
                        }
                        break;
                    case 1://向右，坦克是横向的
                    case 3://向左
                        if (shot.x > tank.getX()
                                && shot.x < tank.getX() + 60
                                && shot.y > tank.getY()
                                && shot.y < tank.getY() + 40) {
                            shot.isLive = false;
                            tank.isLive = false;
                            enemyTanks.remove(tank);
                            if(tank instanceof EnemyTank){
                                Recorder.addAllEnemyTankNum();
                            }
                            //击中以后创建一个Bomb，存到bombs中
                            Bomb bomb = new Bomb(tank.getX(), tank.getY());
                            bombs.add(bomb);
                        }
                        break;
                }
            }
        }
    }

}
