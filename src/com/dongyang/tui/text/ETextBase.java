package com.dongyang.tui.text;

import java.util.List;

import com.dongyang.ui.TWord;

/**
 *
 * <p>Title: 文本元素基础类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.9.22
 * @author whao 2013
 * @version 1.0
 */
public abstract class ETextBase implements IBlock
{
    /**
     * 父类
     */
    private EComponent parent;
    /**
     * 横坐标
     */
    private int x;
    /**
     * 纵坐标
     */
    private int y;
    /**
     * 宽度
     */
    private int width;
    /**
     * 高度
     */
    private int height;
    /**
     * 风格
     */
    private DStyle style;
    /**
     * 修改状态
     */
    private boolean modify;
    /**
     * 设置启示位置
     */
    private int start;
    /**
     * 设置结束位置
     */
    private int end;
    /**
     * 上一个连接Text
     */
    private EText previousLinkText;
    /**
     * 下一个连接Text
     */
    private EText nextLinkText;
    /**
     * 上一个Text索引号码(保存专用)
     */
    private int previousTextIndex;
    /**
     * 位置
     * 0 中间
     * 1 行首
     * 2 行尾
     */
    private int position;
    /**
     * 最大宽度
     */
    private int maxWidth;
    /**
     * 最大高度
     */
    private int maxHeight;
    /**
     * 签字编号
     */
    private int keyIndex = -1;
    /**
     * 删除标记
     */
    private boolean isDeleteFlg;
    /**
     * 设置父类
     * @param parent EComponent
     */
    public void setParent(EComponent parent)
    {
        this.parent = parent;
    }
    /**
     * 得到父类
     * @return EComponent
     */
    public EComponent getParent()
    {
        return parent;
    }
    /**
     * 查找自己的位置
     * @return int
     */
    public int findIndex()
    {
        return findIndex(this);
    }
    /**
     * 查找组件位置
     * @param com EComponent
     * @return int
     */
    public int findIndex(IBlock com)
    {
        return getPanel().indexOf(com);
    }
    /**
     * 设置横坐标
     * @param x int
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * 得到横坐标
     * @return int
     */
    public int getX()
    {
        return x;
    }
    /**
     * 设置纵坐标
     * @param y int
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * 得到纵坐标
     * @return int
     */
    public int getY()
    {
        return y;
    }
    /**
     * 设置位置
     * @param x int
     * @param y int
     */
    public void setLocation(int x,int y)
    {
        setX(x);
        setY(y);
    }
    /**
     * 设置宽度
     * @param width int
     */
    public void setWidth(int width)
    {
        this.width = width;
    }
    /**
     * 得到宽度
     * @return int
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height)
    {
        this.height = height;
    }
    /**
     * 得到高度
     * @return int
     */
    public int getHeight()
    {
        return height;
    }
    /**
     * 设置边框
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void setBounds(int x,int y,int width,int height)
    {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
    /**
     * 设置风格
     * @param style DStyle
     */
    public void setStyle(DStyle style)
    {
        if(this.style == style)
            return;
        this.style = style;
        setModify(true);
    }
    /**
     * 设置其它的风格
     * @param style DStyle
     */
    public void setStyleOther(DStyle style)
    {
        setStyle(getStyleManager().getStyle(style.copy()));
    }
    /**
     * 设置风格
     * @param index int
     */
    public void setStyle(int index)
    {
        setStyle(getStyleManager().get(index));
    }
    /**
     * 得到风格
     * @return DStyle
     */
    public DStyle getStyle()
    {
    	//
    	if(style==null){
    		MStyle style = getStyleManager();
            if (style == null) {
                return null;
            }
            return style.get("default");
    	}
    	//
        return style;
    }
    /**
     * 得到面板
     * @return EPanel
     */
    public EPanel getPanel()
    {
        EComponent component = getParent();
        if(component == null)
            return null;
        if(component instanceof EPanel)
            return (EPanel)component;
        return null;
    }
    /**
     * 得到字符数据
     * @return DString
     */
    public DString getDString()
    {
        return getPanel().getDString();
    }
    /**
     * 得到显示字符
     * @return String
     */
    public String getString()
    {
        DString s = getDString();
        if(s == null)
            return "";
        return s.getString(getStart(),getEnd());
    }
    /**
     * 得到指定位置的字符串
     * @param index int
     * @return String
     */
    public String getString(int index)
    {
        DString s = getDString();
        if(s == null)
            return "";
        return s.getString(getStart(),getStart() + index);
    }
    /**
     * 得到指定位置的字符串
     * @param start int
     * @param end int
     * @return String
     */
    public String getString(int start,int end)
    {
        DString s = getDString();
        if(s == null)
            return "";
        return s.getString(start,end);
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        EPanel panel = getPanel();
        if(panel == null)
            return null;
        return panel.getPM();
    }
    /**
     * 得到风格管理器
     * @return MStyle
     */
    public MStyle getStyleManager()
    {
        PM pm = getPM();
        if(pm == null)
            return null;
        return pm.getStyleManager();
    }
    /**
     * 得到焦点控制器
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
    }
    /**
     * 得到显示管理器
     * @return MView
     */
    public MView getViewManager()
    {
        return getPM().getViewManager();
    }
    /**
     * 得到页面管理器
     * @return MPage
     */
    public MPage getPageManager()
    {
        return getPM().getPageManager();
    }
    /**
     * 得到事件管理器
     * @return MEvent
     */
    public MEvent getEventManager()
    {
        return getPM().getEventManager();
    }
    /**
     * 得到语法管理器
     * @return MSyntax
     */
    public MSyntax getSyntaxManager()
    {
        return getPM().getSyntaxManager();
    }
    /**
     * 得到Ttext控制类
     * @return MTextSave
     */
    public MTextSave getTextSaveManager()
    {
        return getPM().getFileManager().getTextSaveManager();
    }
    /**
     * 得到修改记录管理器
     * @return MModifyNode
     */
    public MModifyNode getModifyNodeManager()
    {
        return getPM().getModifyNodeManager();
    }
    /**
     * 得到当前修改记录层次
     * @return int
     */
    public int getModifyNodeIndex()
    {
        if( TWord.IsMark ){

        	return getModifyNodeManager().getIndex();
        }else{

            //泰心专用
        	return -1;
        }
    }
    /**
     * 设置是否修改状态
     * @param modify boolean
     */
    public void setModify(boolean modify)
    {
        if(this.modify == modify)
            return;
        this.modify = modify;
        if(getParent() != null)
            getParent().setModify(modify);

    }
    /**
     * 是否修改
     * @return boolean
     */
    public boolean isModify()
    {
        return modify;
    }
    /**
     * 设置开始位置
     * @param start int
     */
    public void setStart(int start)
    {
        if(this.start == start)
            return;
        this.start = start;
        setModify(true);
    }
    /**
     * 得到开始位置
     * @return int
     */
    public int getStart()
    {
        return start;
    }
    /**
     * 设置结束位置
     * @param end int
     */
    public void setEnd(int end)
    {
        if(this.end == end)
            return;
        this.end = end;
        setModify(true);
    }
    /**
     * 得到结束位置
     * @return int
     */
    public int getEnd()
    {
        return end;
    }
    /**
     * 得到长度
     * @return int
     */
    public int getLength()
    {
        return getEnd() - getStart();
    }
    /**
     * 设置位置范围
     * @param start int
     * @param end int
     */
    public void setBlock(int start,int end)
    {
        setStart(start);
        setEnd(end);
    }
    /**
     * 索引位置调整
     * @param length int
     */
    public void indexMove(int length)
    {
        setStart(getStart() + length);
        setEnd(getEnd() + length);
    }
    /**
     * 设置上一个连接Text
     * @param previousLinkText EText
     */
    public void setPreviousLinkText(EText previousLinkText)
    {
        this.previousLinkText = previousLinkText;
    }
    /**
     * 得到上一个连接Text
     * @return EText
     */
    public EText getPreviousLinkText()
    {
        return previousLinkText;
    }
    /**
     * 设置下一个连接Text
     * @param nextLinkText EText
     */
    public void setNextLinkText(EText nextLinkText)
    {
        this.nextLinkText = nextLinkText;
    }
    /**
     * 得到下一个连接Text
     * @return EText
     */
    public EText getNextLinkText()
    {
        return nextLinkText;
    }
    /**
     * 设置上一个Text的索引号码(保存专用)
     * @param index int
     */
    public void setPreviousTextIndex(int index)
    {
        previousTextIndex = index;
    }
    /**
     * 得到上一个Text的索引号码(保存专用)
     * @return int
     */
    public int getPreviousTextIndex()
    {
        return previousTextIndex;
    }
    /**
     * 设置位置
     * @param position int
     */
    public void setPosition(int position)
    {
        this.position = position;
    }
    /**
     * 设置位置
     * @param position int
     */
    public void setPositionL(int position)
    {
        setPosition(position | getPosition());
    }
    /**
     * 得到位置
     * @return int
     */
    public int getPosition()
    {
        return position;
    }
    /**
     * 得到位置
     * @param position int
     * @return boolean
     */
    public boolean getPosition(int position)
    {
        return (getPosition() & position) == position;
    }
    /**
     * 设置最大宽度
     * @param maxWidth int
     */
    public void setMaxWidth(int maxWidth)
    {
        this.maxWidth = maxWidth;
    }
    /**
     * 得到最大宽度
     * @return int
     */
    public int getMaxWidth()
    {
        return maxWidth;
    }
    /**
     * 设置最大高度
     * @param maxHeight int
     */
    public void setMaxHeight(int maxHeight)
    {
        this.maxHeight = maxHeight;
    }
    /**
     * 得到最大高度
     * @return int
     */
    public int getMaxHeight()
    {
        return maxHeight;
    }
    /**
     * 设置焦点
     * @return boolean
     */
    public boolean setFocus()
    {
        return setFocus(0);
    }
    /**
     * 移动焦点
     * @param index int
     * @return boolean
     */
    public boolean moveFocus(int index)
    {
        return setFocus(getFocusIndex() + index);
    }
    /**
     * 设置焦点
     * @param index int 焦点位置
     * @return boolean
     */
    public boolean setFocus(int index)
    {
        setFocus((EText)this,index);
        return true;
    }
    /**
     * 设置焦点到最后
     * @return boolean
     */
    public boolean setFocusLast()
    {
        setFocus((EText)this,getLength());
        return true;
    }
    /**
     * 设置焦点
     * @param text EText
     * @param index int
     */
    public void setFocus(EText text,int index)
    {
        getFocusManager().setFocusText(text,index);
    }
    /**
     * 得到焦点
     * @return EComponent
     */
    public EComponent getFocus()
    {
        return getFocusManager().getFocus();
    }
    /**
     * 设置焦点位置
     * @param focusIndex int
     */
    public void setFocusIndex(int focusIndex)
    {
        getFocusManager().setFocusIndex(focusIndex);
    }
    /**
     * 得到焦点位置
     * @return int
     */
    public int getFocusIndex()
    {
        return getFocusManager().getFocusIndex();
    }
    /**
     * 得到缩放比例
     * @return double
     */
    public double getZoom()
    {
        return getViewManager().getZoom();
    }
    /**
     * 锁定编辑
     * @return boolean
     */
    public boolean isLockEdit()
    {
        return getPanel().isLockEdit();
    }
    /**
     * 能否编辑
     * @return boolean
     */
    public boolean canEdit()
    {
        if(!getEventManager().canEdit())
            return false;
        if(isLockEdit())
            return false;
        return true;
    }
    /**
     * 得到对象类型
     * @return int
     */
    public int getObjectType()
    {
        return TEXT_TYPE;
    }
    /**
     * 查找对象
     * @param name String 名称
     * @param type int 类型
     * @return EComponent
     */
    public EComponent findObject(String name,int type)
    {
        if(getObjectType() == type)
            return this;
        return null;
    }
    /**
     * 过滤对象
     * @param list List
     * @param name String
     * @param type int
     */
    public void filterObject(List list,String name,int type)
    {

    }
    /**
     * 查询组
     * @param groupName String
     * @return EComponent
     */
    public EComponent findGroup(String groupName)
    {
        return null;
    }
    /**
     * 得到组值
     * @param group String
     * @return String
     */
    public String findGroupValue(String group)
    {
        return null;
    }
    /**
     * 是否阅览状态
     * @return boolean
     */
    public boolean isPreview()
    {
        return getViewManager().isPreview();
    }
    /**
     * 是否鼠标拖动
     * @return boolean
     */
    public boolean isMouseDragged()
    {
        return getEventManager().isMouseDragged();
    }
    /**
     * 设置签字编号
     * @param keyIndex int
     */
    public void setKeyIndex(int keyIndex)
    {
        this.keyIndex = keyIndex;
    }
    /**
     * 得到签字编号
     * @return int
     */
    public int getKeyIndex()
    {
        if( TWord.IsMark ){

        	return keyIndex;
        }else{

            //泰心专用
        	return -1;
        }
    }
    /**
     * 设置删除标记
     * @param isDeleteFlg boolean
     */
    public void setDeleteFlg(boolean isDeleteFlg)
    {
        if( TWord.IsMark ){

        	this.isDeleteFlg = isDeleteFlg;
        }else{

            //泰心专用
        	this.isDeleteFlg =false;
        }

    }
    /**
     * 是否删除
     * @return boolean
     */
    public boolean isDeleteFlg()
    {
        return isDeleteFlg;
    }
}
