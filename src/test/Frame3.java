package test;

import java.awt.*;

import javax.swing.*;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame3 extends JFrame
{
    public Frame3()
    {
        try
        {
            jbInit();
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception
    {
        getContentPane().setLayout(null);
        jScrollPane1.setBounds(new Rectangle(49, 48, 278, 184));
        jButton1.setBounds(new Rectangle(229, 251, 81, 23));
        jButton1.setText("jButton1");
        jButton1.addActionListener(new Frame3_jButton1_actionAdapter(this));
        this.getContentPane().add(jScrollPane1);
        this.getContentPane().add(jButton1);
        jScrollPane1.getViewport().add(jTextArea1);
        jTextArea1.setText("jTextArea1");
        setSize(400,400);
    }

    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea jTextArea1 = new JTextArea();
    JButton jButton1 = new JButton();
    public void jButton1_actionPerformed(ActionEvent e)
    {
        System.out.println(jScrollPane1.getViewport().getViewPosition());

    }
    public static void main(String args[])
    {
        new Frame3().setVisible(true);
    }
}


class Frame3_jButton1_actionAdapter implements ActionListener
{
    private Frame3 adaptee;
    Frame3_jButton1_actionAdapter(Frame3 adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton1_actionPerformed(e);
    }
}
