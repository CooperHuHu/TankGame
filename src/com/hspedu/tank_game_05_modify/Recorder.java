package com.hspedu.tank_game_05_modify;

import java.io.*;
import java.util.Vector;

/**
 * @Auther :Cooper
 * @Version :1.0
 * @Date :2022/8/31 20:31
 * @Description :统计我方打掉的坦克数，并在退出时保存到myRecord.txt文件中
 */
public class Recorder {

    private static int allEnemyTankNum = 0; //计分
    private static BufferedWriter bw = null;//处理流，写数据
    private static String recordFile = "src\\myRecord.txt";
    private static Vector<EnemyTank> enemyTanks = null;
    //读取myRecord中坦克的相关信息的属性
    private static Vector<Node> nodes = new Vector<>();
    private static BufferedReader br = null;

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //5.4增加一个方法，用于恢复坦克信息和累计击毁敌方坦克的数量
    public static Vector<Node> getNodesAndEnemyNum() throws IOException {
        //从myRecord.txt中读取数据
        br = new BufferedReader(new FileReader(recordFile));
        allEnemyTankNum = Integer.parseInt(br.readLine());//读累计击毁的坦克数
        br.readLine();//读掉第二行标题行
        String line = "";
        while ((line = br.readLine()) != null) {
            String[] strs = line.split(" ");
            Node node = new Node(Integer.parseInt(strs[0]),
                    Integer.parseInt(strs[1]), Integer.parseInt(strs[2]));
            nodes.add(node);
        }

        if (br != null) {
            br.close();
        }

        return nodes;
    }

    //保存记录
    public static void keepRecord() throws IOException {

        bw = new BufferedWriter(new FileWriter(recordFile));
        bw.write(allEnemyTankNum + "\r\n");
        //bw.newLine();//换一行，相当于\r\n
        bw.write("剩下的坦克位置：X坐标 Y坐标 方向\r\n");
        for (EnemyTank enemyTank : enemyTanks) {
            if (enemyTank.isLive) {//保险起见，判断一下坦克是否存活
                String record = enemyTank.getX() + " " + enemyTank.getY() + " "
                        + enemyTank.getDirect() + "\r\n";
                bw.write(record);
            }
        }

        if (bw != null) {
            bw.close();
        }
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //击中敌方坦克时，计分+1
    public static void addAllEnemyTankNum() {
        allEnemyTankNum++;
    }


}
