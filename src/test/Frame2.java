package test;

import java.awt.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.Format;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.InternationalFormatter;
import java.text.AttributedCharacterIterator;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyEvent;
import java.awt.Font;

public class Frame2 extends JFrame {
    public Frame2() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(null);
        this.getContentPane().setBackground(SystemColor.textHighlight);
        jComboBox1.setBounds(new Rectangle(59, 91, 140, 23));
        jComboBox1.addActionListener(new Frame2_jComboBox1_actionAdapter(this));
        //jFormattedTextField1.setText("jFormattedTextField1");
        DecimalFormat df1 = new DecimalFormat("####0.000");
        jFormattedTextField1.setFormatterFactory(
            new DefaultFormatterFactory(new InternationalFormatter(df1)));
        //jFormattedTextField1.setformat
        jFormattedTextField1.setBounds(new Rectangle(56, 158, 126, 23));
        jLabel1.setFont(new java.awt.Font("²©Ñó²ÝÊé3500", Font.PLAIN, 12));
        jLabel1.setText("jLabel1");
        jLabel1.setBounds(new Rectangle(46, 202, 196, 36));
        jTextField1.setText("jTextField1");
        jTextField1.setBounds(new Rectangle(163, 219, 77, 20));
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("jRadioButton1");
        jRadioButton1.setBounds(new Rectangle(114, 24, 103, 23));
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("jRadioButton2");
        jRadioButton2.setBounds(new Rectangle(117, 57, 103, 23));
        this.getContentPane().add(jComboBox1);
        this.getContentPane().add(jFormattedTextField1);
        this.getContentPane().add(jLabel1);
        this.getContentPane().add(jTextField1);
        this.getContentPane().add(jRadioButton1);
        this.getContentPane().add(jRadioButton2);
        jComboBox1.addItem("aaa");
        jComboBox1.addItem("bbbb");
        jComboBox1.addItem("cccc");
        jComboBox1.setEditable(true);
        this.setSize(300,300);
        jTextField1.setInputVerifier(null);
        buttonGroup1.add(jRadioButton1);
        buttonGroup1.add(jRadioButton2);
        System.out.println(jRadioButton1.getModel().getClass().getName());

    }

    JComboBox jComboBox1 = new JComboBox();
    JFormattedTextField jFormattedTextField1 = new JFormattedTextField1();
    JLabel jLabel1 = new JLabel();
    JTextField jTextField1 = new JTextField();
    JRadioButton jRadioButton1 = new JRadioButton();
    ButtonGroup buttonGroup1 = new ButtonGroup();
    JRadioButton jRadioButton2 = new JRadioButton();
    public void jComboBox1_actionPerformed(ActionEvent e) {
        System.out.println(e);
        System.out.println(jComboBox1.getSelectedItem());
    }
}
class JFormattedTextField1 extends JFormattedTextField
{
    int leftcount = 5;
    int rightcount = 3;
    protected boolean processKeyBinding(KeyStroke ks, KeyEvent e,
                                        int condition, boolean pressed) {
        if(e.isActionKey() || e.isControlDown() || e.isAltDown())
            return super.processKeyBinding(ks, e, condition, pressed);
        if(e.isShiftDown() && (e.getKeyCode() == KeyEvent.VK_LEFT||
                               e.getKeyCode() == KeyEvent.VK_RIGHT))
            return super.processKeyBinding(ks, e, condition, pressed);
        if(e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
            return super.processKeyBinding(ks, e, condition, pressed);
        if(!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9' ||
             e.getKeyChar() == '.' || e.getKeyChar() == '-'))
            return true;
        if(getSelectionStart() > 0 && e.getKeyChar() == '-')
            return true;
        int d = getText().indexOf(".");
        int l = getSelectionEnd() - getSelectionStart();
        if(d!= -1 && e.getKeyChar() == '.')
            return true;
        if(d == -1)
            d = getText().length();
        if(getSelectionStart() <= d)
            if(d + 1 > leftcount + l && e.getKeyChar() != '.')
                return true;
        if(getSelectionStart() > d)
            if(getText().length() - d > rightcount + l)
                return true;

        boolean retValue = super.processKeyBinding(ks, e, condition, pressed);

        return retValue;
    }
}

class Frame2_jComboBox1_actionAdapter implements ActionListener {
    private Frame2 adaptee;
    Frame2_jComboBox1_actionAdapter(Frame2 adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jComboBox1_actionPerformed(e);
    }
    public static void main(String args[])
    {
        new Frame2().setVisible(true);
    }
}
