package com.dongyang.ui;

import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

public class TSeparator extends JComponent {
    //�̶����
    private static int WIDTH = 2;
    //�̶��߶�
    private static int HEIGHT = 24;
    /**
     * ������
     */
    public TSeparator() {
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setSize(new Dimension(WIDTH, HEIGHT));
    }

    /**
     * ��ͼ
     * @param g ͼ�ζ���
     */
    public void paint(Graphics g) {
        g.setColor(new Color(106, 140, 203));
        g.drawLine(0, 4, 0, getHeight() - 6);
        g.setColor(new Color(241, 249, 255));
        g.drawLine(1, 5, 1, getHeight() - 5);
    }
}
