package com.dongyang.tui.text;

import com.dongyang.util.TList;
import java.util.List;

/**
 *
 * <p>Title: 区域块</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author 2009.4.3
 * @version 1.0
 */
public interface IBlock extends EComponent
{
    /**
     * 设置位置
     * @param position int
     */
    public void setPosition(int position);
    /**
     * 得到位置
     * @return int
     */
    public int getPosition();
    /**
     * 查找自己的位置
     * @return int
     */
    public int findIndex();
    /**
     * 得到坐标位置
     * @return String
     */
    public String getIndexString();
    /**
     * 得到路径
     * @param list TList
     */
    public void getPath(TList list);
    /**
     * 调试修改
     * @param i int
     */
    public void debugModify(int i);
    /**
     * 调试对象组成
     * @param i int
     */
    public void debugShow(int i);
    /**
     * 刷新数据
     * @param action EAction
     */
    public void resetData(EAction action);
    /**
     * 得到对象类型
     * @return int
     */
    public int getObjectType();
    /**
     * 查找对象
     * @param name String 名称
     * @param type int 类型
     * @return EComponent
     */
    public EComponent findObject(String name,int type);
    /**
     * 过滤对象
     * @param list List
     * @param name String
     * @param type int
     */
    public void filterObject(List list,String name,int type);
    /**
     * 查找组
     * @param group String
     * @return EComponent
     */
    public EComponent findGroup(String group);
    /**
     * 得到组值
     * @param group String
     * @return String
     */
    public String findGroupValue(String group);
    /**
     * 得到块值
     * @return String
     */
    public String getBlockValue();
    /**
     * 得到上一个真块
     * @return IBlock
     */
    public IBlock getPreviousTrueBlock();
    /**
     * 得到下一个真块
     * @return IBlock
     */
    public IBlock getNextTrueBlock();
    /**
     * 得到面板
     * @return EPanel
     */
    public EPanel getPanel();
    /**
     * 克隆
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel);
    /**
     * 整理
     */
    public void arrangement();
}
