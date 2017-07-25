package com.zyf.program.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public class SimpleLayout {
    private JFrame mainFrame;

    public SimpleLayout(){
        mainFrame=new JFrame("Test");
        mainFrame.setSize(800,800);
        mainFrame.setLayout(new GridLayout(3,3));
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        JLabel label=new JLabel("text1",JLabel.CENTER);

        JPanel panel=new JPanel(new FlowLayout());
        JButton button=new JButton("button");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("点击了按钮");
            }
        });
        panel.add(button);
        panel.setBackground(Color.green);

        JButton chooseButton = new JButton("Choose Background");
        chooseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color backgroundColor = JColorChooser.showDialog(mainFrame,
                        "Choose background color", Color.white);
                if(backgroundColor != null){
                    mainFrame.getContentPane().setBackground(backgroundColor);
                }
            }
        });


        mainFrame.add(label);
        mainFrame.add(panel);
        mainFrame.add(chooseButton);
        mainFrame.setVisible(true);
    }
}
