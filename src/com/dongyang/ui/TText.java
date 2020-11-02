package com.dongyang.ui;

import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.awt.Color;
import com.dongyang.ui.base.TTextBase;

public class TText extends TTextBase{
    /**
     * 设置浏览类型
     * @param viewType String
     */
    public void setViewType(String viewType)
    {
        if(getViewType().equals(viewType))
            return;
        getTextUI().getPage().setViewType(viewType);
        getTextUI().repaint();
    }
    /**
     * 得到浏览模式
     * @return String
     */
    public String getViewType()
    {
        return getTextUI().getPage().getViewType();
    }
    public static void main(String args[])
    {
        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(new Color(255,255,255));
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(600,600);
        TText dw = new TText();
        frame.getContentPane().add(dw);
        //dw.readFile("dw-write.xml");
        dw.readFile("dw.xml");
        dw.writeFile("dw-write.xml");
        frame.setVisible(true);
        frame.setSize(600,600);
    }
}
