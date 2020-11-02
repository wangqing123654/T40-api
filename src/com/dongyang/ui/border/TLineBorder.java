package com.dongyang.ui.border;

import javax.swing.border.AbstractBorder;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Color;

public class TLineBorder extends AbstractBorder
{
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(168,168,168));
        g.drawLine(x,y,x + width - 1,y);
        g.drawLine(x,y,x,y + height - 1);
        g.setColor(new Color(255,255,255));
        g.drawLine(x + width - 1,y,x + width - 1,y + height - 1);
        g.drawLine(x,y + height - 1,x + width,y + height - 1);
    }
    public Insets getBorderInsets(Component c){
        return new Insets(1,1,1,1);
    }
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = 1;
        return insets;
    }
}
