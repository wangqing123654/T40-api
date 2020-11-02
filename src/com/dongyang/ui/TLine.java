package com.dongyang.ui;

import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

public class TLine extends JLabel {
    public TLine() {
        this.setPreferredSize(new Dimension(4,4));
    }

    public void paint(Graphics g) {
        int y = getHeight() / 2;
        g.setColor(new Color(168, 168, 168));
        g.drawLine(10, y, getWidth() - 10, y);
        g.setColor(new Color(255, 255, 255));
        y++;
        g.drawLine(10, y, getWidth() - 10, y);
    }
}
