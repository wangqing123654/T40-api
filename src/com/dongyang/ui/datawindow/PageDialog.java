package com.dongyang.ui.datawindow;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.print.*;
import com.dongyang.ui.base.TDataWindowBase;
/**
 *
 * <p>Title: Text属性对话窗口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 东佑项目部</p>
 * @author lzk 2006.08.09
 * @version 1.0
 */
public class PageDialog
    extends JDialog
{
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JPanel jPanel1 = new JPanel();
  JButton jButtonOK = new JButton();
  JButton jButtonCancel = new JButton();
  JButton jButtonApply = new JButton();
  JPanel jPanel2 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JLabel jLabel1 = new JLabel();
  TDataWindowBase datawindow;
  JRadioButton jRadioButton1 = new JRadioButton();
  JRadioButton jRadioButton2 = new JRadioButton();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JLabel jLabel4 = new JLabel();
  JTextField jTextField2 = new JTextField();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();
  JTextField jTextField3 = new JTextField();
  JTextField jTextField4 = new JTextField();
  JTextField jTextField5 = new JTextField();
  JTextField jTextField6 = new JTextField();
  ButtonGroup buttonGroup1 = new ButtonGroup();
  JToggleButton jToggleButton1 = new JToggleButton();
  int orientation;
  /**
   * 构造器
   */
  public PageDialog(TDataWindowBase datawindow) {
    this.datawindow = datawindow;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * 初始化
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("页面属性");
    setSize(340, 440);
    Rectangle screenRect = getGraphicsConfiguration().getBounds();
    Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
        getGraphicsConfiguration());
    int centerWidth = screenRect.width < getSize().width ?
        screenRect.x :
        screenRect.x + screenRect.width / 2 - getSize().width / 2;
    int centerHeight = screenRect.height < getSize().height ?
        screenRect.y :
        screenRect.y + screenRect.height / 2 - getSize().height / 2;

    centerHeight = centerHeight < screenInsets.top ?
        screenInsets.top : centerHeight;
    setLocation(centerWidth, centerHeight);
    jButtonOK.setFont(new java.awt.Font("Dialog", 0, 12));
    jButtonOK.setSelected(true);
    jButtonOK.setText("确定");
    jButtonOK.addActionListener(new Button_actionAdapter());
    jButtonCancel.setFont(new java.awt.Font("Dialog", 0, 12));
    jButtonCancel.setText("取消");
    jButtonCancel.addActionListener(new Button_actionAdapter());
    jButtonApply.setFont(new java.awt.Font("Dialog", 0, 12));
    jButtonApply.setText("应用");
    jButtonApply.addActionListener(new Button_actionAdapter());
    jTabbedPane1.setFont(new java.awt.Font("Dialog", 0, 12));
    jPanel1.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    jPanel2.setLayout(null);
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel1.setVerifyInputWhenFocusTarget(true);
    jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel1.setText("纸张方向:");
    jLabel1.setBounds(new Rectangle(19, 16, 66, 16));
    jRadioButton1.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton1.setText("纵向");
    jRadioButton1.setBounds(new Rectangle(41, 35, 57, 25));
    jRadioButton1.addActionListener(new PageDialog_jRadioButton1_actionAdapter(this));
    jRadioButton2.setBounds(new Rectangle(100, 35, 57, 25));
    jRadioButton2.addActionListener(new PageDialog_jRadioButton2_actionAdapter(this));
    jRadioButton2.setText("横向");
    jRadioButton2.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel2.setBounds(new Rectangle(19, 67, 66, 16));
    jLabel2.setText("纸张大小:");
    jLabel2.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel2.setVerifyInputWhenFocusTarget(true);
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel3.setVerifyInputWhenFocusTarget(true);
    jLabel3.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel3.setText("宽度:");
    jLabel3.setBounds(new Rectangle(23, 91, 39, 16));
    jTextField1.setText("");
    jTextField1.setBounds(new Rectangle(67, 89, 204, 21));
    jLabel4.setBounds(new Rectangle(23, 121, 39, 16));
    jLabel4.setText("高度:");
    jLabel4.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel4.setVerifyInputWhenFocusTarget(true);
    jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
    jTextField2.setBounds(new Rectangle(67, 119, 204, 21));
    jTextField2.setText("");
    jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel5.setVerifyInputWhenFocusTarget(true);
    jLabel5.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel5.setText("页边距:");
    jLabel5.setBounds(new Rectangle(19, 149, 66, 16));
    jLabel6.setBounds(new Rectangle(36, 177, 27, 16));
    jLabel6.setText("左:");
    jLabel6.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel6.setVerifyInputWhenFocusTarget(true);
    jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel7.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel7.setVerifyInputWhenFocusTarget(true);
    jLabel7.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel7.setText("右:");
    jLabel7.setBounds(new Rectangle(38, 235, 27, 16));
    jLabel8.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel8.setVerifyInputWhenFocusTarget(true);
    jLabel8.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel8.setText("上:");
    jLabel8.setBounds(new Rectangle(36, 206, 27, 16));
    jLabel9.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel9.setVerifyInputWhenFocusTarget(true);
    jLabel9.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel9.setText("下:");
    jLabel9.setBounds(new Rectangle(38, 264, 27, 16));
    jTextField3.setText("");
    jTextField3.setBounds(new Rectangle(68, 177, 204, 21));
    jTextField4.setText("");
    jTextField4.setBounds(new Rectangle(68, 235, 204, 21));
    jTextField5.setText("");
    jTextField5.setBounds(new Rectangle(68, 206, 204, 21));
    jTextField6.setText("");
    jTextField6.setBounds(new Rectangle(68, 264, 204, 21));
    jToggleButton1.setFont(new java.awt.Font("Dialog", 0, 12));
    jToggleButton1.setText("打印设置");
    jToggleButton1.setBounds(new Rectangle(211, 295, 103, 25));
    jToggleButton1.addActionListener(new PageDialog_jToggleButton1_actionAdapter(this));
    this.getContentPane().add(jTabbedPane1, BorderLayout.CENTER);
    jTabbedPane1.add(jPanel2, "一般");
    jPanel2.add(jLabel1, null);
    jPanel2.add(jRadioButton1, null);
    jPanel2.add(jRadioButton2, null);
    jPanel2.add(jLabel2, null);
    jPanel2.add(jTextField1, null);
    jPanel2.add(jLabel4, null);
    jPanel2.add(jTextField2, null);
    jPanel2.add(jLabel3, null);
    jPanel2.add(jLabel5, null);
    jPanel2.add(jTextField3, null);
    jPanel2.add(jTextField5, null);
    jPanel2.add(jLabel6, null);
    jPanel2.add(jLabel8, null);
    jPanel2.add(jTextField4, null);
    jPanel2.add(jTextField6, null);
    jPanel2.add(jLabel7, null);
    jPanel2.add(jLabel9, null);
    jPanel2.add(jToggleButton1, null);
    this.getContentPane().add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(jButtonOK, null);
    jPanel1.add(jButtonCancel, null);
    jPanel1.add(jButtonApply, null);

    //加载对象数据
    onInitObjectData();
    buttonGroup1.add(jRadioButton2);
    buttonGroup1.add(jRadioButton1);
  }
  /**
   * 加载对象数据
   */
  private void onInitObjectData()
  {

    double x = datawindow.getPrintNode().getAttributeValueAsDouble("ImageableX").
        doubleValue();
    double y = datawindow.getPrintNode().getAttributeValueAsDouble("ImageableY").
        doubleValue();
    double w = datawindow.getPrintNode().getAttributeValueAsDouble(
        "ImageableWidth").
        doubleValue();
    double h = datawindow.getPrintNode().getAttributeValueAsDouble(
        "ImageableHeight").
        doubleValue();
    double width = datawindow.getPrintNode().getAttributeValueAsDouble("Width").
        doubleValue();
    double height = datawindow.getPrintNode().getAttributeValueAsDouble("Height").
        doubleValue();
    jTextField1.setText("" + width);
    jTextField2.setText("" + height);
    jTextField3.setText("" + x);
    jTextField5.setText("" + y);
    jTextField4.setText("" + (width - w - x));
    jTextField6.setText("" + (height - h - y));
    switch (datawindow.getPrintNode().getAttributeValueAsDouble("Orientation").
            intValue())
    {
      case PageFormat.PORTRAIT: //纵向
        jRadioButton1.setSelected(true);
        jRadioButton2.setSelected(false);
        orientation = PageFormat.PORTRAIT;
        break;
      case PageFormat.REVERSE_LANDSCAPE: //横向
        jRadioButton2.setSelected(true);
        jRadioButton1.setSelected(false);
        orientation = PageFormat.REVERSE_LANDSCAPE;
        break;
    }

  }

  /**
   * 确定
   */
  void onOK()
  {
    //应用
    onApply();
    //关闭
    dispose();
  }
  /**
   * 取消
   */
  void onCancel()
  {
    //关闭
    dispose();
  }
  /**
   * 应用
   */
  void onApply()
  {
    double width = Double.parseDouble(jTextField1.getText());
    double height = Double.parseDouble(jTextField2.getText());
    double x = Double.parseDouble(jTextField3.getText());
    double y = Double.parseDouble(jTextField5.getText());
    double w =  Double.parseDouble(jTextField4.getText());
    double h =  Double.parseDouble(jTextField6.getText());
    datawindow.getPrintNode().setAttributeValue("ImageableX", x);
    datawindow.getPrintNode().setAttributeValue("ImageableY", y);
    datawindow.getPrintNode().setAttributeValue("ImageableWidth", width - x - w);
    datawindow.getPrintNode().setAttributeValue("ImageableHeight", height - y - h);
    datawindow.getPrintNode().setAttributeValue("Width", width);
    datawindow.getPrintNode().setAttributeValue("Height", height);
    datawindow.getPrintNode().setAttributeValue("Orientation",jRadioButton1.isSelected()
                                               ?PageFormat.PORTRAIT:PageFormat.REVERSE_LANDSCAPE);
    datawindow.repaint();
  }
  /**
   * <p>Title: 按钮动作倾听类</p>
   * <p>Description: </p>
   * <p>Copyright: Copyright (c) 2006</p>
   * <p>Company: 东佑项目部</p>
   * @author lzk 2006.08.10
   * @version 1.0
   */
  class Button_actionAdapter implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      if(e.getSource() == jButtonOK)
        onOK();
      else if(e.getSource() == jButtonCancel)
        onCancel();
      else if(e.getSource() == jButtonApply)
        onApply();
    }
  }

  void jToggleButton1_actionPerformed(ActionEvent e) {
    datawindow.printSetup();
    onInitObjectData();
  }

  void jRadioButton1_actionPerformed(ActionEvent e) {
    if(orientation != PageFormat.PORTRAIT)
    {
      String temp = jTextField1.getText();
      jTextField1.setText(jTextField2.getText());
      jTextField2.setText(temp);
      temp = jTextField3.getText();
      jTextField3.setText(jTextField5.getText());
      jTextField5.setText(temp);
      temp = jTextField4.getText();
      jTextField4.setText(jTextField6.getText());
      jTextField6.setText(temp);
    }
    orientation = PageFormat.PORTRAIT;
  }

  void jRadioButton2_actionPerformed(ActionEvent e) {
    if(orientation != PageFormat.REVERSE_LANDSCAPE)
    {
      String temp = jTextField1.getText();
      jTextField1.setText(jTextField2.getText());
      jTextField2.setText(temp);
      temp = jTextField3.getText();
      jTextField3.setText(jTextField5.getText());
      jTextField5.setText(temp);
      temp = jTextField4.getText();
      jTextField4.setText(jTextField6.getText());
      jTextField6.setText(temp);
    }
    orientation = PageFormat.REVERSE_LANDSCAPE;
  }

}

class PageDialog_jToggleButton1_actionAdapter implements java.awt.event.ActionListener {
  PageDialog adaptee;

  PageDialog_jToggleButton1_actionAdapter(PageDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jToggleButton1_actionPerformed(e);
  }
}

class PageDialog_jRadioButton1_actionAdapter implements java.awt.event.ActionListener {
  PageDialog adaptee;

  PageDialog_jRadioButton1_actionAdapter(PageDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jRadioButton1_actionPerformed(e);
  }
}

class PageDialog_jRadioButton2_actionAdapter implements java.awt.event.ActionListener {
  PageDialog adaptee;

  PageDialog_jRadioButton2_actionAdapter(PageDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jRadioButton2_actionPerformed(e);
  }
}
