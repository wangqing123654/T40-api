package com.dongyang.tui.text;

import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.tui.text.ui.CTable;
import com.dongyang.data.TParm;
import java.util.Vector;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: 宏</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.4.11
 * @version 1.0
 */
public class EMacroroutine extends EText
{
    /**
     * 前一个连接
     */
    private EMacroroutine previousMacroroutine;
    /**
     * 下一个连接
     */
    private EMacroroutine nextMacroroutine;
    /**
     * 宏模型
     */
    private EMacroroutineModel model;
    /**
     * 是否释放Model
     */
    private boolean freeModel = true;
    /**
     * 是否是图片
     */
    private boolean isPic;
    
    
    private int sum=0;

    /**
     * 显示文本
     */
    String showValue;

    /**
     * 构造器
     * @param panel EPanel
     */
    public EMacroroutine(EPanel panel)
    {
        super(panel);
    }
    /**
     * 得到宏控制器
     * @return MMacroroutine
     */
    public MMacroroutine getMacroroutineManager()
    {
        return getPM().getMacroroutineManager();
    }
    /**
     * 设置前一个连接
     * @param previousMacroroutine EMacroroutine
     */
    public void setPreviousMacroroutine(EMacroroutine previousMacroroutine)
    {
        this.previousMacroroutine = previousMacroroutine;
    }
    /**
     * 得到前一个连接
     * @return EMacroroutine
     */
    public EMacroroutine getPreviousMacroroutine()
    {
        return previousMacroroutine;
    }
    /**
     * 设置下一个连接
     * @param nextMacroroutine EMacroroutine
     */
    public void setNextMacroroutine(EMacroroutine nextMacroroutine)
    {
        this.nextMacroroutine = nextMacroroutine;
    }
    /**
     * 得到下一个连接
     * @return EMacroroutine
     */
    public EMacroroutine getNextMacroroutine()
    {
        return nextMacroroutine;
    }
    /**
     * 设置是否是图片
     * @param isPic boolean
     */
    public void setIsPic(boolean isPic)
    {
        this.isPic = isPic;
    }
    /**
     * 是否是图片
     * @return boolean
     */
    public boolean isPic()
    {
        return isPic;
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
     * 绘制
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paint(Graphics g,int x,int y,int width,int height)
    {
        if(getModel() != null)
        {
            getModel().setMacroroutineNow(this);
            //绘制背景
            getModel().paintBackground(g,x,y, 0);
            getModel().setMacroroutineNow(null);
        }
        if(!isPic())
            super.paint(g,x,y,width,height);
        if(getModel() != null)
        {
            getModel().setMacroroutineNow(this);
            //绘制前景
            getModel().paintForeground(g,x,y, 0);
            getModel().setMacroroutineNow(null);
        }
    }
    /**
     * 绘制焦点背景
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintFocusBack(Graphics g,int x,int y,int width,int height)
    {
        if(getViewManager().isMacroroutineDebug())
        {
            g.setColor(new Color(240, 168, 168));
            g.drawRect(x, y, width, height);
        }
        if(!isPreview())
        {
            g.setColor(new Color(240, 168, 168));
            g.drawRect(x, y, width, height);
        }
        if(isFocus())
            paintMacroroutineFocusBack(g,x,y,width,height);
    }
    /**
     * 绘制宏文本焦点背景
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintMacroroutineFocusBack(Graphics g,int x,int y,int width,int height)
    {
        if(getModel() != null && getModel().isLockSize())
            return;
        g.setColor(new Color(240, 168, 168));
        g.fillRect(x, y, width, height);
    }
    /**
     * 当前焦点
     * @return boolean
     */
    public boolean isFocus()
    {
        EComponent com = getFocus();
        if(!(com instanceof EMacroroutine))
            return false;
        EMacroroutine macroroutine = (EMacroroutine)com;
        if(macroroutine == this)
            return true;
        EMacroroutine n = macroroutine.getPreviousMacroroutine();
        while(n != null)
        {
            if(n == this)
                return true;
            n = n.getPreviousMacroroutine();
        }
        n = macroroutine.getNextMacroroutine();
        while(n != null)
        {
            if(n == this)
                return true;
            n = n.getNextMacroroutine();
        }
        return false;
    }
    /**
     * 打印
     * @param g Graphics
     * @param x int
     * @param y int
     */
    public void print(Graphics g,int x,int y)
    {
        if(getModel() != null)
        {
            getModel().setMacroroutineNow(this);
            //绘制背景
            getModel().printBackground(g, x, y, 0);
            getModel().setMacroroutineNow(null);
        }
        if(!isPic())
            super.print(g,x,y);
        if(getModel() != null)
        {
            //绘制前景
            getModel().printForeground(g, x, y, 0);
            getModel().setMacroroutineNow(null);
        }
    }
    /**
     * 得到分割新组件
     * @param index int
     * @return EText
     */
    public EText getSeparateNewText(int index)
    {
        EMacroroutine macroroutine = getPanel().newMacroroutine(index);
        //向下连接
        if(getNextMacroroutine() != null)
        {
            macroroutine.setNextMacroroutine(getNextMacroroutine());
            getNextMacroroutine().setPreviousMacroroutine(macroroutine);
        }
        setNextMacroroutine(macroroutine);
        macroroutine.setPreviousMacroroutine(this);

        return macroroutine;
    }
    /**
     * 能否合并
     * @param block IBlock
     * @return boolean
     */
    public boolean unite(IBlock block)
    {
        if(block == null)
            return false;
        if(!(block instanceof EMacroroutine))
            return false;
        EMacroroutine macroroutine = (EMacroroutine)block;
        if(getPreviousMacroroutine() != null && getPreviousMacroroutine() == macroroutine)
        {
            macroroutine.setEnd(getEnd());
            if(getNextMacroroutine() != null)
            {
                macroroutine.setNextMacroroutine(getNextMacroroutine());
                getNextMacroroutine().setPreviousMacroroutine(macroroutine);
            }else
                macroroutine.setNextMacroroutine(null);
            removeThis();
            return true;
        }
        if(getNextMacroroutine() != null && getNextMacroroutine() == macroroutine)
        {
            setEnd(macroroutine.getEnd());
            if(macroroutine.getNextMacroroutine() != null)
            {
                setNextMacroroutine(macroroutine.getNextMacroroutine());
                macroroutine.getNextMacroroutine().setPreviousMacroroutine(this);
            }else
                setNextMacroroutine(null);
            macroroutine.removeThis();
            return true;
        }
        return false;
    }
    /**
     * 得到宏文本的首对象
     * @return EMacroroutine
     */
    public EMacroroutine getHeadMacroroutine()
    {
        EMacroroutine n = getPreviousMacroroutine();
        if(n == null)
            return this;
        while(n.getPreviousMacroroutine() != null)
            n = n.getPreviousMacroroutine();
        return n;
    }
    /**
     * 合并全部的宏
     * @return EMacroroutine
     */
    public EMacroroutine uniteAll()
    {
        EMacroroutine macroroutine = getHeadMacroroutine();
        while(macroroutine.getNextMacroroutine() != null)
            macroroutine.unite(macroroutine.getNextMacroroutine());
        return macroroutine;
    }
    /**
     * 能否回车拆分组件
     * @return boolean
     */
    public boolean canEnterSeparate()
    {
        return false;
    }
    /**
     * 设置模型
     * @param model EMacroroutineModel
     */
    public void setModel(EMacroroutineModel model)
    {
        this.model = model;
    }
    /**
     * 得到模型
     * @return EMacroroutineModel
     */
    public EMacroroutineModel getModel()
    {
        if(getPreviousMacroroutine() != null)
            return getPreviousMacroroutine().getModel();
        return model;
    }
    /**
     * 设置语法
     * @param index int 位置
     * @param b boolean true 单值 false 多值
     */
    public void setModel(int index,boolean b)
    {
        if(index == -1)
            return;
        setModel(getMacroroutineManager().get(index));
        if(b)
            getModel().setMacroroutine(this);
        else
            getModel().addMacroroutine(this);

    }
    /**
     * 查找自己模型的位置
     * @return int
     */
    public int findModelIndex()
    {
        if(getPreviousMacroroutine() != null || getModel() == null)
            return -1;
        return getModel().findIndex();
    }
    /**
     * 设置模型
     * @return EMacroroutineModel
     */
    public EMacroroutineModel createModel()
    {
        EMacroroutineModel model = new EMacroroutineModel(this);
        setModel(model);
        //通知宏控制器
        getMacroroutineManager().add(model);
        return model;
    }
    /**
     * 删除
     */
    public void delete()
    {
        //合并全部的宏
        EMacroroutine macroroutine = uniteAll();
        macroroutine.setModify(true);
        macroroutine.free();
        macroroutine.removeThis();
    }
    /**
     * 释放自己
     */
    public void free()
    {
        if(getPreviousMacroroutine() == null && getModel() != null && isFreeModel())
            getModel().free();

        if(getNextMacroroutine() != null)
            getNextMacroroutine().setPreviousMacroroutine(getPreviousMacroroutine());
        if(getPreviousMacroroutine() != null)
            getPreviousMacroroutine().setNextMacroroutine(getNextMacroroutine());
    }
    /**
     * 双击
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        if(isPic())
        {
            Object value = (Object)getPM().getUI().openDialog("%ROOT%\\config\\database\\PictureEditDialog.x",this);
            if(value == null)
                return true;
            setModify(true);
            getFocusManager().update();
            return true;
        }
        if(getModel() == null)
            return false;
        return getModel().onDoubleClickedS(mouseX,mouseY);
    }
    /**
     * 得到Parm数据
     * @return TParm
     */
    public TParm getParmData()
    {
    	//System.out.println("EMacroroutine getParmData===================");
        CTable cTable = getCTable();
        if(cTable == null)
        {
            Object obj = getPM().getFileManager().getParameter();
            if(obj instanceof TParm)
                return (TParm)obj;
            return null;
        }
        //System.out.println("EMacroroutine getCTable().getData()==================="+getCTable().getData());
        
        return getCTable().getData();
    }
    /**
     * 得到数据
     * @param column int
     * @return Object
     */
    public Object getData(int column)
    {
        ETRModel etrModel= getTRModel();
        if(etrModel == null)
            return null;
        switch(etrModel.getType())
        {
        case ETRModel.COLUMN_TYPE:       	
        case ETRModel.COLUMN_MAX:
            return getData(etrModel.getRow(),column);
        case ETRModel.GROUP_HEAD:
        case ETRModel.GROUP_SUM:
            return getData(etrModel.getRow(),etrModel.getEndRow(),column);
        }
        return null;
    }
    /**
     * 得到数据行
     * @return int
     */
    public int getDataRow()
    {
        ETRModel etrModel= getTRModel();
        if(etrModel == null)
            return -1;
        
        return etrModel.getRow();
    }
    /**
     * 得到数据
     * @param row int
     * @param column int
     * @return Object
     */
    public Object getData(int row,int column)
    {
        TParm parm = getParmData();
        if(parm == null)
            return null;
        //返回列的全部数据
        if(row == -1)
            return parm.getData(column);
        
        return parm.getData(row,column);
    }
    /**
     * 得到数据
     * @param column String
     * @return Object
     */
    public Object getData(String column)
    {
        return getData(getDataRow(),column);
    }
    /**
     * 得到数据
     * @param row int
     * @param column String
     * @return Object
     */
    public Object getData(int row,String column)
    {
        TParm parm = getParmData();
        if(parm == null)
            return null;
        //返回列的全部数据
        if(row == -1)
            return parm.getData(column);

        return parm.getData(column,row);
    }
    /**
     * 得到数据区间
     * @param startRow int
     * @param endRow int
     * @param column int
     * @return Vector
     */
    public Vector getData(int startRow,int endRow,int column)
    {
        TParm parm = getParmData();
        if(parm == null)
            return null;
        Object obj = parm.getData(column);
        if(obj == null || !(obj instanceof Vector))
            return null;
        Vector v = (Vector)obj;
        return new Vector(v.subList(startRow,endRow));
    }
    public String toString()
    {
        return "<EMacroroutine " +
                "width=" + getWidth() +
                ",height=" + getHeight() +
                ",start=" + getStart() +
                ",end=" + getEnd() +
                ",s=" + getString() +
                ",position=" + getPosition() +
                ",previousMacroroutine=" + (getPreviousMacroroutine()==null?null:getPreviousMacroroutine().getIndexString()) +
                ",nextMacroroutine=" + (getNextMacroroutine()== null?null:getNextMacroroutine().getIndexString()) +
                ",previousLinkText=" + (getPreviousLinkText() == null?null:getPreviousLinkText().getIndexString()) +
                ",nextLinkText=" + (getNextLinkText() == null?null:getNextLinkText().getIndexString()) +
                ",index=" + getIndexString() + ">";
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @param c int
     * @throws IOException
     */
    public void writeObjectDebug(DataOutputStream s,int c)
      throws IOException
    {
        s.WO("<EMacroroutine>",c);
        s.WO(1,"Start",getStart(),"",c + 1);
        s.WO(2,"String",getString(),"",c + 1);
        s.WO(50,"ModelIndex",findModelIndex(),"",c + 1);
        s.WO("</EMacroroutine>",c);
    }
    /**
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        super.writeObjectAttribute(s);
        s.writeShort(50);
        s.writeInt(findModelIndex());
        s.writeBoolean(getModel()!=null?(getModel().getMacroroutine() == this):false);
        s.writeBoolean(51,getPreviousMacroroutine() != null,false);
        s.writeBoolean(52,isPic(),false);
        s.writeString(53,getShowValue(),"");
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
        if(super.readObjectAttribute(id,s))
            return true;
        switch (id)
        {
        case 50:
            setModel(s.readInt(),s.readBoolean());
            return true;
        case 51:
            if(s.readBoolean())
            {
                EComponent com = getPreviousComponent();
                if(com != null && com instanceof EMacroroutine)
                {
                    EMacroroutine macroroutine = (EMacroroutine)com;
                    setPreviousMacroroutine(macroroutine);
                    macroroutine.setNextMacroroutine(this);
                }
            }
            return true;
        case 52:
            setIsPic(s.readBoolean());
            return true;
        case 53:
            setShowValue(s.readString());
            return true;
        }
        return false;
    }
    /**
     * 设置是否释放Model
     * @param freeModel boolean
     */
    public void setFreeModel(boolean freeModel)
    {
        this.freeModel = freeModel;
    }
    /**
     * 是否释放Model
     * @return boolean
     */
    public boolean isFreeModel()
    {
        return freeModel;
    }
    public void modifyFontSize(int size)
    {
        super.modifyFontSize(size);
        if(getModel()!= null)
            getModel().setFont(getStyle().getFont());
    }
    /**
     * 粘贴字符
     * @param c char
     * @return boolean
     */
    public boolean pasteChar(char c)
    {
        int index = getFocusIndex();
        //光标在首位
        if (index == 0 && getPreviousMacroroutine() == null)
        {
            //如果前一个不是Text创建一个
            EText previousText = getPreviousText();
            if (previousText == null || previousText instanceof EFixed)
            {
                previousText = getPanel().newText(findIndex());
                previousText.setBlock(getStart(), getStart());
            }
            setFocus(previousText,previousText.getLength());
            previousText.pasteChar(c);
            setFocus(this,0);
            return true;
        }
        //光标在最末尾
        if (index == getLength() && getNextMacroroutine() == null)
        {
            //如果最后没有Text创建一个
            EText nextText = getNextText();
            if (nextText == null || nextText instanceof EFixed)
            {
                nextText = getPanel().newText(findIndex() + 1);
                nextText.setBlock(getEnd(), getEnd());
            }
            setFocus(nextText,0);
            return nextText.pasteChar(c);
        }
        //固定文本区域能否编辑
        if (canEdit(index, 1))
            return super.pasteChar(c);

        return false;
    }
    /**
     * 宏文本区域能否编辑
     * @param index int
     * @param type int
     *  1 输入字符
     *  2 向前删除
     *  3 向后删除
     * @return boolean true 能编辑 false 不能编辑
     */
    public boolean canEdit(int index,int type)
    {
        return false;
    }
    /**
     * 向前删除
     * 焦点在宏文本区域中执行向左删除操作需要屏蔽，调整光标位置向左移动一格
     * @return boolean
     */
    public boolean backspaceChar()
    {
        int index = getFocusIndex();
        if(index != 0)
        {
            //固定文本区域能否向前删除
            if(canEdit(index,2))
                return super.backspaceChar();
            setFocusIndex(getFocusIndex() - 1);
            return true;
        }
        return super.backspaceChar();
    }
    /**
     * 向后删除
     * 焦点在宏文本区域中执行向右删除操作需要屏蔽，调整光标位置向右移动一格
     * @return boolean
     */
    public boolean deleteChar()
    {
        int index = getFocusIndex();
        if(index != getLength())
        {
            //宏文本区域能否向后删除
            if(canEdit(index,3))
                return super.deleteChar();
            setFocusIndex(getFocusIndex() + 1);
            return true;
        }
        return super.deleteChar();
    }
    /**
     * 回车事件
     * 焦点在宏文本区域中执行回车换行分割操作需要屏蔽
     * @return boolean
     */
    public boolean onEnter()
    {
        int index = getFocusIndex();
        if(index == 0 || index == getLength())
            return super.onEnter();
        return true;
    }
    /**
     * 连接上一个Text
     * 连接被屏蔽
     */
    public void linkText()
    {
    }
    /**
     * 得到宏名称
     * @return String
     */
    public String getName()
    {
        if(getModel() == null)
            return "";
        return getModel().getName();
    }
    /**
     * 修改宏名称
     * @param name String
     */
    public void setName(String name)
    {
        if(getModel() == null)
            return;
        getModel().setName(name);
    }

    /**
     * 的到显示名称
     * @return String
     */
    public String getShowValue(){
        return showValue;
    }

    /**
     * 设置显示名称
     * @param showValue String
     */
    public void setShowValue(String showValue){
        this.showValue = showValue;
    }
}
