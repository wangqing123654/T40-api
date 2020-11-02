package test;

import java.awt.*;

import javax.swing.*;
import com.dongyang.ui.TButton;
import java.awt.BorderLayout;
import com.dongyang.config.TConfig;
import com.dongyang.ui.TPanel;
import java.awt.Rectangle;
import com.dongyang.config.TConfigParm;
import com.dongyang.util.Log;
import com.dongyang.util.DynamicClassLoader;
import com.dongyang.ui.TSelectBlock;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class Frame1 extends JFrame {

    public Frame1() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.setTabPlacement(JTabbedPane.LEFT);
        jTabbedPane1.add(jPanel1, "jPanel1");
        jTabbedPane1.add(jPanel2, "jPanel2");
        jTabbedPane1.add(jPanel3, "jPanel3");
    }
    public static void main(String args[])
    {
        try{
            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch(Exception e)
        {
        }
        Frame1 f = new Frame1();
        f.setSize(400,400);
        f.setVisible(true);
    }

    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
}

