package com.dongyang.tui.text.graph;

import java.awt.Color;
import java.awt.Stroke;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Graphics;
import com.dongyang.tui.text.EPicData;
import java.awt.Font;
import java.awt.FontMetrics;
import com.dongyang.util.StringTool;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: ����</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.5.27
 * @version 1.0
 */
public class Axis
{
    /**
     * ����
     */
    private EPicData parent;
    /**
     * ��ʾ
     */
    private boolean visible = true;
    /**
     * �᷽��
     * true ���� false ����
     */
    private boolean way = true;
    /**
     * ��������
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    private int mainLineType = 0;
    /**
     * �����߿�
     */
    private float mainLineWidth = 0.5f;
    /**
     * ���������߼�
     */
    private float mainLineWidth1 = 5.5f;
    /**
     * ������ɫ
     */
    private Color mainLineColor = new Color(0,0,0);
    /**
     * ��Сֵ
     */
    private double minValue = 0;
    /**
     * ���ֵ
     */
    private double maxValue = 100;
    /**
     * ���̶ȵ�λ
     */
    private double mainUnit = 10;
    /**
     * �ο̶ȵ�λ
     */
    private double secondUnit = 2;
    /**
     * ���̶ȿ��
     */
    private int mainWidth = 5;
    /**
     * �ο̶ȿ��
     */
    private int secondWidth = 2;
    /**
     * ����߷���
     * 1 ��/��
     * 2 ��/��
     * 3 ����/����
     */
    private int mainTypeWay = 1;
    /**
     * �α�߷���
     * 1 ��/��
     * 2 ��/��
     * 3 ����/����
     */
    private int secondTypeWay = 1;
    /**
     * ���̶�����
     * 0 ����ʾ
     * 1 �ⲿ
     * 2 �ڲ�
     * 3 ����
     */
    private int mainType = 1;
    /**
     * �ο̶�����
     * 0 ����ʾ
     * 1 �ⲿ
     * 2 �ڲ�
     * 3 ����
     */
    private int secondType = 2;
    /**
     * ���̶�������
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    private int mainAxisLineType = 0;
    /**
     * ���̶��߿�
     */
    private float mainAxisLineWidth = 0.5f;
    /**
     * ���̶������߼�
     */
    private float mainAxisLineWidth1 = 5.5f;
    /**
     * ���̶���ɫ
     */
    private Color mainAxisLineColor = new Color(0,0,0);
    /**
     * �ο̶�������
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    private int secondAxisLineType = 0;
    /**
     * �ο̶��߿�
     */
    private float secondAxisLineWidth = 0.5f;
    /**
     * �ο̶������߼�
     */
    private float secondAxisLineWidth1 = 5f;
    /**
     * �ο̶���ɫ
     */
    private Color secondAxisLineColor = new Color(0,0,0);
    /**
     * ��ʾ��������
     */
    private boolean backMainVisible = true;
    /**
     * ��������������
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    private int backMainLineType = 0;
    /**
     * �������߿��
     */
    private float backMainLineWidth = 1.0f;
    /**
     * �������������߼�
     */
    private float backMainLineWidth1 = 5f;
    /**
     * ����������ɫ
     */
    private Color backMainLineColor = new Color(0,0,0);
    /**
     * ��ʾ��������
     */
    private boolean backSecondVisible = false;
    /**
     * ��������������
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    private int backSecondLineType = 0;
    /**
     * �������߿��
     */
    private float backSecondLineWidth = 1.0f;
    /**
     * �������������߼�
     */
    private float backSecondLineWidth1 = 5f;
    /**
     * ����������ɫ
     */
    private Color backSecondLineColor = new Color(0,0,0);
    /**
     * �Ƿ�������
     */
    private boolean isNumberAxis;
    /**
     * ������ɫ
     */
    private Color textColor = new Color(0,0,0);
    /**
     * ��������
     */
    private Font textFont = new Font("����",0,10);
    /**
     * ������ת�Ƕ�
     */
    private double textFontRotate = 0.0;
    /**
     * ���ַ���
     * 1 ��/��
     * 2 ��/��
     * 3 ����/����
     */
    private int textWay = 1;
    /**
     * ����ƫ��λ��
     */
    private int textOffset = 5;
    /**
     * �Զ��س�
     */
    private boolean textAutoEnter = false;
    /**
     * �Զ��س�ƫ��
     */
    private int textEnterOffset;
    /**
     * ƫ�Ƹ�ʽ
     */
    private String textOffsetFormat = "####0";
    /**
     * ���䷽ʽ
     * 0 ����
     * 1 ��ת�޲�
     */
    private int textAlign = 0;
    /**
     * �����м�
     */
    private boolean textCenter = false;
    /**
     * ������
     */
    public Axis()
    {

    }
    /**
     * ������
     * @param parent EPicData
     */
    public Axis(EPicData parent)
    {
        setParent(parent);
    }
    /**
     * ���ø���
     * @param parent EPicData
     */
    public void setParent(EPicData parent)
    {
        this.parent = parent;
    }
    /**
     * �õ�����
     * @return EPicData
     */
    public EPicData getParent()
    {
        return parent;
    }
    /**
     * �õ�X����
     * @return int
     */
    public int getX()
    {
        return getParent().getX();
    }
    /**
     * �õ�Y����
     * @return int
     */
    public int getY()
    {
        return getParent().getY();
    }
    /**
     * �õ����
     * @return int
     */
    public int getWidth()
    {
        return getParent().getWidth();
    }
    /**
     * �õ��߶�
     * @return int
     */
    public int getHeight()
    {
        return getParent().getHeight();
    }
    /**
     * �õ�����
     * @param index int
     * @return String
     */
    public String getColumnName(int index)
    {
        String s[] = getParent().getColumns();
        if(s == null || s.length == 0 || s.length <= index)
            return "";
        return s[index];
    }
    /**
     * ������ʾ
     * @param visible boolean
     */
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    /**
     * �Ƿ���ʾ
     * @return boolean
     */
    public boolean isVisible()
    {
        return visible;
    }
    /**
     * �����᷽��
     * @param way boolean true ���� false ����
     */
    public void setWay(boolean way)
    {
        this.way = way;
    }
    /**
     * �õ��᷽��
     * @return boolean true ���� false ����
     */
    public boolean getWay()
    {
        return way;
    }
    /**
     * ������������
     * @param mainLineType int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public void setMainLineType(int mainLineType)
    {
        this.mainLineType = mainLineType;
    }
    /**
     * �õ���������
     * @return int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public int getMainLineType()
    {
        return mainLineType;
    }
    /**
     * ���������߿�
     * @param mainLineWidth float
     */
    public void setMainLineWidth(float mainLineWidth)
    {
        this.mainLineWidth = mainLineWidth;
    }
    /**
     * �õ������߿�
     * @return float
     */
    public float getMainLineWidth()
    {
        return mainLineWidth;
    }
    /**
     * �������������߼�
     * @param mainLineWidth1 float
     */
    public void setMainLineWidth1(float mainLineWidth1)
    {
        this.mainLineWidth1 = mainLineWidth1;
    }
    /**
     * �õ����������߼�
     * @return float
     */
    public float getMainLineWidth1()
    {
        return mainLineWidth1;
    }
    /**
     * ����������ɫ
     * @param mainLineColor Color
     */
    public void setMainLineColor(Color mainLineColor)
    {
        this.mainLineColor = mainLineColor;
    }
    /**
     * �õ�������ɫ
     * @return Color
     */
    public Color getMainLineColor()
    {
        return mainLineColor;
    }
    /**
     * �õ�������ɫ(String)
     * @return String
     */
    public String getMainLineColorString()
    {
        if(mainLineColor == null)
            return "";
        return mainLineColor.getRed() + "," + mainLineColor.getGreen() + "," + mainLineColor.getBlue();
    }
    /**
     * ������Сֵ
     * @param minValue double
     */
    public void setMinValue(double minValue)
    {
        this.minValue = minValue;
    }
    /**
     * �õ���Сֵ
     * @return double
     */
    public double getMinValue()
    {
        return minValue;
    }
    /**
     * �������ֵ
     * @param maxValue double
     */
    public void setMaxValue(double maxValue)
    {
        this.maxValue = maxValue;
    }
    /**
     * �õ����ֵ
     * @return double
     */
    public double getMaxValue()
    {
        return maxValue;
    }
    /**
     * �������̶ȵ�λ
     * @param mainUnit double
     */
    public void setMainUnit(double mainUnit)
    {
        this.mainUnit = mainUnit;
    }
    /**
     * �õ����̶ȵ�λ
     * @return double
     */
    public double getMainUnit()
    {
        return mainUnit;
    }
    /**
     * ���ôο̶ȵ�λ
     * @param secondUnit double
     */
    public void setSecondUnit(double secondUnit)
    {
        this.secondUnit = secondUnit;
    }
    /**
     * �õ��ο̶ȵ�λ
     * @return double
     */
    public double getSecondUnit()
    {
        return secondUnit;
    }
    /**
     * �������̶ȿ��
     * @param mainWidth int
     */
    public void setMainWidth(int mainWidth)
    {
        this.mainWidth = mainWidth;
    }
    /**
     * �õ����̶ȿ��
     * @return int
     */
    public int getMainWidth()
    {
        return mainWidth;
    }
    /**
     * ���ôο̶ȿ��
     * @param secondWidth int
     */
    public void setSecondWidth(int secondWidth)
    {
        this.secondWidth = secondWidth;
    }
    /**
     * �õ��ο̶ȿ��
     * @return int
     */
    public int getSecondWidth()
    {
        return secondWidth;
    }
    /**
     * �������̶�����
     * @param mainType int
     * 0 ����ʾ
     * 1 �ⲿ
     * 2 �ڲ�
     * 3 ����
     */
    public void setMainType(int mainType)
    {
        this.mainType = mainType;
    }
    /**
     * �õ����̶�����
     * @return int
     * 0 ����ʾ
     * 1 �ⲿ
     * 2 �ڲ�
     * 3 ����
     */
    public int getMainType()
    {
        return mainType;
    }
    /**
     * ���ôο̶�����
     * @param secondType int
     * 0 ����ʾ
     * 1 �ⲿ
     * 2 �ڲ�
     * 3 ����
     */
    public void setSecondType(int secondType)
    {
        this.secondType = secondType;
    }
    /**
     * �õ��ο̶�����
     * @return int
     * 0 ����ʾ
     * 1 �ⲿ
     * 2 �ڲ�
     * 3 ����
     */
    public int getSecondType()
    {
        return secondType;
    }
    /**
     * �������̶�������
     * @param mainAxisLineType int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public void setMainAxisLineType(int mainAxisLineType)
    {
        this.mainAxisLineType = mainAxisLineType;
    }
    /**
     * �õ����̶�������
     * @return int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public int getMainAxisLineType()
    {
        return mainAxisLineType;
    }
    /**
     * �������̶��߿�
     * @param mainAxisLineWidth float
     */
    public void setMainAxisLineWidth(float mainAxisLineWidth)
    {
        this.mainAxisLineWidth = mainAxisLineWidth;
    }
    /**
     * �õ����̶��߿�
     * @return float
     */
    public float getMainAxisLineWidth()
    {
        return mainAxisLineWidth;
    }
    /**
     * �������̶������߼�
     * @param mainAxisLineWidth1 float
     */
    public void setMainAxisLineWidth1(float mainAxisLineWidth1)
    {
        this.mainAxisLineWidth1 = mainAxisLineWidth1;
    }
    /**
     * �õ����̶������߼�
     * @return float
     */
    public float getMainAxisLineWidth1()
    {
        return mainAxisLineWidth1;
    }
    /**
     * �������̶���ɫ
     * @param mainAxisLineColor Color
     */
    public void setMainAxisLineColor(Color mainAxisLineColor)
    {
        this.mainAxisLineColor = mainAxisLineColor;
    }
    /**
     * �õ����̶���ɫ
     * @return Color
     */
    public Color getMainAxisLineColor()
    {
        return mainAxisLineColor;
    }
    /**
     * �õ����̶���ɫ(String)
     * @return String
     */
    public String getMainAxisLineColorString()
    {
        if(mainAxisLineColor == null)
            return "";
        return mainAxisLineColor.getRed() + "," + mainAxisLineColor.getGreen() + "," + mainAxisLineColor.getBlue();
    }
    /**
     * ���ôο̶�������
     * @param secondAxisLineType int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public void setSecondAxisLineType(int secondAxisLineType)
    {
        this.secondAxisLineType = secondAxisLineType;
    }
    /**
     * �õ��ο̶�������
     * @return int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public int getSecondAxisLineType()
    {
        return secondAxisLineType;
    }
    /**
     * ���ôο̶��߿�
     * @param secondAxisLineWidth float
     */
    public void setSecondAxisLineWidth(float secondAxisLineWidth)
    {
        this.secondAxisLineWidth = secondAxisLineWidth;
    }
    /**
     * �õ��ο̶��߿�
     * @return float
     */
    public float getSecondAxisLineWidth()
    {
        return secondAxisLineWidth;
    }
    /**
     * ���ôο̶������߼�
     * @param secondAxisLineWidth1 float
     */
    public void setSecondAxisLineWidth1(float secondAxisLineWidth1)
    {
        this.secondAxisLineWidth1 = secondAxisLineWidth1;
    }
    /**
     * �õ��ο̶������߼�
     * @return float
     */
    public float getSecondAxisLineWidth1()
    {
        return secondAxisLineWidth1;
    }
    /**
     * ���ôο̶���ɫ
     * @param secondAxisLineColor Color
     */
    public void setSecondAxisLineColor(Color secondAxisLineColor)
    {
        this.secondAxisLineColor = secondAxisLineColor;
    }
    /**
     * �õ��ο̶���ɫ
     * @return Color
     */
    public Color getSecondAxisLineColor()
    {
        return secondAxisLineColor;
    }
    /**
     * �õ��ο̶���ɫ(String)
     * @return String
     */
    public String getSecondAxisLineColorString()
    {
        if(secondAxisLineColor == null)
            return "";
        return secondAxisLineColor.getRed() + "," + secondAxisLineColor.getGreen() + "," + secondAxisLineColor.getBlue();
    }
    /**
     * �õ���ֵ
     * @return double
     */
    public double getValue()
    {
        return getMaxValue() - getMinValue();
    }
    /**
     * �õ����̶ȸ���
     * @return int
     */
    public int getMainCount()
    {
        return (int)(getValue() / getMainUnit());
    }
    /**
     * �õ��ο̶ȸ���
     * @return int
     */
    public int getSecondCount()
    {
        return (int)(getValue() / getSecondUnit());
    }
    /**
     * ��������λ����
     * @param height int
     * @return double
     */
    public double getDrawMainUnit(int height)
    {
        double value = getValue();
        if(value == 0)
            return 0;
        return (double)height / value * getMainUnit();
    }
    /**
     * ����ε�λ����
     * @param height int
     * @return double
     */
    public double getDrawSecondUnit(int height)
    {
        double value = getValue();
        if(value == 0)
            return 0;
        return (double)height / value * getSecondUnit();
    }
    /**
     * ������ʾ��������
     * @param backMainVisible boolean
     */
    public void setBackMainVisible(boolean backMainVisible)
    {
        this.backMainVisible = backMainVisible;
    }
    /**
     * �õ���ʾ��������
     * @return boolean
     */
    public boolean isBackMainVisible()
    {
        return backMainVisible;
    }
    /**
     * ���ñ�������������
     * @param backMainLineType int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public void setBackMainLineType(int backMainLineType)
    {
        this.backMainLineType = backMainLineType;
    }
    /**
     * �õ���������������
     * @return int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public int getBackMainLineType()
    {
        return backMainLineType;
    }
    /**
     * ���ñ������߿��
     * @param backMainLineWidth float
     */
    public void setBackMainLineWidth(float backMainLineWidth)
    {
        this.backMainLineWidth = backMainLineWidth;
    }
    /**
     * �õ��������߿��
     * @return float
     */
    public float getBackMainLineWidth()
    {
        return backMainLineWidth;
    }
    /**
     * ���ñ������������߼�
     * @param backMainLineWidth1 float
     */
    public void setBackMainLineWidth1(float backMainLineWidth1)
    {
        this.backMainLineWidth1 = backMainLineWidth1;
    }
    /**
     * �õ��������������߼�
     * @return float
     */
    public float getBackMainLineWidth1()
    {
        return backMainLineWidth1;
    }
    /**
     * ���ñ���������ɫ
     * @param backMainLineColor Color
     */
    public void setBackMainLineColor(Color backMainLineColor)
    {
        this.backMainLineColor = backMainLineColor;
    }
    /**
     * �õ�����������ɫ
     * @return Color
     */
    public Color getBackMainLineColor()
    {
        return backMainLineColor;
    }
    /**
     * �õ�����������ɫ(String)
     * @return String
     */
    public String getBackMainLineColorString()
    {
        if(backMainLineColor == null)
            return "";
        return backMainLineColor.getRed() + "," + backMainLineColor.getGreen() + "," + backMainLineColor.getBlue();
    }
    /**
     * ������ʾ��������
     * @param backSecondVisible boolean
     */
    public void setBackSecondVisible(boolean backSecondVisible)
    {
        this.backSecondVisible = backSecondVisible;
    }
    /**
     * �õ���ʾ��������
     * @return boolean
     */
    public boolean isBackSecondVisible()
    {
        return backSecondVisible;
    }
    /**
     * ���ñ�������������
     * @param backSecondLineType int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public void setBackSecondLineType(int backSecondLineType)
    {
        this.backSecondLineType = backSecondLineType;
    }
    /**
     * �õ���������������
     * @return int
     * 0 ʵ��
     * 1 ����
     * 2 ����
     */
    public int getBackSecondLineType()
    {
        return backSecondLineType;
    }
    /**
     * ���ñ������߿��
     * @param backSecondLineWidth float
     */
    public void setBackSecondLineWidth(float backSecondLineWidth)
    {
        this.backSecondLineWidth = backSecondLineWidth;
    }
    /**
     * �õ��������߿��
     * @return float
     */
    public float getBackSecondLineWidth()
    {
        return backSecondLineWidth;
    }
    /**
     * ���ñ������������߼�
     * @param backSecondLineWidth1 float
     */
    public void setBackSecondLineWidth1(float backSecondLineWidth1)
    {
        this.backSecondLineWidth1 = backSecondLineWidth1;
    }
    /**
     * �õ��������������߼�
     * @return float
     */
    public float getBackSecondLineWidth1()
    {
        return backSecondLineWidth1;
    }
    /**
     * ���ñ���������ɫ
     * @param backSecondLineColor Color
     */
    public void setBackSecondLineColor(Color backSecondLineColor)
    {
        this.backSecondLineColor = backSecondLineColor;
    }
    /**
     * �õ�����������ɫ
     * @return Color
     */
    public Color getBackSecondLineColor()
    {
        return backSecondLineColor;
    }
    /**
     * �õ�����������ɫ(String)
     * @return String
     */
    public String getBackSecondLineColorString()
    {
        if(backSecondLineColor == null)
            return "";
        return backSecondLineColor.getRed() + "," + backSecondLineColor.getGreen() + "," + backSecondLineColor.getBlue();
    }
    /**
     * ��������߷���
     * @param mainTypeWay int
     * 1 ��/��
     * 2 ��/��
     * 3 ����/����
     */
    public void setMainTypeWay(int mainTypeWay)
    {
        this.mainTypeWay = mainTypeWay;
    }
    /**
     * �õ�����߷���
     * @return int
     * 1 ��/��
     * 2 ��/��
     * 3 ����/����
     */
    public int getMainTypeWay()
    {
        return mainTypeWay;
    }
    /**
     * ��������߷���
     * @param mainTypeWay
     * 1 ��/��
     * 2 ��/��
     * @param b boolean true ��ʾ false ����ʾ
     */
    public void setMainTypeWay(int mainTypeWay,boolean b)
    {
        if(b)
            setMainTypeWay(getMainTypeWay() | mainTypeWay);
        else
            setMainTypeWay(~(~getMainTypeWay() | mainTypeWay));
    }
    /**
     * �õ�����߷���
     * @param mainTypeWay int
     * 1 ��/��
     * 2 ��/��
     * @return boolean true ��ʾ false ����ʾ
     */
    public boolean isMainTypeWay(int mainTypeWay)
    {
        return (getMainTypeWay() & mainTypeWay) == mainTypeWay;
    }
    /**
     * ���ôα�߷���
     * @param secondTypeWay int
     * 1 ��/��
     * 2 ��/��
     * 3 ����/����
     */
    public void setSecondTypeWay(int secondTypeWay)
    {
        this.secondTypeWay = secondTypeWay;
    }
    /**
     * �õ��α�߷���
     * @return int
     * 1 ��/��
     * 2 ��/��
     * 3 ����/����
     */
    public int getSecondTypeWay()
    {
        return secondTypeWay;
    }
    /**
     * ���ôα�߷���
     * @param secondTypeWay
     * 1 ��/��
     * 2 ��/��
     * @param b boolean true ��ʾ false ����ʾ
     */
    public void setSecondTypeWay(int secondTypeWay,boolean b)
    {
        if(b)
            setSecondTypeWay(getSecondTypeWay() | secondTypeWay);
        else
            setSecondTypeWay(~(~getSecondTypeWay() | secondTypeWay));
    }
    /**
     * �õ��α�߷���
     * @param secondTypeWay int
     * 1 ��/��
     * 2 ��/��
     * @return boolean true ��ʾ false ����ʾ
     */
    public boolean isSecondTypeWay(int secondTypeWay)
    {
        return (getSecondTypeWay() & secondTypeWay) == secondTypeWay;
    }
    /**
     * �����Ƿ�������
     * @param isNumberAxis boolean
     */
    public void setNumberAxis(boolean isNumberAxis)
    {
        this.isNumberAxis = isNumberAxis;
    }
    /**
     * �Ƿ�������
     * @return boolean
     */
    public boolean isNumberAxis()
    {
        return isNumberAxis;
    }
    /**
     * ����������ɫ
     * @param textColor Color
     */
    public void setTextColor(Color textColor)
    {
        this.textColor = textColor;
    }
    /**
     * �õ�������ɫ
     * @return Color
     */
    public Color getTextColor()
    {
        return textColor;
    }
    /**
     * �õ�������ɫ(String)
     * @return String
     */
    public String getTextColorString()
    {
        if(textColor == null)
            return "";
        return textColor.getRed() + "," + textColor.getGreen() + "," + textColor.getBlue();
    }
    /**
     * ������������
     * @param textFont Font
     */
    public void setTextFont(Font textFont)
    {
        this.textFont = textFont;
    }
    /**
     * �õ���������
     * @return Font
     */
    public Font getTextFont()
    {
        return textFont;
    }
    /**
     * ������������
     * @param name String
     */
    public void setTextFontName(String name)
    {
        setTextFont(new Font(name,getTextFontStyle(),getTextFontSize()));
    }
    /**
     * �õ���������
     * @return String
     */
    public String getTextFontName()
    {
        return getTextFont().getName();
    }
    /**
     * ����������������
     * @param style int
     */
    public void setTextFontStyle(int style)
    {
        setTextFont(new Font(getTextFontName(),style,getTextFontSize()));
    }
    /**
     * �õ�������������
     * @return int
     */
    public int getTextFontStyle()
    {
        return getTextFont().getStyle();
    }
    /**
     * �������ִ���
     * @param b boolean
     */
    public void setTextFontBold(boolean b)
    {
        setTextFontStyle((b?Font.BOLD:0)|((isTextFontItalic())?Font.ITALIC:0));
    }
    /**
     * �����Ƿ��Ǵ���
     * @return boolean
     */
    public boolean isTextFontBold()
    {
        return (getTextFontStyle() & Font.BOLD) == Font.BOLD;
    }
    /**
     * ���������Ƿ���б��
     * @param b boolean
     */
    public void setTextFontItalic(boolean b)
    {
        setTextFontStyle((isTextFontBold()?Font.BOLD:0)|(b?Font.ITALIC:0));
    }
    /**
     * �����Ƿ���б��
     * @return boolean
     */
    public boolean isTextFontItalic()
    {
        return (getTextFontStyle() & Font.ITALIC) == Font.ITALIC;
    }
    /**
     * ������������ߴ�
     * @param size int
     */
    public void setTextFontSize(int size)
    {
        setTextFont(new Font(getTextFontName(),getTextFontStyle(),size));
    }
    /**
     * �õ���������ߴ�
     * @return int
     */
    public int getTextFontSize()
    {
        return getTextFont().getSize();
    }
    /**
     * ����������ת�Ƕ�
     * @param textFontRotate double
     */
    public void setTextFontRotate(double textFontRotate)
    {
        this.textFontRotate = textFontRotate;
    }
    /**
     * �õ�������ת�Ƕ�
     * @return double
     */
    public double getTextFontRotate()
    {
        return textFontRotate;
    }
    /**
     * �������ַ���
     * 1 ��/��
     * 2 ��/��
     * 3 ����/����
     * @param textWay int
     */
    public void setTextWay(int textWay)
    {
        this.textWay = textWay;
    }
    /**
     * �õ����ַ���
     * 1 ��/��
     * 2 ��/��
     * 3 ����/����
     * @return int
     */
    public int getTextWay()
    {
        return textWay;
    }
    /**
     * �������巽��
     * @param textWay int
     * 1 ��/��
     * 2 ��/��
     * @param b boolean true ��ʾ false ����ʾ
     */
    public void setTextWay(int textWay,boolean b)
    {
        if(b)
            setTextWay(getTextWay() | textWay);
        else
            setTextWay(~(~getTextWay() | textWay));
    }
    /**
     * �õ����巽��
     * @param textWay int
     * 1 ��/��
     * 2 ��/��
     * @return boolean true ��ʾ false ����ʾ
     */
    public boolean isTextWay(int textWay)
    {
        return (getTextWay() & textWay) == textWay;
    }
    /**
     * ��������ƫ��λ��
     * @param textOffset int
     */
    public void setTextOffset(int textOffset)
    {
        this.textOffset = textOffset;
    }
    /**
     * �õ�����ƫ��λ��
     * @return int
     */
    public int getTextOffset()
    {
        return textOffset;
    }
    /**
     * �����Զ��س�
     * @param textAutoEnter boolean
     */
    public void setTextAutoEnter(boolean textAutoEnter)
    {
        this.textAutoEnter = textAutoEnter;
    }
    /**
     * �õ��Զ��س�
     * @return boolean
     */
    public boolean isTextAutoEnter()
    {
        return textAutoEnter;
    }
    /**
     * �����Զ��س�ƫ��
     * @param textEnterOffset int
     */
    public void setTextEnterOffset(int textEnterOffset)
    {
        this.textEnterOffset = textEnterOffset;
    }
    /**
     * �õ��Զ��س�ƫ��
     * @return int
     */
    public int getTextEnterOffset()
    {
        return textEnterOffset;
    }
    /**
     * ����ƫ�Ƹ�ʽ
     * @param textOffsetFormat String
     */
    public void setTextOffsetFormat(String textOffsetFormat)
    {
        this.textOffsetFormat = textOffsetFormat;
    }
    /**
     * �õ�ƫ�Ƹ�ʽ
     * @return String
     */
    public String getTextOffsetFormat()
    {
        return textOffsetFormat;
    }
    /**
     * ��ʽ��
     * @param s String
     * @return String
     */
    public String textFormat(String s)
    {
        if(getTextOffsetFormat() == null || getTextOffsetFormat().length() == 0)
            return s;
        return StringTool.S(s,getTextOffsetFormat());
    }
    /**
     * ���ö��䷽ʽ
     * @param textAlign int
     */
    public void setTextAlign(int textAlign)
    {
        this.textAlign = textAlign;
    }
    /**
     * �õ����䷽ʽ
     * @return int
     */
    public int getTextAlign()
    {
        return textAlign;
    }
    /**
     * ���������м�
     * @param textCenter boolean
     */
    public void setTextCenter(boolean textCenter)
    {
        this.textCenter = textCenter;
    }
    /**
     * �Ƿ������м�
     * @return boolean
     */
    public boolean isTextCenter()
    {
        return textCenter;
    }
    /**
     * ������
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        if(!isVisible())
            return;
        if(getWay())
        {
            //���ƴα����
            drawSecondAxisForward(g);
            //�����������
            drawMainAxisForward(g);
            //������
            drawForward(g);
            //��������
            drawTextForward(g);
        }
        else
        {
            //���ƴα����
            drawSecondAxisReverse(g);
            //�����������
            drawMainAxisReverse(g);
            //������
            drawReverse(g);
            //��������
            drawTextReverse(g);
        }
    }
    /**
     * �õ������Ͷ���
     * @return Stroke
     */
    private Stroke getMainStroke()
    {
        switch(getMainLineType())
        {
        case 1:
            return new BasicStroke(getMainLineWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{getMainLineWidth1()}, 0.0f);
        case 2:
            return new BasicStroke(getMainLineWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{getMainLineWidth1(),2.f,1.f,2.f}, 0.0f);
        }
        return new BasicStroke(getMainLineWidth());
    }
    /**
     * �õ�����������Ͷ���
     * @return Stroke
     */
    private Stroke getMainAxisStroke()
    {
        switch(getMainAxisLineType())
        {
        case 1:
            return new BasicStroke(getMainAxisLineWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{getMainAxisLineWidth1()}, 0.0f);
        case 2:
            return new BasicStroke(getMainAxisLineWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{getMainAxisLineWidth1(),2.f,1.f,2.f}, 0.0f);
        }
        return new BasicStroke(getMainAxisLineWidth());
    }
    /**
     * �õ��α�������Ͷ���
     * @return Stroke
     */
    private Stroke getSecondAxisStroke()
    {
        switch(getSecondAxisLineType())
        {
        case 1:
            return new BasicStroke(getSecondAxisLineWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{getSecondAxisLineWidth1()}, 0.0f);
        case 2:
            return new BasicStroke(getSecondAxisLineWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{getSecondAxisLineWidth1(),2.f,1.f,2.f}, 0.0f);
        }
        return new BasicStroke(getSecondAxisLineWidth());
    }
    /**
     * �õ���������������Ͷ���
     * @return Stroke
     */
    private Stroke getBackMainAxisStroke()
    {
        switch(getBackMainLineType())
        {
        case 1:
            return new BasicStroke(getBackMainLineWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{getBackMainLineWidth1()}, 0.0f);
        case 2:
            return new BasicStroke(getBackMainLineWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{getBackMainLineWidth1(),2.f,1.f,2.f}, 0.0f);
        }
        return new BasicStroke(getBackMainLineWidth());
    }
    /**
     * �õ������α�������Ͷ���
     * @return Stroke
     */
    private Stroke getBackSecondAxisStroke()
    {
        switch(getBackSecondLineType())
        {
        case 1:
            return new BasicStroke(getBackSecondLineWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{getBackSecondLineWidth1()}, 0.0f);
        case 2:
            return new BasicStroke(getBackSecondLineWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                                   10.0f, new float[]{getBackSecondLineWidth1(),2.f,1.f,2.f}, 0.0f);
        }
        return new BasicStroke(getBackSecondLineWidth());
    }
    /**
     * ������-����
     * @param g Graphics
     */
    public void drawForward(Graphics g)
    {
        //����������
        ((Graphics2D)g).setStroke(getMainStroke());
        //��������ɫ
        g.setColor(getMainLineColor());
        g.drawLine(getX(),getY(),getX(),getY() + getHeight());
    }
    /**
     * ������-����
     * @param g Graphics
     */
    public void drawReverse(Graphics g)
    {
        //����������
        ((Graphics2D)g).setStroke(getMainStroke());
        //��������ɫ
        g.setColor(getMainLineColor());
        g.drawLine(getX(),getY() + getHeight(),getX() + getWidth(),getY() + getHeight());
    }
    /**
     * �����������-����
     * @param g Graphics
     */
    public void drawMainAxisForward(Graphics g)
    {
        if(getMainType() == 0||getMainTypeWay() == 0)
            return;
        ((Graphics2D)g).setStroke(getMainAxisStroke());
        g.setColor(getMainAxisLineColor());
        if(isMainTypeWay(1))
        {
            int count = getMainCount();
            int x1 = 0;
            int x2 = 0;
            switch (getMainType())
            {
            case 1: //�ⲿ
                x1 = getX() - getMainWidth();
                x2 = getX();
                break;
            case 2: //�ڲ�
                x1 = getX();
                x2 = getX() + getMainWidth();
                break;
            case 3: //����
                x1 = getX() - getMainWidth();
                x2 = getX() + getMainWidth();
                break;
            }
            double unit = getDrawMainUnit(getHeight());
            for (int i = 0; i <= count; i++)
            {
                int y = getY() + getHeight() - (int) ((double) i * unit);
                g.drawLine(x1, y, x2, y);
            }
        }
        if(isMainTypeWay(2))
        {
            int count = getMainCount();
            int x1 = 0;
            int x2 = 0;
            switch (getMainType())
            {
            case 1: //�ⲿ
                x1 = getX() + getWidth();
                x2 = getX() + getWidth() + getMainWidth();
                break;
            case 2: //�ڲ�
                x1 = getX() + getWidth() - getMainWidth();
                x2 = getX() + getWidth();
                break;
            case 3: //����
                x1 = getX() + getWidth() - getMainWidth();
                x2 = getX() + getWidth() + getMainWidth();
                break;
            }
            double unit = getDrawMainUnit(getHeight());
            for (int i = 0; i <= count; i++)
            {
                int y = getY() + getHeight() - (int) ((double) i * unit);
                g.drawLine(x1, y, x2, y);
            }
        }
    }
    /**
     * �����������-����
     * @param g Graphics
     */
    public void drawMainAxisReverse(Graphics g)
    {
        if (getMainType() == 0 || getMainTypeWay() == 0)
            return;
        ((Graphics2D) g).setStroke(getMainAxisStroke());
        g.setColor(getMainAxisLineColor());
        if (isMainTypeWay(1))
        {
            int count = getMainCount();
            int y1 = 0;
            int y2 = 0;
            switch (getMainType())
            {
            case 1: //�ⲿ
                y1 = getY() + getHeight();
                y2 = getY() + getHeight() + getMainWidth();
                break;
            case 2: //�ڲ�
                y1 = getY() + getHeight() - getMainWidth();
                y2 = getY() + getHeight();
                break;
            case 3: //����
                y1 = getY() + getHeight() - getMainWidth();
                y2 = getY() + getHeight() + getMainWidth();
                break;
            }
            double unit = getDrawMainUnit(getWidth());
            for (int i = 0; i <= count; i++)
            {
                int x = getX() + (int) ((double) i * unit);
                g.drawLine(x, y1, x, y2);
            }
        }
        if (isMainTypeWay(2))
        {
            int count = getMainCount();
            int y1 = 0;
            int y2 = 0;
            switch (getMainType())
            {
            case 1: //�ⲿ
                y1 = getY() - getMainWidth();
                y2 = getY();
                break;
            case 2: //�ڲ�
                y1 = getY();
                y2 = getY() + getMainWidth();
                break;
            case 3: //����
                y1 = getY() - getMainWidth();
                y2 = getY() + getMainWidth();
                break;
            }
            double unit = getDrawMainUnit(getWidth());
            for (int i = 0; i <= count; i++)
            {
                int x = getX() + (int) ((double) i * unit);
                g.drawLine(x, y1, x, y2);
            }
        }
    }
    /**
     * ���ƴα����-����
     * @param g Graphics
     */
    public void drawSecondAxisForward(Graphics g)
    {
        if(getSecondType() == 0 || getSecondTypeWay() == 0)
            return;
        ((Graphics2D)g).setStroke(getSecondAxisStroke());
        g.setColor(getSecondAxisLineColor());
        if (isSecondTypeWay(1))
        {
            int count = getSecondCount();
            int x1 = 0;
            int x2 = 0;
            switch (getSecondType())
            {
            case 1: //�ⲿ
                x1 = getX() - getSecondWidth();
                x2 = getX();
                break;
            case 2: //�ڲ�
                x1 = getX();
                x2 = getX() + getSecondWidth();
                break;
            case 3: //����
                x1 = getX() - getSecondWidth();
                x2 = getX() + getSecondWidth();
                break;
            }
            double unit = getDrawSecondUnit(getHeight());
            for (int i = 0; i <= count; i++)
            {
                int y = getY() + getHeight() - (int) ((double) i * unit);
                g.drawLine(x1, y, x2, y);
            }
        }
        if (isSecondTypeWay(2))
        {
            int count = getSecondCount();
            int x1 = 0;
            int x2 = 0;
            switch (getSecondType())
            {
            case 1: //�ⲿ
                x1 = getX() + getWidth();
                x2 = getX() + getWidth() + getSecondWidth();
                break;
            case 2: //�ڲ�
                x1 = getX() + getWidth() - getSecondWidth();
                x2 = getX() + getWidth();
                break;
            case 3: //����
                x1 = getX() + getWidth() - getSecondWidth();
                x2 = getX() + getWidth() + getSecondWidth();
                break;
            }
            double unit = getDrawSecondUnit(getHeight());
            for (int i = 0; i <= count; i++)
            {
                int y = getY() + getHeight() - (int) ((double) i * unit);
                g.drawLine(x1, y, x2, y);
            }
        }
    }
    /**
     * ���ƴα����-����
     * @param g Graphics
     */
    public void drawSecondAxisReverse(Graphics g)
    {
        if (getSecondType() == 0 || getSecondTypeWay() == 0)
            return;
        ((Graphics2D) g).setStroke(getSecondAxisStroke());
        g.setColor(getSecondAxisLineColor());
        if (isSecondTypeWay(1))
        {
            int count = getSecondCount();
            int y1 = 0;
            int y2 = 0;
            switch (getSecondType())
            {
            case 1: //�ⲿ
                y1 = getY() + getHeight();
                y2 = getY() + getHeight() + getSecondWidth();
                break;
            case 2: //�ڲ�
                y1 = getY() + getHeight() - getSecondWidth();
                y2 = getY() + getHeight();
                break;
            case 3: //����
                y1 = getY() + getHeight() - getSecondWidth();
                y2 = getY() + getHeight() + getMainWidth();
                break;
            }
            double unit = getDrawSecondUnit(getWidth());
            for (int i = 0; i <= count; i++)
            {
                int x = getX() + (int) ((double) i * unit);
                g.drawLine(x, y1, x, y2);
            }
        }
        if (isSecondTypeWay(2))
        {
            int count = getSecondCount();
            int y1 = 0;
            int y2 = 0;
            switch (getSecondType())
            {
            case 1: //�ⲿ
                y1 = getY() - getSecondWidth();
                y2 = getY();
                break;
            case 2: //�ڲ�
                y1 = getY();
                y2 = getY() + getSecondWidth();
                break;
            case 3: //����
                y1 = getY() - getSecondWidth();
                y2 = getY() + getMainWidth();
                break;
            }
            double unit = getDrawSecondUnit(getWidth());
            for (int i = 0; i <= count; i++)
            {
                int x = getX() + (int) ((double) i * unit);
                g.drawLine(x, y1, x, y2);
            }
        }
    }
    /**
     * �õ���������
     * @return Font
     */
    private Font getDrawFont()
    {
        if(getTextFontRotate() == 0.0)
            return getTextFont();
        AffineTransform fontAT = new AffineTransform();
        fontAT.rotate(Math.toRadians(getTextFontRotate()));
        return getTextFont().deriveFont(fontAT);
    }
    /**
     * ��������-����
     * @param g Graphics
     */
    public void drawTextForward(Graphics g)
    {
        if(getTextWay() == 0)
            return;
        g.setColor(getTextColor());
        g.setFont(getTextFont());
        FontMetrics fm = g.getFontMetrics();
        g.setFont(getDrawFont());
        if (this.isTextWay(1))//��
        {
            int count = getMainCount();
            int fontHeight = fm.getHeight();
            int ascent = fm.getAscent();
            int x = getX() - getTextOffset();
            double unit = getDrawMainUnit(getHeight());
            for (int i = 0; i <= count; i++)
            {
                String s = "";
                if(isNumberAxis())
                    s = textFormat("" + (getMinValue() + (double)i * getMainUnit()));
                else
                    s = getColumnName(i);
                if(s == null || s.length() == 0)
                    continue;
                int y = getY() + getHeight() - (int) ((double) i * unit);
                if(isTextAutoEnter())
                {
                    y += ascent;
                    int y0 = y - (int)(((double)s.length() * (double)fontHeight + ((double)s.length() - 1.0) * (double)getTextEnterOffset()) / 2.0);
                    for(int j = 0;j < s.length();j++)
                    {
                        char c = s.charAt(j);
                        int x0 = x - fm.charWidth(c);
                        g.drawString("" + c, x0, y0);
                        y0 += fontHeight + getTextEnterOffset();
                    }
                }
                else
                {
                    y += ascent / 2;
                    int x0 = x;
                    //��ת�޲�
                    if(getTextAlign() == 0)
                        x0 = x - fm.stringWidth(s);
                    if(getTextAlign() == 1)
                        x0 = x - ascent;
                    g.drawString(s, x0, y);
                }
            }
        }
        if (this.isTextWay(2))//��
        {
            int count = getMainCount();
            int fontHeight = fm.getHeight();
            int ascent = fm.getAscent();
            int x = getX() + getWidth() + getTextOffset();
            double unit = getDrawMainUnit(getHeight());
            for (int i = 0; i <= count; i++)
            {
                String s = "";
                if(isNumberAxis())
                    s = textFormat("" + (getMinValue() + (double)i * getMainUnit()));
                else
                    s = getColumnName(i);
                if(s == null || s.length() == 0)
                    continue;
                int y = getY() + getHeight() - (int) ((double) i * unit);
                if(isTextAutoEnter())
                {
                    y += ascent;
                    int y0 = y - (int)(((double)s.length() * (double)fontHeight + ((double)s.length() - 1.0) * (double)getTextEnterOffset()) / 2.0);
                    for(int j = 0;j < s.length();j++)
                    {
                        char c = s.charAt(j);
                        g.drawString("" + c, x, y0);
                        y0 += fontHeight + getTextEnterOffset();
                    }
                }
                else
                {
                    y += ascent / 2;
                    g.drawString(s, x, y);
                }
            }
        }
    }
    /**
     * ��������-����
     * @param g Graphics
     */
    public void drawTextReverse(Graphics g)
    {
        if(getTextWay() == 0)
            return;
        g.setColor(getTextColor());
        g.setFont(getTextFont());
        FontMetrics fm = g.getFontMetrics();
        g.setFont(getDrawFont());
        if (this.isTextWay(1))//��
        {
            int count = getMainCount();
            int fontHeight = fm.getHeight();
            int y = getY() + getHeight() + fontHeight + getTextOffset();
            double unit = getDrawMainUnit(getWidth());
            for (int i = 0; i <= count; i++)
            {
                String s = "";
                if(isNumberAxis())
                    s = textFormat("" + (getMinValue() + (double)i * getMainUnit()));
                else
                    s = getColumnName(i);
                if(s == null || s.length() == 0)
                    continue;
                int x = getX() + (int) ((double) i * unit);
                //�������
                if(isTextCenter())
                    x += unit / 2;
                if(isTextAutoEnter())
                {
                    int y0 = y;
                    for(int j = 0;j < s.length();j++)
                    {
                        char c = s.charAt(j);
                        int w = fm.charWidth(c);
                        int x0 = x - w / 2;
                        g.drawString("" + c, x0, y0);
                        y0 += fontHeight + getTextEnterOffset();
                    }
                }
                else
                {
                    int w = fm.stringWidth(s);
                    x -= w / 2;
                    g.drawString(s, x, y);
                }
            }
        }
        if (this.isTextWay(2))//��
        {
            int count = getMainCount();
            int fontHeight = fm.getHeight();
            int y = getY() - getTextOffset();
            double unit = getDrawMainUnit(getWidth());
            for (int i = 0; i <= count; i++)
            {
                String s = "";
                if(isNumberAxis())
                    s = textFormat("" + (getMinValue() + (double)i * getMainUnit()));
                else
                    s = getColumnName(i);
                if(s == null || s.length() == 0)
                    continue;
                int x = getX() + (int) ((double) i * unit);
                //�������
                if(isTextCenter())
                    x += unit / 2;
                if(isTextAutoEnter())
                {
                    int y0 = y - (s.length() - 1) * (getTextEnterOffset() + fontHeight);
                    for(int j = 0;j < s.length();j++)
                    {
                        char c = s.charAt(j);
                        int w = fm.charWidth(c);
                        int x0 = x - w / 2;
                        g.drawString("" + c, x0, y0);
                        y0 += fontHeight + getTextEnterOffset();
                    }
                }
                else
                {
                    int w = fm.stringWidth(s);
                    x -= w / 2;
                    g.drawString(s, x, y);
                }
            }
        }
    }
    /**
     * ���Ʊ�����
     * @param g Graphics
     */
    public void paintBackground(Graphics g)
    {
        if(getWay())
        {
            //���ƴα����
            drawBackSecondAxisForward(g);
            //�����������
            drawBackMainAxisForward(g);
        }
        else
        {
            //���ƴα����
            drawBackSecondAxisReverse(g);
            //�����������
            drawBackMainAxisReverse(g);
        }
    }
    /**
     * ���Ʊ����������-����
     * @param g Graphics
     */
    public void drawBackMainAxisForward(Graphics g)
    {
        if(!isBackMainVisible())
            return;
        ((Graphics2D)g).setStroke(getBackMainAxisStroke());
        g.setColor(getBackMainLineColor());
        int count = getMainCount();
        int x1 = getX();
        int x2 = getX() + getWidth();
        double unit = getDrawMainUnit(getHeight());
        for(int i = 0;i <= count;i++)
        {
            int y = getY() + getHeight() - (int)((double)i * unit);
            g.drawLine(x1,y,x2,y);
        }
    }
    /**
     * ���Ʊ����������-����
     * @param g Graphics
     */
    public void drawBackMainAxisReverse(Graphics g)
    {
        if(!isBackMainVisible())
            return;
        ((Graphics2D)g).setStroke(getBackMainAxisStroke());
        g.setColor(getBackMainLineColor());
        int count = getMainCount();
        int y1 = getY();
        int y2 = getY() + getHeight();
        double unit = getDrawMainUnit(getWidth());
        for(int i = 0;i <= count;i++)
        {
            int x = getX() + (int)((double)i * unit);
            g.drawLine(x,y1,x,y2);
        }
    }
    /**
     * ���ƴα�߱�����-����
     * @param g Graphics
     */
    public void drawBackSecondAxisForward(Graphics g)
    {
        if(!isBackSecondVisible())
            return;
        ((Graphics2D)g).setStroke(getBackSecondAxisStroke());
        g.setColor(getBackSecondLineColor());
        int count = getSecondCount();
        int x1 = getX();
        int x2 = getX() + getWidth();
        double unit = getDrawSecondUnit(getHeight());
        for(int i = 0;i <= count;i++)
        {
            int y = getY() + getHeight() - (int)((double)i * unit);
            g.drawLine(x1,y,x2,y);
        }
    }
    /**
     * ���ƴα�߱�����-����
     * @param g Graphics
     */
    public void drawBackSecondAxisReverse(Graphics g)
    {
        if(!isBackSecondVisible())
            return;
        ((Graphics2D)g).setStroke(getBackSecondAxisStroke());
        g.setColor(getBackSecondLineColor());
        int count = getSecondCount();
        int y1 = getY();
        int y2 = getY() + getHeight();
        double unit = getDrawSecondUnit(getWidth());
        for(int i = 0;i <= count;i++)
        {
            int x = getX() + (int)((double)i * unit);
            g.drawLine(x,y1,x,y2);
        }
    }
    /**
     * �õ���ƫ���� X
     * @param index int
     * @return int
     */
    public int getAxisX(int index)
    {
        double unit = getDrawMainUnit(getWidth());
        return getX() + (int)((double)index * unit);
    }
    /**
     * �õ���ƫ���� Y
     * @param index int
     * @return int
     */
    public int getAxisY(int index)
    {
        double unit = getDrawMainUnit(getHeight());
        return getY() + (int)((double)index * unit);
    }
    /**
     * �õ���ƫ����(�м�) X
     * @param index int
     * @return int
     */
    public int getAxisCenterX(int index)
    {
        double unit = getDrawMainUnit(getWidth());
        return getX() + (int)((double)index * unit + unit / 2.0);
    }
    /**
     * �õ���ƫ����(�м�) Y
     * @param index int
     * @return int
     */
    public int getAxisCenterY(int index)
    {
        double unit = getDrawMainUnit(getHeight());
        return getY() + (int)((double)index * unit + unit / 2.0);
    }
    /**
     * ������ֵ���
     * @param value double
     * @return int
     */
    public int getValueX(double value)
    {
        double max = getValue();
        double v = value - getMinValue();
        return (int)((double)getX() + (double)getWidth() - v/max * (double)getWidth() + 0.5);
    }
    /**
     * ������ֵ�߶�
     * @param value double
     * @return int
     */
    public int getValueY(double value)
    {
        double max = getValue();
        double v = value - getMinValue();
        return (int)((double)getY() + (double)getHeight() - v/max * (double)getHeight() + 0.5);
    }
    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        //д��������
        writeObjectAttribute(s);
        s.writeShort( -1);
        //����ҳ
        s.writeInt(0);
    }
    /**
     * ������
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        int id = s.readShort();
        while (id > 0)
        {
            //����������
            readObjectAttribute(id,s);
            id = s.readShort();
        }
        //��ȡ��
        int count = s.readInt();
    }
    /**
     * д��������
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        s.writeBoolean(1,isVisible(),true);
        s.writeBoolean(2,getWay(),true);
        s.writeDouble(3,getMinValue(),0.0);
        s.writeDouble(4,getMaxValue(),0.0);
        s.writeDouble(5,getMainUnit(),0.0);
        s.writeDouble(6,getSecondUnit(),0.0);
        if(getMainLineColor() != null)
        {
            s.writeShort(7);
            s.writeInt(getMainLineColor().getRGB());
        }
        s.writeInt(8,getMainLineType(),0);
        s.writeFloat(9,getMainLineWidth(),0);
        s.writeFloat(10,getMainLineWidth1(),0);
        s.writeInt(11,getMainWidth(),0);
        s.writeInt(12,getMainTypeWay(),0);
        s.writeInt(13,getMainType(),0);
        if(getMainAxisLineColor() != null)
        {
            s.writeShort(14);
            s.writeInt(getMainAxisLineColor().getRGB());
        }
        s.writeInt(15,getMainAxisLineType(),0);
        s.writeFloat(16,getMainAxisLineWidth(),0);
        s.writeFloat(17,getMainAxisLineWidth1(),0);
        s.writeInt(18,getSecondWidth(),0);
        s.writeInt(19,getSecondTypeWay(),0);
        s.writeInt(20,getSecondType(),0);
        if(getSecondAxisLineColor() != null)
        {
            s.writeShort(21);
            s.writeInt(getSecondAxisLineColor().getRGB());
        }
        s.writeInt(22,getSecondAxisLineType(),0);
        s.writeFloat(23,getSecondAxisLineWidth(),0);
        s.writeFloat(24,getSecondAxisLineWidth1(),0);
        s.writeBoolean(25,isBackMainVisible(),true);
        if(getBackMainLineColor() != null)
        {
            s.writeShort(26);
            s.writeInt(getBackMainLineColor().getRGB());
        }
        s.writeInt(27,getBackMainLineType(),0);
        s.writeFloat(28,getBackMainLineWidth(),0);
        s.writeFloat(29,getBackMainLineWidth1(),0);
        s.writeBoolean(30,isBackSecondVisible(),false);
        if(getBackSecondLineColor() != null)
        {
            s.writeShort(31);
            s.writeInt(getBackSecondLineColor().getRGB());
        }
        s.writeInt(32,getBackSecondLineType(),0);
        s.writeFloat(33,getBackSecondLineWidth(),0);
        s.writeFloat(34,getBackSecondLineWidth1(),0);
        if(getTextColor() != null)
        {
            s.writeShort(35);
            s.writeInt(getTextColor().getRGB());
        }
        s.writeShort(36);
        s.writeString(getTextFontName());
        s.writeInt(getTextFontStyle());
        s.writeInt(getTextFontSize());
        s.writeDouble(getTextFontRotate());
        s.writeInt(37,getTextWay(),0);
        s.writeInt(38,getTextOffset(),0);
        s.writeBoolean(39,isTextAutoEnter(),false);
        s.writeInt(40,getTextEnterOffset(),0);
        s.writeString(41,getTextOffsetFormat(),"");
        s.writeInt(42,getTextAlign(),0);
        s.writeBoolean(43,isTextCenter(),false);
    }
    /**
     * ����������
     * @param id int
     * @param s DataInputStream
     * @return boolean
     * @throws IOException
     */
    public boolean readObjectAttribute(int id,DataInputStream s)
            throws IOException
    {
        switch (id)
        {
        case 1:
            setVisible(s.readBoolean());
            return true;
        case 2:
            setWay(s.readBoolean());
            return true;
        case 3:
            setMinValue(s.readDouble());
            return true;
        case 4:
            setMaxValue(s.readDouble());
            return true;
        case 5:
            setMainUnit(s.readDouble());
            return true;
        case 6:
            setSecondUnit(s.readDouble());
            return true;
        case 7:
            setMainLineColor(new Color(s.readInt()));
            return true;
        case 8:
            setMainLineType(s.readInt());
            return true;
        case 9:
            setMainLineWidth(s.readFloat());
            return true;
        case 10:
            setMainLineWidth1(s.readFloat());
            return true;
        case 11:
            setMainWidth(s.readInt());
            return true;
        case 12:
            setMainTypeWay(s.readInt());
            return true;
        case 13:
            setMainType(s.readInt());
            return true;
        case 14:
            setMainAxisLineColor(new Color(s.readInt()));
            return true;
        case 15:
            setMainAxisLineType(s.readInt());
            return true;
        case 16:
            setMainAxisLineWidth(s.readFloat());
            return true;
        case 17:
            setMainAxisLineWidth1(s.readFloat());
            return true;
        case 18:
            setSecondWidth(s.readInt());
            return true;
        case 19:
            setSecondTypeWay(s.readInt());
            return true;
        case 20:
            setSecondType(s.readInt());
            return true;
        case 21:
            setSecondAxisLineColor(new Color(s.readInt()));
            return true;
        case 22:
            setSecondAxisLineType(s.readInt());
            return true;
        case 23:
            setSecondAxisLineWidth(s.readFloat());
            return true;
        case 24:
            setSecondAxisLineWidth1(s.readFloat());
            return true;
        case 25:
            setBackMainVisible(s.readBoolean());
            return true;
        case 26:
            setBackMainLineColor(new Color(s.readInt()));
            return true;
        case 27:
            setBackMainLineType(s.readInt());
            return true;
        case 28:
            setBackMainLineWidth(s.readFloat());
            return true;
        case 29:
            setBackMainLineWidth1(s.readFloat());
            return true;
        case 30:
            setBackSecondVisible(s.readBoolean());
            return true;
        case 31:
            setBackSecondLineColor(new Color(s.readInt()));
            return true;
        case 32:
            setBackSecondLineType(s.readInt());
            return true;
        case 33:
            setBackSecondLineWidth(s.readFloat());
            return true;
        case 34:
            setBackSecondLineWidth1(s.readFloat());
            return true;
        case 35:
            setTextColor(new Color(s.readInt()));
            return true;
        case 36:
            setTextFont(new Font(s.readString(),s.readInt(),s.readInt()));
            setTextFontRotate(s.readDouble());
           return true;
        case 37:
            setTextWay(s.readInt());
            return true;
        case 38:
            setTextOffset(s.readInt());
            return true;
        case 39:
            setTextAutoEnter(s.readBoolean());
            return true;
        case 40:
            setTextEnterOffset(s.readInt());
            return true;
        case 41:
            setTextOffsetFormat(s.readString());
            return true;
        case 42:
            setTextAlign(s.readInt());
            return true;
        case 43:
            setTextCenter(s.readBoolean());
            return true;
        }
        return false;
    }
}
