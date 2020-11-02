package com.dongyang.tui.text;

/**
 *
 * <p>Title: 文本组件重用接口扩展类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.11.21
 * @version 1.0
 */
public abstract class ETextSpecialIO extends ETextBase
{
    /**
     * 删除指定区域内容扩展接口
     * @param start int 开始位置
     * @param end int 结束位置
     * @return boolean true 阻止后续动作 false 处理标准动作
     */
    public boolean onDeleteActionIO(int start,int end)
    {
        return false;
    }
    /**
     * 得到显示字串
     * @return String
     */
    public String getShowString()
    {
        return getString();
    }
    /**
     * 得到显示字符的长度
     * @return int
     */
    public int getShowStringWidth()
    {
        return getStyle().stringWidth(getShowString());
    }
    /**
     * 能否拆分
     * @return boolean
     */
    public boolean canSeparate()
    {
        return true;
    }
    /**
     * 得到显示左面孔穴宽度
     * @return int
     */
    public int getShowLeftW()
    {
        return 0;
    }
    /**
     * 得到显示右面孔穴宽度
     * @return int
     */
    public int getShowRightW()
    {
        return 0;
    }
}
