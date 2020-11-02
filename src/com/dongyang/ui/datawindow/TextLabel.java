package com.dongyang.ui.datawindow;

import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Insets;

public class TextLabel extends JLabel
{
    private boolean transparence;
    /**
     * �������
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
     * �����Ƿ�͸��
     * @param transparence boolean true ͸�� fase ��͸��
     */
    public void setTransparence(boolean transparence)
    {
        this.transparence = transparence;
        repaint();
    }

    /**
     * �Ƿ�͸��
     * @return boolean true ͸�� fase ��͸��
     */
    public boolean isTransparence()
    {
        return transparence;
    }
}
