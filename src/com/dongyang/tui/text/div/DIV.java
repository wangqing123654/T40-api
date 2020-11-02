package com.dongyang.tui.text.div;

import java.awt.Graphics;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.text.PM;

/**
 *
 * <p>Title: 浮动组件接口</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.5.21
 * @version 1.0
 */
public interface DIV
{
    /**
     * 设置父类
     * @param parent MV
     */
    public void setParent(MV parent);
    /**
     * 得到父类
     * @return MV
     */
    public MV getParent();
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM();
    /**
     * 绘制
     * @param g Graphics
     */
    public void paint(Graphics g);
    /**
     * 打印
     * @param g Graphics
     */
    public void print(Graphics g);
    /**
     * 设置是否显示
     * @param visible boolean
     */
    public void setVisible(boolean visible);
    /**
     * 是否显示
     * @return boolean
     */
    public boolean isVisible();
    /**
     * 设置名称
     * @param name String
     */
    public void setName(String name);
    /**
     * 得到名称
     * @return String
     */
    public String getName();
    /**
     * 得到类型
     * @return String
     */
    public String getType();
    /**
     * 打开属性对话框
     */
    public void openProperty();
    /**
     * 属性对话框修改参数
     * @param name String
     */
    public void propertyModify(String name);
    /**
     * 释放
     */
    public void DC();
    /**
     * 得到类型
     * @return int
     */
    public int getTypeID();
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException;
    /**
     * 读对象
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException;
    /**
     * 父类是否是宏模型
     * @return boolean
     */
    public boolean isMacroroutineModel();
    /**
     * 得到数据
     * @param column String
     * @return Object
     */
    public Object getMacroroutineData(String column);
    /**
     * 得到页码
     * @return int
     */
    public int getPageIndex();
}
