package com.hspedu.tank_game_05_modify;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Scanner;

public class HspTankGame05Modi extends JFrame {

    //初始化一个Panel
    MyPanel mp = null;

    public static void main(String[] args) throws IOException {

        HspTankGame05Modi hspTankGame05Modi = new HspTankGame05Modi();
    }

    public HspTankGame05Modi() throws IOException {
        //5.4 键盘输入 1
        System.out.println("请选择： 1-开始新游戏  2-继续上一局游戏");
        Scanner scanner = new Scanner(System.in);
        String key = scanner.next();
        mp = new MyPanel(key);
        this.add(mp);
        Thread thread = new Thread(mp);//启动重绘线程
        thread.start();
        this.addKeyListener(mp);
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //增加相应关闭窗口的操作，关闭时保存Record
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Recorder.keepRecord();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    System.exit(0);
                }

            }
        });

    }
}
