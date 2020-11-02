package com.dongyang.root.T;

import java.awt.Frame;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.dongyang.root.T.B.ObBase;
import com.dongyang.root.T.B.Obf;

public class Fo extends Frame
        implements WindowListener
{
    public Fo()
    {
        try
        {
            jbInit();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void windowOpened(WindowEvent windowevent)
    {
    }

    public void windowClosing(WindowEvent event)
    {
      dispose();
    }

    public void windowClosed(WindowEvent event)
    {
    }

    public void windowIconified(WindowEvent windowevent)
    {
    }

    public void windowDeiconified(WindowEvent windowevent)
    {
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
    }
    public static void main(String args[])
    {
        new Fo();
    }

    private void jbInit() throws Exception
    {
        this.setLayout(null);
        jButton1.setBounds(new Rectangle(87, 60, 81, 23));
        jButton1.setText("jButton1");
        jButton1.addActionListener(new Fo_jButton1_actionAdapter(this));
        jButton2.setBounds(new Rectangle(87, 94, 81, 23));
        jButton2.setText("jButton2");
        jButton2.addActionListener(new Fo_jButton2_actionAdapter(this));
        jButton3.setBounds(new Rectangle(89, 128, 81, 23));
        jButton3.setText("jButton3");
        jButton3.addActionListener(new Fo_jButton3_actionAdapter(this));
        this.add(jButton1);
        this.add(jButton2);
        this.add(jButton3);
        addWindowListener(this);
        setSize(400,400);
        setVisible(true);
    }

    Ot ot1;
    Ot ot2;
    Ot ot3;
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    public void jButton1_actionPerformed(ActionEvent e)
    {
        if(ot1 != null)
        {
            ot1.getOb().stopLife();
            ot1 = null;
            return;
        }
        Od od = new Od();
        ot1 = new Ot();
        ot1.setID(0);
        ot1.setOd(od);
        Obf obf = new Obf();
        obf.setObject(jButton1);
        ot1.setOb(obf);
        ot1.getOb().startLife();
    }

    public void jButton2_actionPerformed(ActionEvent e)
    {
        if(ot2 != null)
        {
            ot2.getOb().stopLife();
            ot2 = null;
            return;
        }
        Od od = new Od();
        ot2 = new Ot();
        ot2.setID(0);
        ot2.setOd(od);
        Obf obf = new Obf();
        obf.setObject(jButton2);
        ot2.setOb(obf);
        ot2.getOb().startLife();
    }

    public void jButton3_actionPerformed(ActionEvent e)
    {
        if(ot3 != null)
        {
            ot3.getOb().stopLife();
            ot3 = null;
            return;
        }
        Od od = new Od();
        ot3 = new Ot();
        ot3.setID(0);
        ot3.setOd(od);
        Obf obf = new Obf();
        obf.setObject(jButton3);
        ot3.setOb(obf);
        ot3.getOb().startLife();
    }

}


class Fo_jButton3_actionAdapter implements ActionListener
{
    private Fo adaptee;
    Fo_jButton3_actionAdapter(Fo adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton3_actionPerformed(e);
    }
}


class Fo_jButton2_actionAdapter implements ActionListener
{
    private Fo adaptee;
    Fo_jButton2_actionAdapter(Fo adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton2_actionPerformed(e);
    }
}


class Fo_jButton1_actionAdapter implements ActionListener
{
    private Fo adaptee;
    Fo_jButton1_actionAdapter(Fo adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton1_actionPerformed(e);
    }
}
