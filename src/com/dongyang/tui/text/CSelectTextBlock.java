package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: 选中一个文本块</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.11.7
 * @version 1.0
 */
public class CSelectTextBlock implements IExeAction {
    /**
     * 文本对象
     */
    private EText text;
    /**
     * 所属选中列表
     */
    private TList selectModel;
    /**
     * 起始位置
     */
    private int start;
    /**
     * 结束位置
     */
    private int end;
    /**
     * 是否是选蓝的开始点
     */
    private boolean isStartPoint;
    /**
     * 是否是选蓝的结束点
     */
    private boolean isStopPoint;



    /**
     * 构造器
     * @param text EText
     * @param start int
     * @param end int
     */
    public CSelectTextBlock(EText text, int start, int end) {
        setText(text);
        setIndex(start, end);
    }

    /**
     * 设置文本对象
     * @param text EText
     */
    public void setText(EText text) {
        this.text = text;
    }

    /**
     * 得到文本对象
     * @return EText
     */
    public EText getText() {
        return text;
    }

    /**
     * 设置起始位置
     * @param start int
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * 得到开始位置
     * @return int
     */
    public int getStart() {
        return start;
    }

    /**
     * 设置结束位置
     * @param end int
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * 得到结束位置
     * @return int
     */
    public int getEnd() {
        return end;
    }

    /**
     * 设置位置
     * @param start int
     * @param end int
     */
    public void setIndex(int start, int end) {
        setStart(start);
        setEnd(end);
    }

    /**
     * 是否存在交集
     * @param block CSelectTextBlock
     * @return boolean
     */
    public boolean isIncludes(CSelectTextBlock block) {
        if (block == null) {
            return false;
        }
        if (block.getEnd() <= getStart()) {
            return false;
        }
        if (block.getStart() >= getEnd()) {
            return false;
        }
        return true;
    }

    /**
     * 位置是否选蓝
     * @param index int
     * @return boolean
     */
    public boolean isInclude(int index) {
        if (getStart() == getEnd()) {
            return false;
        }
        return index >= getStart() && index < getEnd();
    }

    /**
     * 清除自己
     */
    public void removeThis() {
        //test
        //getText().getSelectedModel().removeThisComponent(this);
        //test end
        getText().getSelectedModel().remove(this);
        getSelectModel().remove(this);
        //断开与选中列表的连接
        setSelectModel(null);
    }

    public String toString() {
        return "[" + getStart() + "," + getEnd() + "]";
    }

    /**
     * 设置选中列表
     * @param selectModel TList
     */
    public void setSelectModel(TList selectModel) {
        this.selectModel = selectModel;
    }

    /**
     * 得到选中列表
     * @return TList
     */
    public TList getSelectModel() {
        return selectModel;
    }

    /**
     * 设置是否是起点
     * @param isStartPoint boolean
     */
    public void setStartPoint(boolean isStartPoint) {
        this.isStartPoint = isStartPoint;
    }

    /**
     * 是否是起点
     * @return boolean
     */
    public boolean isStartPoint() {
        return isStartPoint;
    }

    /**
     * 设置是否是结束点
     * @param isStopPoint boolean
     */
    public void setStopPoint(boolean isStopPoint) {
        this.isStopPoint = isStopPoint;
    }

    /**
     * 是否是结束点
     * @return boolean
     */
    public boolean isStopPoint() {
        return isStopPoint;
    }

    /**
     * 移动选蓝位置
     * @param position int
     */
    public void move(int position) {
        setStart(getStart() + position);
        setEnd(getEnd() + position);
    }

    /**
     * 专用执行动作
     * 为处理后续继承的固定文本等组件进行动作分支
     * @param action EAction
     * @return boolean
     */
    public boolean specialExeAction(EAction action) {
        //System.out.println("======action type========"+action.getType());
        switch (action.getType()) {
        case EAction.DELETE: //删除动作
            //其他后续组件对删除的特定处理(删除扩展接口)
            if (action.getInt(0) != 0 &&
                getText().onDeleteActionIO(getStart(), getEnd())) {
                //删除元件测试
                getText().getSelectedModel().removeThisComponent(this);
                //删除元件测试

               //清除选蓝
                removeThis();
                return true;
            }
            return false;
        case EAction.COPY:
            CopyOperator copyOperator = action.getCopyOperator(0);
            //$$========added by lx 2011-09-06 加入控件复制功能START============$$//
             copyComponent(getText(),copyOperator);
            //$$========added by lx 2011-09-06 加入控件复制功能END============$$//
            //字符串复制
            String s = getText().getString(getText().getStart() + getStart(),
                                           getText().getStart() + getEnd());
            //System.out.println("==字符串复制s=="+s);

            copyOperator.addString(s);
            return true;
        }

        return false;
    }

    /**
     * 执行动作
     * @param action EAction
     */
    public void exeAction(EAction action) {
        //System.out.println("========action=========="+action.getType());
        if (action == null) {
            return;
        }
        //如果是专用动作有其他处理流程
        if (specialExeAction(action)) {
            return;
        }
        //如果选蓝这个组件的全部内容
        if (getStart() == 0 && getEnd() == getText().getLength()) {
            switch (action.getType()) {
            case EAction.FONT_NAME:
                getText().modifyFont(action.getString(0));
                break;
            case EAction.FONT_SIZE:
                getText().modifyFontSize(action.getInt(0));
                break;
            case EAction.FONT_BOLD:
                getText().modifyBold(action.getBoolean(0));
                break;
            case EAction.FONT_ITALIC:
                getText().modifyItalic(action.getBoolean(0));
                break;
            case EAction.DELETE:
                if (getText().isLockEdit()) {
                    return;
                }
                getText().onDeleteThis();
                return;
            }
            //如果是选中的起始点切断前面的连接
            if (isStartPoint()) {
                getText().clearPreviousLink();
            }
            //如果是选中的结束点切断后面的连接
            if (isStopPoint()) {
                getText().clearNextLink();
            }
            return;
        }
        //分割前部
        if (getStart() > 0) {
            getText().separateText(getStart(), isStartPoint());
        }
        //分割后部
        if (getEnd() < getText().getLength()) {
            getText().separateText(getEnd(), isStopPoint());
        }
        //重新执行
        exeAction(action);
    }

    /**
     * 拷贝控件
     * @param text EText
     */
    private void copyComponent(EText text,CopyOperator copyOperator) {
        //判断属于哪个类型，再将其序列化保存
        if (getText().getClass() != null) {
            String thisClass=getText().getClass().toString();
            String thisValue= getText().getBlockValue();
            //System.out.println("=======CSelectTextBlock thisClass==========="+thisClass);
            //System.out.println("=======CSelectTextBlock thisValue==========="+thisValue);
            boolean isCopy = true;
            //解决元件自动换行多出元件的bug.
            if (copyOperator.getTempTextClass() != null) {
                //类型与值都相同说明是重的不加入;
                /*if (copyOperator.getTempTextClass().equalsIgnoreCase(thisClass) &&
                    copyOperator.getTempTextValue().equalsIgnoreCase(thisValue)) {
                    isCopy=false;
                }else{*/
                    copyOperator.setTempTextClass(thisClass);
                    copyOperator.setTempTextValue(thisValue);
                //}
            } else {
                copyOperator.setTempTextClass(thisClass);
                copyOperator.setTempTextValue(thisValue);
            }
            if (isCopy) {
                copyOperator.addComList(getText());
            }
        }


    }

}
