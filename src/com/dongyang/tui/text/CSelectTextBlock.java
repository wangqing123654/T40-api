package com.dongyang.tui.text;

import com.dongyang.util.TList;

/**
 *
 * <p>Title: ѡ��һ���ı���</p>
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
     * �ı�����
     */
    private EText text;
    /**
     * ����ѡ���б�
     */
    private TList selectModel;
    /**
     * ��ʼλ��
     */
    private int start;
    /**
     * ����λ��
     */
    private int end;
    /**
     * �Ƿ���ѡ���Ŀ�ʼ��
     */
    private boolean isStartPoint;
    /**
     * �Ƿ���ѡ���Ľ�����
     */
    private boolean isStopPoint;



    /**
     * ������
     * @param text EText
     * @param start int
     * @param end int
     */
    public CSelectTextBlock(EText text, int start, int end) {
        setText(text);
        setIndex(start, end);
    }

    /**
     * �����ı�����
     * @param text EText
     */
    public void setText(EText text) {
        this.text = text;
    }

    /**
     * �õ��ı�����
     * @return EText
     */
    public EText getText() {
        return text;
    }

    /**
     * ������ʼλ��
     * @param start int
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * �õ���ʼλ��
     * @return int
     */
    public int getStart() {
        return start;
    }

    /**
     * ���ý���λ��
     * @param end int
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * �õ�����λ��
     * @return int
     */
    public int getEnd() {
        return end;
    }

    /**
     * ����λ��
     * @param start int
     * @param end int
     */
    public void setIndex(int start, int end) {
        setStart(start);
        setEnd(end);
    }

    /**
     * �Ƿ���ڽ���
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
     * λ���Ƿ�ѡ��
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
     * ����Լ�
     */
    public void removeThis() {
        //test
        //getText().getSelectedModel().removeThisComponent(this);
        //test end
        getText().getSelectedModel().remove(this);
        getSelectModel().remove(this);
        //�Ͽ���ѡ���б������
        setSelectModel(null);
    }

    public String toString() {
        return "[" + getStart() + "," + getEnd() + "]";
    }

    /**
     * ����ѡ���б�
     * @param selectModel TList
     */
    public void setSelectModel(TList selectModel) {
        this.selectModel = selectModel;
    }

    /**
     * �õ�ѡ���б�
     * @return TList
     */
    public TList getSelectModel() {
        return selectModel;
    }

    /**
     * �����Ƿ������
     * @param isStartPoint boolean
     */
    public void setStartPoint(boolean isStartPoint) {
        this.isStartPoint = isStartPoint;
    }

    /**
     * �Ƿ������
     * @return boolean
     */
    public boolean isStartPoint() {
        return isStartPoint;
    }

    /**
     * �����Ƿ��ǽ�����
     * @param isStopPoint boolean
     */
    public void setStopPoint(boolean isStopPoint) {
        this.isStopPoint = isStopPoint;
    }

    /**
     * �Ƿ��ǽ�����
     * @return boolean
     */
    public boolean isStopPoint() {
        return isStopPoint;
    }

    /**
     * �ƶ�ѡ��λ��
     * @param position int
     */
    public void move(int position) {
        setStart(getStart() + position);
        setEnd(getEnd() + position);
    }

    /**
     * ר��ִ�ж���
     * Ϊ��������̳еĹ̶��ı���������ж�����֧
     * @param action EAction
     * @return boolean
     */
    public boolean specialExeAction(EAction action) {
        //System.out.println("======action type========"+action.getType());
        switch (action.getType()) {
        case EAction.DELETE: //ɾ������
            //�������������ɾ�����ض�����(ɾ����չ�ӿ�)
            if (action.getInt(0) != 0 &&
                getText().onDeleteActionIO(getStart(), getEnd())) {
                //ɾ��Ԫ������
                getText().getSelectedModel().removeThisComponent(this);
                //ɾ��Ԫ������

               //���ѡ��
                removeThis();
                return true;
            }
            return false;
        case EAction.COPY:
            CopyOperator copyOperator = action.getCopyOperator(0);
            //$$========added by lx 2011-09-06 ����ؼ����ƹ���START============$$//
             copyComponent(getText(),copyOperator);
            //$$========added by lx 2011-09-06 ����ؼ����ƹ���END============$$//
            //�ַ�������
            String s = getText().getString(getText().getStart() + getStart(),
                                           getText().getStart() + getEnd());
            //System.out.println("==�ַ�������s=="+s);

            copyOperator.addString(s);
            return true;
        }

        return false;
    }

    /**
     * ִ�ж���
     * @param action EAction
     */
    public void exeAction(EAction action) {
        //System.out.println("========action=========="+action.getType());
        if (action == null) {
            return;
        }
        //�����ר�ö�����������������
        if (specialExeAction(action)) {
            return;
        }
        //���ѡ����������ȫ������
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
            //�����ѡ�е���ʼ���ж�ǰ�������
            if (isStartPoint()) {
                getText().clearPreviousLink();
            }
            //�����ѡ�еĽ������жϺ��������
            if (isStopPoint()) {
                getText().clearNextLink();
            }
            return;
        }
        //�ָ�ǰ��
        if (getStart() > 0) {
            getText().separateText(getStart(), isStartPoint());
        }
        //�ָ��
        if (getEnd() < getText().getLength()) {
            getText().separateText(getEnd(), isStopPoint());
        }
        //����ִ��
        exeAction(action);
    }

    /**
     * �����ؼ�
     * @param text EText
     */
    private void copyComponent(EText text,CopyOperator copyOperator) {
        //�ж������ĸ����ͣ��ٽ������л�����
        if (getText().getClass() != null) {
            String thisClass=getText().getClass().toString();
            String thisValue= getText().getBlockValue();
            //System.out.println("=======CSelectTextBlock thisClass==========="+thisClass);
            //System.out.println("=======CSelectTextBlock thisValue==========="+thisValue);
            boolean isCopy = true;
            //���Ԫ���Զ����ж��Ԫ����bug.
            if (copyOperator.getTempTextClass() != null) {
                //������ֵ����ͬ˵�����صĲ�����;
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
