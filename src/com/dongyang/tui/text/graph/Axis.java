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
 * <p>Title: 轴线</p>
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
     * 父类
     */
    private EPicData parent;
    /**
     * 显示
     */
    private boolean visible = true;
    /**
     * 轴方向
     * true 正向 false 反向
     */
    private boolean way = true;
    /**
     * 主线类型
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    private int mainLineType = 0;
    /**
     * 主线线宽
     */
    private float mainLineWidth = 0.5f;
    /**
     * 主线虚线线间
     */
    private float mainLineWidth1 = 5.5f;
    /**
     * 主线颜色
     */
    private Color mainLineColor = new Color(0,0,0);
    /**
     * 最小值
     */
    private double minValue = 0;
    /**
     * 最大值
     */
    private double maxValue = 100;
    /**
     * 主刻度单位
     */
    private double mainUnit = 10;
    /**
     * 次刻度单位
     */
    private double secondUnit = 2;
    /**
     * 主刻度宽度
     */
    private int mainWidth = 5;
    /**
     * 次刻度宽度
     */
    private int secondWidth = 2;
    /**
     * 主标尺方向
     * 1 左/下
     * 2 右/上
     * 3 左右/上下
     */
    private int mainTypeWay = 1;
    /**
     * 次标尺方向
     * 1 左/下
     * 2 右/上
     * 3 左右/上下
     */
    private int secondTypeWay = 1;
    /**
     * 主刻度类型
     * 0 不显示
     * 1 外部
     * 2 内部
     * 3 交叉
     */
    private int mainType = 1;
    /**
     * 次刻度类型
     * 0 不显示
     * 1 外部
     * 2 内部
     * 3 交叉
     */
    private int secondType = 2;
    /**
     * 主刻度线类型
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    private int mainAxisLineType = 0;
    /**
     * 主刻度线宽
     */
    private float mainAxisLineWidth = 0.5f;
    /**
     * 主刻度虚线线间
     */
    private float mainAxisLineWidth1 = 5.5f;
    /**
     * 主刻度颜色
     */
    private Color mainAxisLineColor = new Color(0,0,0);
    /**
     * 次刻度线类型
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    private int secondAxisLineType = 0;
    /**
     * 次刻度线宽
     */
    private float secondAxisLineWidth = 0.5f;
    /**
     * 次刻度虚线线间
     */
    private float secondAxisLineWidth1 = 5f;
    /**
     * 次刻度颜色
     */
    private Color secondAxisLineColor = new Color(0,0,0);
    /**
     * 显示背景主线
     */
    private boolean backMainVisible = true;
    /**
     * 背景主线线类型
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    private int backMainLineType = 0;
    /**
     * 背景主线宽度
     */
    private float backMainLineWidth = 1.0f;
    /**
     * 背景主线虚线线间
     */
    private float backMainLineWidth1 = 5f;
    /**
     * 背景主线颜色
     */
    private Color backMainLineColor = new Color(0,0,0);
    /**
     * 显示背景次线
     */
    private boolean backSecondVisible = false;
    /**
     * 背景次线线类型
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    private int backSecondLineType = 0;
    /**
     * 背景次线宽度
     */
    private float backSecondLineWidth = 1.0f;
    /**
     * 背景次线虚线线间
     */
    private float backSecondLineWidth1 = 5f;
    /**
     * 背景次线颜色
     */
    private Color backSecondLineColor = new Color(0,0,0);
    /**
     * 是否是数轴
     */
    private boolean isNumberAxis;
    /**
     * 文字颜色
     */
    private Color textColor = new Color(0,0,0);
    /**
     * 文字字体
     */
    private Font textFont = new Font("宋体",0,10);
    /**
     * 文字旋转角度
     */
    private double textFontRotate = 0.0;
    /**
     * 文字方向
     * 1 左/下
     * 2 右/上
     * 3 左右/上下
     */
    private int textWay = 1;
    /**
     * 字体偏移位置
     */
    private int textOffset = 5;
    /**
     * 自动回车
     */
    private boolean textAutoEnter = false;
    /**
     * 自动回车偏移
     */
    private int textEnterOffset;
    /**
     * 偏移格式
     */
    private String textOffsetFormat = "####0";
    /**
     * 对其方式
     * 0 正常
     * 1 旋转修补
     */
    private int textAlign = 0;
    /**
     * 字体中间
     */
    private boolean textCenter = false;
    /**
     * 构造器
     */
    public Axis()
    {

    }
    /**
     * 构造器
     * @param parent EPicData
     */
    public Axis(EPicData parent)
    {
        setParent(parent);
    }
    /**
     * 设置父类
     * @param parent EPicData
     */
    public void setParent(EPicData parent)
    {
        this.parent = parent;
    }
    /**
     * 得到父类
     * @return EPicData
     */
    public EPicData getParent()
    {
        return parent;
    }
    /**
     * 得到X坐标
     * @return int
     */
    public int getX()
    {
        return getParent().getX();
    }
    /**
     * 得到Y坐标
     * @return int
     */
    public int getY()
    {
        return getParent().getY();
    }
    /**
     * 得到宽度
     * @return int
     */
    public int getWidth()
    {
        return getParent().getWidth();
    }
    /**
     * 得到高度
     * @return int
     */
    public int getHeight()
    {
        return getParent().getHeight();
    }
    /**
     * 得到列名
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
     * 设置显示
     * @param visible boolean
     */
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    /**
     * 是否显示
     * @return boolean
     */
    public boolean isVisible()
    {
        return visible;
    }
    /**
     * 设置轴方向
     * @param way boolean true 正向 false 反向
     */
    public void setWay(boolean way)
    {
        this.way = way;
    }
    /**
     * 得到轴方向
     * @return boolean true 正向 false 反向
     */
    public boolean getWay()
    {
        return way;
    }
    /**
     * 设置主线类型
     * @param mainLineType int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public void setMainLineType(int mainLineType)
    {
        this.mainLineType = mainLineType;
    }
    /**
     * 得到主线类型
     * @return int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public int getMainLineType()
    {
        return mainLineType;
    }
    /**
     * 设置主线线宽
     * @param mainLineWidth float
     */
    public void setMainLineWidth(float mainLineWidth)
    {
        this.mainLineWidth = mainLineWidth;
    }
    /**
     * 得到主线线宽
     * @return float
     */
    public float getMainLineWidth()
    {
        return mainLineWidth;
    }
    /**
     * 设置主线虚线线间
     * @param mainLineWidth1 float
     */
    public void setMainLineWidth1(float mainLineWidth1)
    {
        this.mainLineWidth1 = mainLineWidth1;
    }
    /**
     * 得到主线虚线线间
     * @return float
     */
    public float getMainLineWidth1()
    {
        return mainLineWidth1;
    }
    /**
     * 设置主线颜色
     * @param mainLineColor Color
     */
    public void setMainLineColor(Color mainLineColor)
    {
        this.mainLineColor = mainLineColor;
    }
    /**
     * 得到主线颜色
     * @return Color
     */
    public Color getMainLineColor()
    {
        return mainLineColor;
    }
    /**
     * 得到主线颜色(String)
     * @return String
     */
    public String getMainLineColorString()
    {
        if(mainLineColor == null)
            return "";
        return mainLineColor.getRed() + "," + mainLineColor.getGreen() + "," + mainLineColor.getBlue();
    }
    /**
     * 设置最小值
     * @param minValue double
     */
    public void setMinValue(double minValue)
    {
        this.minValue = minValue;
    }
    /**
     * 得到最小值
     * @return double
     */
    public double getMinValue()
    {
        return minValue;
    }
    /**
     * 设置最大值
     * @param maxValue double
     */
    public void setMaxValue(double maxValue)
    {
        this.maxValue = maxValue;
    }
    /**
     * 得到最大值
     * @return double
     */
    public double getMaxValue()
    {
        return maxValue;
    }
    /**
     * 设置主刻度单位
     * @param mainUnit double
     */
    public void setMainUnit(double mainUnit)
    {
        this.mainUnit = mainUnit;
    }
    /**
     * 得到主刻度单位
     * @return double
     */
    public double getMainUnit()
    {
        return mainUnit;
    }
    /**
     * 设置次刻度单位
     * @param secondUnit double
     */
    public void setSecondUnit(double secondUnit)
    {
        this.secondUnit = secondUnit;
    }
    /**
     * 得到次刻度单位
     * @return double
     */
    public double getSecondUnit()
    {
        return secondUnit;
    }
    /**
     * 设置主刻度宽度
     * @param mainWidth int
     */
    public void setMainWidth(int mainWidth)
    {
        this.mainWidth = mainWidth;
    }
    /**
     * 得到主刻度宽度
     * @return int
     */
    public int getMainWidth()
    {
        return mainWidth;
    }
    /**
     * 设置次刻度宽度
     * @param secondWidth int
     */
    public void setSecondWidth(int secondWidth)
    {
        this.secondWidth = secondWidth;
    }
    /**
     * 得到次刻度宽度
     * @return int
     */
    public int getSecondWidth()
    {
        return secondWidth;
    }
    /**
     * 设置主刻度类型
     * @param mainType int
     * 0 不显示
     * 1 外部
     * 2 内部
     * 3 交叉
     */
    public void setMainType(int mainType)
    {
        this.mainType = mainType;
    }
    /**
     * 得到主刻度类型
     * @return int
     * 0 不显示
     * 1 外部
     * 2 内部
     * 3 交叉
     */
    public int getMainType()
    {
        return mainType;
    }
    /**
     * 设置次刻度类型
     * @param secondType int
     * 0 不显示
     * 1 外部
     * 2 内部
     * 3 交叉
     */
    public void setSecondType(int secondType)
    {
        this.secondType = secondType;
    }
    /**
     * 得到次刻度类型
     * @return int
     * 0 不显示
     * 1 外部
     * 2 内部
     * 3 交叉
     */
    public int getSecondType()
    {
        return secondType;
    }
    /**
     * 设置主刻度线类型
     * @param mainAxisLineType int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public void setMainAxisLineType(int mainAxisLineType)
    {
        this.mainAxisLineType = mainAxisLineType;
    }
    /**
     * 得到主刻度线类型
     * @return int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public int getMainAxisLineType()
    {
        return mainAxisLineType;
    }
    /**
     * 设置主刻度线宽
     * @param mainAxisLineWidth float
     */
    public void setMainAxisLineWidth(float mainAxisLineWidth)
    {
        this.mainAxisLineWidth = mainAxisLineWidth;
    }
    /**
     * 得到主刻度线宽
     * @return float
     */
    public float getMainAxisLineWidth()
    {
        return mainAxisLineWidth;
    }
    /**
     * 设置主刻度虚线线间
     * @param mainAxisLineWidth1 float
     */
    public void setMainAxisLineWidth1(float mainAxisLineWidth1)
    {
        this.mainAxisLineWidth1 = mainAxisLineWidth1;
    }
    /**
     * 得到主刻度虚线线间
     * @return float
     */
    public float getMainAxisLineWidth1()
    {
        return mainAxisLineWidth1;
    }
    /**
     * 设置主刻度颜色
     * @param mainAxisLineColor Color
     */
    public void setMainAxisLineColor(Color mainAxisLineColor)
    {
        this.mainAxisLineColor = mainAxisLineColor;
    }
    /**
     * 得到主刻度颜色
     * @return Color
     */
    public Color getMainAxisLineColor()
    {
        return mainAxisLineColor;
    }
    /**
     * 得到主刻度颜色(String)
     * @return String
     */
    public String getMainAxisLineColorString()
    {
        if(mainAxisLineColor == null)
            return "";
        return mainAxisLineColor.getRed() + "," + mainAxisLineColor.getGreen() + "," + mainAxisLineColor.getBlue();
    }
    /**
     * 设置次刻度线类型
     * @param secondAxisLineType int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public void setSecondAxisLineType(int secondAxisLineType)
    {
        this.secondAxisLineType = secondAxisLineType;
    }
    /**
     * 得到次刻度线类型
     * @return int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public int getSecondAxisLineType()
    {
        return secondAxisLineType;
    }
    /**
     * 设置次刻度线宽
     * @param secondAxisLineWidth float
     */
    public void setSecondAxisLineWidth(float secondAxisLineWidth)
    {
        this.secondAxisLineWidth = secondAxisLineWidth;
    }
    /**
     * 得到次刻度线宽
     * @return float
     */
    public float getSecondAxisLineWidth()
    {
        return secondAxisLineWidth;
    }
    /**
     * 设置次刻度虚线线间
     * @param secondAxisLineWidth1 float
     */
    public void setSecondAxisLineWidth1(float secondAxisLineWidth1)
    {
        this.secondAxisLineWidth1 = secondAxisLineWidth1;
    }
    /**
     * 得到次刻度虚线线间
     * @return float
     */
    public float getSecondAxisLineWidth1()
    {
        return secondAxisLineWidth1;
    }
    /**
     * 设置次刻度颜色
     * @param secondAxisLineColor Color
     */
    public void setSecondAxisLineColor(Color secondAxisLineColor)
    {
        this.secondAxisLineColor = secondAxisLineColor;
    }
    /**
     * 得到次刻度颜色
     * @return Color
     */
    public Color getSecondAxisLineColor()
    {
        return secondAxisLineColor;
    }
    /**
     * 得到次刻度颜色(String)
     * @return String
     */
    public String getSecondAxisLineColorString()
    {
        if(secondAxisLineColor == null)
            return "";
        return secondAxisLineColor.getRed() + "," + secondAxisLineColor.getGreen() + "," + secondAxisLineColor.getBlue();
    }
    /**
     * 得到差值
     * @return double
     */
    public double getValue()
    {
        return getMaxValue() - getMinValue();
    }
    /**
     * 得到主刻度个数
     * @return int
     */
    public int getMainCount()
    {
        return (int)(getValue() / getMainUnit());
    }
    /**
     * 得到次刻度个数
     * @return int
     */
    public int getSecondCount()
    {
        return (int)(getValue() / getSecondUnit());
    }
    /**
     * 计算主单位长度
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
     * 计算次单位长度
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
     * 设置显示背景主线
     * @param backMainVisible boolean
     */
    public void setBackMainVisible(boolean backMainVisible)
    {
        this.backMainVisible = backMainVisible;
    }
    /**
     * 得到显示背景主线
     * @return boolean
     */
    public boolean isBackMainVisible()
    {
        return backMainVisible;
    }
    /**
     * 设置背景主线线类型
     * @param backMainLineType int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public void setBackMainLineType(int backMainLineType)
    {
        this.backMainLineType = backMainLineType;
    }
    /**
     * 得到背景主线线类型
     * @return int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public int getBackMainLineType()
    {
        return backMainLineType;
    }
    /**
     * 设置背景主线宽度
     * @param backMainLineWidth float
     */
    public void setBackMainLineWidth(float backMainLineWidth)
    {
        this.backMainLineWidth = backMainLineWidth;
    }
    /**
     * 得到背景主线宽度
     * @return float
     */
    public float getBackMainLineWidth()
    {
        return backMainLineWidth;
    }
    /**
     * 设置背景主线虚线线间
     * @param backMainLineWidth1 float
     */
    public void setBackMainLineWidth1(float backMainLineWidth1)
    {
        this.backMainLineWidth1 = backMainLineWidth1;
    }
    /**
     * 得到背景主线虚线线间
     * @return float
     */
    public float getBackMainLineWidth1()
    {
        return backMainLineWidth1;
    }
    /**
     * 设置背景主线颜色
     * @param backMainLineColor Color
     */
    public void setBackMainLineColor(Color backMainLineColor)
    {
        this.backMainLineColor = backMainLineColor;
    }
    /**
     * 得到背景主线颜色
     * @return Color
     */
    public Color getBackMainLineColor()
    {
        return backMainLineColor;
    }
    /**
     * 得到背景主线颜色(String)
     * @return String
     */
    public String getBackMainLineColorString()
    {
        if(backMainLineColor == null)
            return "";
        return backMainLineColor.getRed() + "," + backMainLineColor.getGreen() + "," + backMainLineColor.getBlue();
    }
    /**
     * 设置显示背景次线
     * @param backSecondVisible boolean
     */
    public void setBackSecondVisible(boolean backSecondVisible)
    {
        this.backSecondVisible = backSecondVisible;
    }
    /**
     * 得到显示背景次线
     * @return boolean
     */
    public boolean isBackSecondVisible()
    {
        return backSecondVisible;
    }
    /**
     * 设置背景次线线类型
     * @param backSecondLineType int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public void setBackSecondLineType(int backSecondLineType)
    {
        this.backSecondLineType = backSecondLineType;
    }
    /**
     * 得到背景次线线类型
     * @return int
     * 0 实线
     * 1 虚线
     * 2 点线
     */
    public int getBackSecondLineType()
    {
        return backSecondLineType;
    }
    /**
     * 设置背景次线宽度
     * @param backSecondLineWidth float
     */
    public void setBackSecondLineWidth(float backSecondLineWidth)
    {
        this.backSecondLineWidth = backSecondLineWidth;
    }
    /**
     * 得到背景次线宽度
     * @return float
     */
    public float getBackSecondLineWidth()
    {
        return backSecondLineWidth;
    }
    /**
     * 设置背景次线虚线线间
     * @param backSecondLineWidth1 float
     */
    public void setBackSecondLineWidth1(float backSecondLineWidth1)
    {
        this.backSecondLineWidth1 = backSecondLineWidth1;
    }
    /**
     * 得到背景次线虚线线间
     * @return float
     */
    public float getBackSecondLineWidth1()
    {
        return backSecondLineWidth1;
    }
    /**
     * 设置背景次线颜色
     * @param backSecondLineColor Color
     */
    public void setBackSecondLineColor(Color backSecondLineColor)
    {
        this.backSecondLineColor = backSecondLineColor;
    }
    /**
     * 得到背景次线颜色
     * @return Color
     */
    public Color getBackSecondLineColor()
    {
        return backSecondLineColor;
    }
    /**
     * 得到背景次线颜色(String)
     * @return String
     */
    public String getBackSecondLineColorString()
    {
        if(backSecondLineColor == null)
            return "";
        return backSecondLineColor.getRed() + "," + backSecondLineColor.getGreen() + "," + backSecondLineColor.getBlue();
    }
    /**
     * 设置主标尺方向
     * @param mainTypeWay int
     * 1 左/下
     * 2 右/上
     * 3 左右/上下
     */
    public void setMainTypeWay(int mainTypeWay)
    {
        this.mainTypeWay = mainTypeWay;
    }
    /**
     * 得到主标尺方向
     * @return int
     * 1 左/下
     * 2 右/上
     * 3 左右/上下
     */
    public int getMainTypeWay()
    {
        return mainTypeWay;
    }
    /**
     * 设置主标尺方向
     * @param mainTypeWay
     * 1 左/下
     * 2 右/上
     * @param b boolean true 显示 false 不显示
     */
    public void setMainTypeWay(int mainTypeWay,boolean b)
    {
        if(b)
            setMainTypeWay(getMainTypeWay() | mainTypeWay);
        else
            setMainTypeWay(~(~getMainTypeWay() | mainTypeWay));
    }
    /**
     * 得到主标尺方向
     * @param mainTypeWay int
     * 1 左/下
     * 2 右/上
     * @return boolean true 显示 false 不显示
     */
    public boolean isMainTypeWay(int mainTypeWay)
    {
        return (getMainTypeWay() & mainTypeWay) == mainTypeWay;
    }
    /**
     * 设置次标尺方向
     * @param secondTypeWay int
     * 1 左/下
     * 2 右/上
     * 3 左右/上下
     */
    public void setSecondTypeWay(int secondTypeWay)
    {
        this.secondTypeWay = secondTypeWay;
    }
    /**
     * 得到次标尺方向
     * @return int
     * 1 左/下
     * 2 右/上
     * 3 左右/上下
     */
    public int getSecondTypeWay()
    {
        return secondTypeWay;
    }
    /**
     * 设置次标尺方向
     * @param secondTypeWay
     * 1 左/下
     * 2 右/上
     * @param b boolean true 显示 false 不显示
     */
    public void setSecondTypeWay(int secondTypeWay,boolean b)
    {
        if(b)
            setSecondTypeWay(getSecondTypeWay() | secondTypeWay);
        else
            setSecondTypeWay(~(~getSecondTypeWay() | secondTypeWay));
    }
    /**
     * 得到次标尺方向
     * @param secondTypeWay int
     * 1 左/下
     * 2 右/上
     * @return boolean true 显示 false 不显示
     */
    public boolean isSecondTypeWay(int secondTypeWay)
    {
        return (getSecondTypeWay() & secondTypeWay) == secondTypeWay;
    }
    /**
     * 设置是否是数轴
     * @param isNumberAxis boolean
     */
    public void setNumberAxis(boolean isNumberAxis)
    {
        this.isNumberAxis = isNumberAxis;
    }
    /**
     * 是否是数轴
     * @return boolean
     */
    public boolean isNumberAxis()
    {
        return isNumberAxis;
    }
    /**
     * 设置文字颜色
     * @param textColor Color
     */
    public void setTextColor(Color textColor)
    {
        this.textColor = textColor;
    }
    /**
     * 得到文字颜色
     * @return Color
     */
    public Color getTextColor()
    {
        return textColor;
    }
    /**
     * 得到文字颜色(String)
     * @return String
     */
    public String getTextColorString()
    {
        if(textColor == null)
            return "";
        return textColor.getRed() + "," + textColor.getGreen() + "," + textColor.getBlue();
    }
    /**
     * 设置文字字体
     * @param textFont Font
     */
    public void setTextFont(Font textFont)
    {
        this.textFont = textFont;
    }
    /**
     * 得到文字字体
     * @return Font
     */
    public Font getTextFont()
    {
        return textFont;
    }
    /**
     * 设置文字字体
     * @param name String
     */
    public void setTextFontName(String name)
    {
        setTextFont(new Font(name,getTextFontStyle(),getTextFontSize()));
    }
    /**
     * 得到文字字体
     * @return String
     */
    public String getTextFontName()
    {
        return getTextFont().getName();
    }
    /**
     * 设置文字字体类型
     * @param style int
     */
    public void setTextFontStyle(int style)
    {
        setTextFont(new Font(getTextFontName(),style,getTextFontSize()));
    }
    /**
     * 得到文字字体类型
     * @return int
     */
    public int getTextFontStyle()
    {
        return getTextFont().getStyle();
    }
    /**
     * 设置文字粗体
     * @param b boolean
     */
    public void setTextFontBold(boolean b)
    {
        setTextFontStyle((b?Font.BOLD:0)|((isTextFontItalic())?Font.ITALIC:0));
    }
    /**
     * 文字是否是粗体
     * @return boolean
     */
    public boolean isTextFontBold()
    {
        return (getTextFontStyle() & Font.BOLD) == Font.BOLD;
    }
    /**
     * 设置字体是否是斜体
     * @param b boolean
     */
    public void setTextFontItalic(boolean b)
    {
        setTextFontStyle((isTextFontBold()?Font.BOLD:0)|(b?Font.ITALIC:0));
    }
    /**
     * 字体是否是斜体
     * @return boolean
     */
    public boolean isTextFontItalic()
    {
        return (getTextFontStyle() & Font.ITALIC) == Font.ITALIC;
    }
    /**
     * 设置文字字体尺寸
     * @param size int
     */
    public void setTextFontSize(int size)
    {
        setTextFont(new Font(getTextFontName(),getTextFontStyle(),size));
    }
    /**
     * 得到文字字体尺寸
     * @return int
     */
    public int getTextFontSize()
    {
        return getTextFont().getSize();
    }
    /**
     * 设置文字旋转角度
     * @param textFontRotate double
     */
    public void setTextFontRotate(double textFontRotate)
    {
        this.textFontRotate = textFontRotate;
    }
    /**
     * 得到文字旋转角度
     * @return double
     */
    public double getTextFontRotate()
    {
        return textFontRotate;
    }
    /**
     * 设置文字方向
     * 1 左/下
     * 2 右/上
     * 3 左右/上下
     * @param textWay int
     */
    public void setTextWay(int textWay)
    {
        this.textWay = textWay;
    }
    /**
     * 得到文字方向
     * 1 左/下
     * 2 右/上
     * 3 左右/上下
     * @return int
     */
    public int getTextWay()
    {
        return textWay;
    }
    /**
     * 设置字体方向
     * @param textWay int
     * 1 左/下
     * 2 右/上
     * @param b boolean true 显示 false 不显示
     */
    public void setTextWay(int textWay,boolean b)
    {
        if(b)
            setTextWay(getTextWay() | textWay);
        else
            setTextWay(~(~getTextWay() | textWay));
    }
    /**
     * 得到字体方向
     * @param textWay int
     * 1 左/下
     * 2 右/上
     * @return boolean true 显示 false 不显示
     */
    public boolean isTextWay(int textWay)
    {
        return (getTextWay() & textWay) == textWay;
    }
    /**
     * 设置字体偏移位置
     * @param textOffset int
     */
    public void setTextOffset(int textOffset)
    {
        this.textOffset = textOffset;
    }
    /**
     * 得到字体偏移位置
     * @return int
     */
    public int getTextOffset()
    {
        return textOffset;
    }
    /**
     * 设置自动回车
     * @param textAutoEnter boolean
     */
    public void setTextAutoEnter(boolean textAutoEnter)
    {
        this.textAutoEnter = textAutoEnter;
    }
    /**
     * 得到自动回车
     * @return boolean
     */
    public boolean isTextAutoEnter()
    {
        return textAutoEnter;
    }
    /**
     * 设置自动回车偏移
     * @param textEnterOffset int
     */
    public void setTextEnterOffset(int textEnterOffset)
    {
        this.textEnterOffset = textEnterOffset;
    }
    /**
     * 得到自动回车偏移
     * @return int
     */
    public int getTextEnterOffset()
    {
        return textEnterOffset;
    }
    /**
     * 设置偏移格式
     * @param textOffsetFormat String
     */
    public void setTextOffsetFormat(String textOffsetFormat)
    {
        this.textOffsetFormat = textOffsetFormat;
    }
    /**
     * 得到偏移格式
     * @return String
     */
    public String getTextOffsetFormat()
    {
        return textOffsetFormat;
    }
    /**
     * 格式化
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
     * 设置对其方式
     * @param textAlign int
     */
    public void setTextAlign(int textAlign)
    {
        this.textAlign = textAlign;
    }
    /**
     * 得到对其方式
     * @return int
     */
    public int getTextAlign()
    {
        return textAlign;
    }
    /**
     * 设置字体中间
     * @param textCenter boolean
     */
    public void setTextCenter(boolean textCenter)
    {
        this.textCenter = textCenter;
    }
    /**
     * 是否字体中间
     * @return boolean
     */
    public boolean isTextCenter()
    {
        return textCenter;
    }
    /**
     * 绘制轴
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        if(!isVisible())
            return;
        if(getWay())
        {
            //绘制次标尺线
            drawSecondAxisForward(g);
            //绘制主标尺线
            drawMainAxisForward(g);
            //绘制轴
            drawForward(g);
            //绘制文字
            drawTextForward(g);
        }
        else
        {
            //绘制次标尺线
            drawSecondAxisReverse(g);
            //绘制主标尺线
            drawMainAxisReverse(g);
            //绘制轴
            drawReverse(g);
            //绘制文字
            drawTextReverse(g);
        }
    }
    /**
     * 得到线类型对象
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
     * 得到主标尺线类型对象
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
     * 得到次标尺线类型对象
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
     * 得到背景主标尺线类型对象
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
     * 得到背景次标尺线类型对象
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
     * 绘制轴-正向
     * @param g Graphics
     */
    public void drawForward(Graphics g)
    {
        //设置主线形
        ((Graphics2D)g).setStroke(getMainStroke());
        //设置主线色
        g.setColor(getMainLineColor());
        g.drawLine(getX(),getY(),getX(),getY() + getHeight());
    }
    /**
     * 绘制轴-反向
     * @param g Graphics
     */
    public void drawReverse(Graphics g)
    {
        //设置主线形
        ((Graphics2D)g).setStroke(getMainStroke());
        //设置主线色
        g.setColor(getMainLineColor());
        g.drawLine(getX(),getY() + getHeight(),getX() + getWidth(),getY() + getHeight());
    }
    /**
     * 绘制主标尺线-正向
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
            case 1: //外部
                x1 = getX() - getMainWidth();
                x2 = getX();
                break;
            case 2: //内部
                x1 = getX();
                x2 = getX() + getMainWidth();
                break;
            case 3: //交叉
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
            case 1: //外部
                x1 = getX() + getWidth();
                x2 = getX() + getWidth() + getMainWidth();
                break;
            case 2: //内部
                x1 = getX() + getWidth() - getMainWidth();
                x2 = getX() + getWidth();
                break;
            case 3: //交叉
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
     * 绘制主标尺线-正向
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
            case 1: //外部
                y1 = getY() + getHeight();
                y2 = getY() + getHeight() + getMainWidth();
                break;
            case 2: //内部
                y1 = getY() + getHeight() - getMainWidth();
                y2 = getY() + getHeight();
                break;
            case 3: //交叉
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
            case 1: //外部
                y1 = getY() - getMainWidth();
                y2 = getY();
                break;
            case 2: //内部
                y1 = getY();
                y2 = getY() + getMainWidth();
                break;
            case 3: //交叉
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
     * 绘制次标尺线-正向
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
            case 1: //外部
                x1 = getX() - getSecondWidth();
                x2 = getX();
                break;
            case 2: //内部
                x1 = getX();
                x2 = getX() + getSecondWidth();
                break;
            case 3: //交叉
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
            case 1: //外部
                x1 = getX() + getWidth();
                x2 = getX() + getWidth() + getSecondWidth();
                break;
            case 2: //内部
                x1 = getX() + getWidth() - getSecondWidth();
                x2 = getX() + getWidth();
                break;
            case 3: //交叉
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
     * 绘制次标尺线-正向
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
            case 1: //外部
                y1 = getY() + getHeight();
                y2 = getY() + getHeight() + getSecondWidth();
                break;
            case 2: //内部
                y1 = getY() + getHeight() - getSecondWidth();
                y2 = getY() + getHeight();
                break;
            case 3: //交叉
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
            case 1: //外部
                y1 = getY() - getSecondWidth();
                y2 = getY();
                break;
            case 2: //内部
                y1 = getY();
                y2 = getY() + getSecondWidth();
                break;
            case 3: //交叉
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
     * 得到绘制字体
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
     * 绘制文字-正向
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
        if (this.isTextWay(1))//左
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
                    //旋转修补
                    if(getTextAlign() == 0)
                        x0 = x - fm.stringWidth(s);
                    if(getTextAlign() == 1)
                        x0 = x - ascent;
                    g.drawString(s, x0, y);
                }
            }
        }
        if (this.isTextWay(2))//右
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
     * 绘制文字-反向
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
        if (this.isTextWay(1))//下
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
                //字体居中
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
        if (this.isTextWay(2))//上
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
                //字体居中
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
     * 绘制背景轴
     * @param g Graphics
     */
    public void paintBackground(Graphics g)
    {
        if(getWay())
        {
            //绘制次标尺线
            drawBackSecondAxisForward(g);
            //绘制主标尺线
            drawBackMainAxisForward(g);
        }
        else
        {
            //绘制次标尺线
            drawBackSecondAxisReverse(g);
            //绘制主标尺线
            drawBackMainAxisReverse(g);
        }
    }
    /**
     * 绘制背景主标尺线-正向
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
     * 绘制背景主标尺线-反向
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
     * 绘制次标尺背景线-正向
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
     * 绘制次标尺背景线-正向
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
     * 得到轴偏移量 X
     * @param index int
     * @return int
     */
    public int getAxisX(int index)
    {
        double unit = getDrawMainUnit(getWidth());
        return getX() + (int)((double)index * unit);
    }
    /**
     * 得到轴偏移量 Y
     * @param index int
     * @return int
     */
    public int getAxisY(int index)
    {
        double unit = getDrawMainUnit(getHeight());
        return getY() + (int)((double)index * unit);
    }
    /**
     * 得到轴偏移量(中间) X
     * @param index int
     * @return int
     */
    public int getAxisCenterX(int index)
    {
        double unit = getDrawMainUnit(getWidth());
        return getX() + (int)((double)index * unit + unit / 2.0);
    }
    /**
     * 得到轴偏移量(中间) Y
     * @param index int
     * @return int
     */
    public int getAxisCenterY(int index)
    {
        double unit = getDrawMainUnit(getHeight());
        return getY() + (int)((double)index * unit + unit / 2.0);
    }
    /**
     * 计算数值宽度
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
     * 计算数值高度
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
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        //写对象属性
        writeObjectAttribute(s);
        s.writeShort( -1);
        //保存页
        s.writeInt(0);
    }
    /**
     * 读对象
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        int id = s.readShort();
        while (id > 0)
        {
            //读对象属性
            readObjectAttribute(id,s);
            id = s.readShort();
        }
        //读取行
        int count = s.readInt();
    }
    /**
     * 写对象属性
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
     * 读对象属性
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
