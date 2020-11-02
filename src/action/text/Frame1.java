package action.text;

import java.awt.*;

import javax.swing.*;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.dongyang.ui.TMovePane;
import com.dongyang.ui.TButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.TextUI;
import javax.swing.plaf.basic.BasicTextUI;

public class Frame1 extends JFrame {
    public Frame1() {
        UIManager.put("TextField.inactiveForeground", Color.red);
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(null);
        //tMovePane1.resizeEntity();
        this.setSize(400,400);
        setVisible(true);
        jTextField1.setEnabled(false);
        jTextField1.setText("jTextField1");
        jTextField1.setBounds(new Rectangle(64, 105, 250, 27));
        this.getContentPane().add(jTextField1);
        jTextField1.setDisabledTextColor(Color.red);
        BasicTextUI ui = (BasicTextUI)jTextField1.getUI();

    }

    public static void main(String[] args) {
        Frame1 frame1 = new Frame1();
    }

    JTextField jTextField1 = new JTextField();

}

