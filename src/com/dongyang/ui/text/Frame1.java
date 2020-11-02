package com.dongyang.ui.text;

import java.awt.*;
import javax.swing.*;
import com.dongyang.config.TNode;
import com.dongyang.config.INode;
import java.util.Iterator;

public class Frame1 extends JPanel {

    IElement element = new TEText();
    public Frame1() {
        /*element.setText("你好aajkgWWXX");
        element.setStartPage(0);
        element.setEndPage(1);
        element.setX(100);
        element.setY(100);
        element.setWidth(100);
        element.setHeight(100);*/
        String xml = "<?xml version='1.0' encoding='GBK'?>"+
                     "<Text borderLeft='10' borderRight='10' borderUp='10' borderDown='10' x='10' y='10' width='400' height='400' color='255,0,0' startPage='1' endPage='1'>"+
                     "<Text borderLeft='10' borderRight='10' borderUp='10' borderDown='10' x='10' y='10' width='40' height='40' startPage='1' endPage='1'>" +
                     "你好\nabc"+
                     "</Text>"+
                     "<Text color='255,0,0' fontSize='20' borderLeft='10' borderRight='10' borderUp='10' borderDown='10' x='10' y='10' width='40' height='40' startPage='1' endPage='1'>" +
                     "AAABBBCCC#enter;"+
                     "</Text>"+
                     "<Table borderLeft='10' borderRight='10' borderUp='10' borderDown='10' x='10' y='10' width='300' height='300' startPage='1' endPage='1'>"+
                     "<Caption startPage='1' endPage='1' align='Center'>"+
                     "<Text fontSize='20' startPage='1' endPage='1'>"+
                     "表格标题1#enter;"+
                     "</Text>"+
                     "<Text color='255,0,0' startPage='1' endPage='1'>"+
                     "#nbsp;表格标题2#enter;"+
                     "</Text>"+
                     "</Caption>"+
                     "<TR height='40' borderLeft='10' borderRight='10' borderUp='10' borderDown='10' startPage='1' endPage='1'>"+
                     "<TD width='40' startPage='1' endPage='1'>"+
                     "<Text startPage='1' fontSize='20' endPage='1'>"+
                     "啊啊"+
                     "</Text>"+
                     "<Text startPage='1' fontSize='12' endPage='1'>"+
                     "12345 sdf  sdf sd fsdfsdfsdf"+
                     "</Text>"+
                     "</TD>"+
                     "<TD width='40' startPage='1' endPage='1'>"+
                     "<Text startPage='1' fontSize='20' endPage='1'>"+
                     "ddd sdfsdf sdfs"+
                     "</Text>"+
                     "</TD>"+
                     "</TR>"+
                     "</Table>"+
                     "<Text startPage='1' color='255,0,0' endPage='1'>"+
                     "xdsfsdfsdfsdfsdfsdfdsf"+
                     "</Text>"+
                     "</Text>";
        INode node = TNode.loadXML(xml);
        element.read(node);
        element.fine();
        System.out.println(element);
    }

    public void paint(Graphics g)
    {
        //Rectangle r = g.getClipBounds();
        //System.out.println(r);
        Graphics g1 = g.create(element.getX(),element.getY(),element.getWidth(),element.getHeight());
        element.paint(g1,1);
    }
    public static void main(String args[])
    {
        /*JFrame f = new JFrame();
        f.setSize(500,500);
        TTextUI ui = new TTextUI();
        //f.getContentPane().add(ui);
        f.getContentPane().add(new Frame1());
        f.setVisible(true);*/
        String s = "<Request><Admission><ParamType>0</ParamType><DeptCode>1101020202</DeptCode></Admission></Request>";
        INode node = TNode.loadXML(s);
        INode node1 = node.getChildElement(0);
        Iterator iterator = node1.getChildElements();
        while(iterator.hasNext())
        {
            INode node2 = (INode)iterator.next();
            System.out.println(node2.getName() + "=" + node2.getValue());
        }
    }
}
