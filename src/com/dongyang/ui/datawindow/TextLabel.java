package com.dongyang.ui.datawindow;

import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Insets;

public class TextLabel extends JLabel
{
    private boolean transparence;
    /**
     * 绘制组件
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        if (!isTransparence())
        {
            Insets insets = getBorder().getBorderInsets(this);
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth() - insets.right,
                       getHeight() - insets.bottom);
        }
        super.paint(g);
    }

    /**
     * 设置是否透明
     * @param transparence boolean true 透明 fase 不透明
     */
    public void setTransparence(boolean transparence)
    {
        this.transparence = transparence;
        repaint();
    }

    /**
     * 是否透明
     * @return boolean true 透明 fase 不透明
     */
    public boolean isTransparence()
    {
        return transparence;
    }
}
