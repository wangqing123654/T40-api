package com.dongyang.tui.text;

import java.awt.Graphics;

/**
 *
 * <p>Title: 组件接口</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author 2009.3.11
 * @version 1.0
 */
public interface EComponent
{
    /**
     * 文本类型
     */
    public static final int TEXT_TYPE = 0;
    /**
     * 表格类型
     */
    public static final int TABLE_TYPE = 1;
    /**
     * 图区类型
     */
    public static final int PIC_TYPE = 2;
    /**
     * 固定文本类型
     */
    public static final int FIXED_TYPE = 3;
    /**
     * 单选类型
     */
    public static final int SINGLE_CHOOSE_TYPE = 4;
    /**
     * 多选类型
     */
    public static final int MULTI_CHOOSE_TYPE = 5;
    /**
     * 有无选择类型
     */
    public static final int HAS_CHOOSE_TYPE = 6;
    /**
     * 输入提示类型
     */
    public static final int INPUT_TEXT_TYPE = 7;
    /**
     * 输入宏类型
     */
    public static final int MICRO_FIELD_TYPE = 8;
    /**
     * 抓去对象类型
     */
    public static final int CAPTURE_TYPE = 9;
    /**
     * 选择框类型
     */
    public static final int CHECK_BOX_CHOOSE_TYPE = 10;
    /**
     * 图片编辑类型
     */
    public static final int IMAGE_TYPE = 11;
    /**
     * 固定文本类型过滤动作类型
     */
    public static final int FIXED_ACTION_TYPE = 12;
    /**
     * 下拉框属性
     */
    public static final int TEXTFORMAT_TYPE = 13;
    /**
     * 关联类型
     */
    public static final int ASSOCIATE_CHOOSE_TYPE = 14;
    /**
     * 数字类型
     */
    public static final int NUMBER_CHOOSE_TYPE = 15;
       
    /**
     * 签名类型
     */
    public static final int SIGN_TYPE = 16;

    /**
     * 设置父类
     * @param parent EComponent
     */
    public void setParent(EComponent parent);
    /**
     * 得到父类
     * @return EComponent
     */
    public EComponent getParent();
    /**
     * 设置横坐标
     * @param x int
     */
    public void setX(int x);
    /**
     * 得到横坐标
     * @return int
     */
    public int getX();
    /**
     * 设置纵坐标
     * @param y int
     */
    public void setY(int y);
    /**
     * 得到纵坐标
     * @return int
     */
    public int getY();
    /**
     * 设置位置
     * @param x int
     * @param y int
     */
    public void setLocation(int x,int y);

    /**
     * 设置宽度
     * @param width int
     */
    public void setWidth(int width);
    /**
     * 得到宽度
     * @return int
     */
    public int getWidth();
    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height);
    /**
     * 得到高度
     * @return int
     */
    public int getHeight();
    /**
     * 调整尺寸
     */
    public void reset();
    /**
     * 调整尺寸
     * @param index int
     */
    public void reset(int index);
    /**
     * 绘制
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paint(Graphics g,int x,int y,int width,int height);
    /**
     * 设置是否修改状态
     * @param modify boolean
     */
    public void setModify(boolean modify);
    /**
     * 是否修改
     * @return boolean
     */
    public boolean isModify();
    /**
     * 得到页面对象
     * @return EPage
     */
    public EPage getPage();
}
