package com.dongyang.ui.datawindow;

import java.awt.Color;
import com.dongyang.jdo.TJDOObject;
import com.dongyang.util.StringTool;
import com.dongyang.config.TConfigParm;
import com.dongyang.config.INode;
import com.dongyang.manager.TCM_Transform;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * <p>Title: ���ͷ����</p>
 *
 * <p>Description: ���ͷ����</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.9.11
 * @version 1.0
 */
public class Header extends TJDOObject
{
    /**
     * xml����
     */
    private INode node;
    /**
     * �����ļ�����
     */
    private TConfigParm config;
    /**
     * ��ɫ����
     */
    private Color color;
    /**
     * �߶�
     */
    private double height;
    /**
     * ����xml����
     * @param node INode
     */
    public void setNode(INode node)
    {
        this.node = node;
    }
    /**
     * �õ�xml����
     * @return INode
     */
    public INode getNode()
    {
        return node;
    }
    /**
     * ���������ļ�����
     * @param config TConfigParm
     */
    public void setConfigParm(TConfigParm config)
    {
        this.config = config;
    }
    /**
     * �õ������ļ�����
     * @return TConfigParm
     */
    public TConfigParm getConfigParm()
    {
        return config;
    }
    /**
     * ������ɫ
     * @param color Color
     */
    public void setColor(Color color)
    {
        this.color = color;
        if(getNode() != null)
            node.setAttributeValue("color",getColorString());
    }
    /**
     * ������ɫ(�ַ�����ɫ)
     * @param color String "255,0,0"
     */
    public void setColor(String color)
    {
        if(getConfigParm() != null)
            setColor(StringTool.getColor(color,getConfigParm()));
        else
            setColor(StringTool.getColor(color));
    }
    /**
     * �õ���ɫ
     * @return Color
     */
    public Color getColor()
    {
        return color;
    }
    /**
     * �õ���ɫ(�ַ�����ɫ)
     * @return String "255,0,0"
     */
    public String getColorString()
    {
        return StringTool.getString(color);
    }
    /**
     * ���ø߶�
     * @param height double
     */
    public void setHeight(double height)
    {
        this.height = height;
        if(node != null)
            node.setAttributeValue("height",height);
    }
    /**
     * �õ��߶�
     * @return double
     */
    public double getHeight()
    {
        return height;
    }
    /**
     * ��ʼ��XML�����node
     * @param node INode
     */
    public void initNode(INode node)
    {
        if(node == null)
            return;
        setNode(node);
        setHeight(TCM_Transform.getDouble(node.getAttributeValue("height")));
        setColor(TCM_Transform.getString(node.getAttributeValue("color")));
    }
    public static void main(String args[])
    {
        Header header = new Header();
        header.setColor("255,0,0");
        header.setHeight(100);
        System.out.println(header);
    }
    public void paint(Graphics g)
    {
        Rectangle rectangleg = g.getClipBounds();
        Color color = getColor();
        if(color != null)
        {
            g.setColor(color);
            g.fillRect(0,0,rectangleg.width,(int)getHeight());
        }
    }
}
