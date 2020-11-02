package com.dongyang.ui.datawindow;

import java.awt.*;
import com.dongyang.ui.event.TDataWindowEvent;
/**
 *
 * <p>Title: 组件移动缩放控制设备接口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.07.27
 * @version 1.0
 */
public interface IMoveable extends TDataWindowEvent
{
  /**
   * 是否可以缩放
   * @return true 可以 false 不可以
   */
  public boolean isResizable();
  /**
   * 是否可以移动
   * @return  true 可以 false 不可以
   */
  public boolean isMoveable();
  /**
   * 得到组建位置
   * @return Point点对象
   */
  public Point getLocation();
  /**
   * 设置位置
   * @param x 横坐标
   * @param y 纵坐标
   */
  public void setLocation(int x,int y);
  /**
   * 设置光标样式
   * @param cursor 光标对象
   */
  public void setCursor(Cursor cursor);
  /**
   * 得到边框尺寸
   * @return Rectangle
   */
  public Rectangle getBounds();
  /**
   * 设置边框尺寸
   * @param x 横坐标
   * @param y 纵坐标
   * @param width 宽度
   * @param height 高度
   */
  public void setBounds(int x,int y,int width,int height);
  /**
   * 设置大小尺寸
   * @param width 宽度
   * @param height 高度
   */
  public void setSize(int width,int height);
  /**
   * 得到尺寸
   * @return Dimension
   */
  public Dimension getSize();
  /**
   * 得到所在容器
   * @return 容器
   */
  public Container getParent();
  /**
   * 得到X位置
   * @return X位置
   */
  public int getX();
  /**
   * 得到Y位置
   * @return Y位置
   */
  public int getY();
  /**
   * 设置X位置
   * @param x 位置
   */
  public void setX(int x);
  /**
   * 设置Y位置
   * @param y 位置
   */
  public void setY(int y);
  /**
   * 得到宽度
   * @return 宽度
   */
  public int getWidth();
  /**
   * 得到高度
   * @return 高度
   */
  public int getHeight();
  /**
   * 设置宽度
   * @param width 宽度
   */
  public void setWidth(int width);
  /**
   * 设置高度
   * @param height 高度
   */
  public void setHeight(int height);
  /**
   * 检测扩选
   * @param x1 左上 x
   * @param y1 左上 y
   * @param x2 右下 x
   * @param y2 右下 y
   * @param type 类型 1接触选 2包含选
   * @return true 在范围内 false 不再
   */
  public boolean checkInclude(int x1,int y1,int x2,int y2,int type);
  /**
   * 得到系统ID
   * @return 系统ID
   */
  public int getID();
  /**
   * 得到对象类型
   * @return 对象类型
   */
  public String getType();
  /**
   * 处理外部功能调用请求
   * @param action 功能名称
   * @param parm 参数
   */
  public void callEventListener(String action,Object parm);

}
