package com.dongyang.tui.text;

import com.dongyang.util.TList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * <p>Title: �ı�����ѡ���������</p>
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
public class CSelectedTextModel {
    /**
     * �ı�����
     */
    private EText text;
    /**
     * ѡ����б�
     */
    private TList selectList;

    private List selectComList = new ArrayList();
    /**
     * ������
     * @param text EText
     */
    public CSelectedTextModel(EText text) {
        setText(text);
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
     * �Ƿ�ѡ��
     * @return boolean
     */
    public boolean isSelected() {
        return size() > 0;
    }

    /**
     * ����ѡ����б�
     * @param list TList
     */
    public void setSelectList(TList list) {
        this.selectList = list;
    }

    /**
     * �õ�ѡ����б�
     * @return TList
     */
    public TList getSelectList() {
        return selectList;
    }

    /**
     * ����ѡ���
     */
    public void createSelectList() {
        if (getSelectList() == null) {
            setSelectList(new TList());
        }
    }

    /**
     * ���ѡ���
     */
    public void removeAll() {
        setSelectList(null);
    }

    /**
     * ɾ��ѡ���
     * @param index int
     */
    public void remove(int index) {
        if (getSelectList() == null) {
            return;
        }
        getSelectList().remove(index);
        if (size() == 0) {
            removeAll();
        }
    }

    /**
     * ɾ��ѡ���
     * @param block CSelectTextBlock
     */
    public void remove(CSelectTextBlock block) {
        /**System.out.println(
         "===========remove(CSelectTextBlock block)=================");**/
        if (getSelectList() == null) {
            return;
        }
        getSelectList().remove(block);
        if (size() == 0) {
            removeAll();
        }
    }

    /**
     * ����ѡ���
     * @param block CSelectTextBlock
     */
    public void insert(CSelectTextBlock block) {
        if (getSelectList() == null) {
            createSelectList();
        }
        getSelectList().add(0, block);
        //����ѡ���ϲ�
        CSelectTextBlock b = getStartSelectedBlock(block.getEnd());
        if (b == null) {
            return;
        }
        if (b.getSelectModel() != block.getSelectModel()) {
            return;
        }
        block.setEnd(b.getEnd());
        block.getSelectModel().remove(b);
        getSelectList().remove(b);
    }

    /**
     * ׷��ѡ���
     * @param block CSelectTextBlock
     */
    public void append(CSelectTextBlock block) {
        if (getSelectList() == null) {
            createSelectList();
        }
        getSelectList().add(block);
        //����ѡ���ϲ�
        CSelectTextBlock b = getEndSelectedBlock(block.getStart());
        if (b == null) {
            return;
        }
        if (b.getSelectModel() != block.getSelectModel()) {
            return;
        }
        b.setEnd(block.getEnd());
        b.getSelectModel().remove(block);
        getSelectList().remove(block);
    }

    /**
     * ����ѡ���ײ���ָ������Ķ���
     * @param start int
     * @return CSelectTextBlock
     */
    private CSelectTextBlock getStartSelectedBlock(int start) {
        for (int i = 0; i < size(); i++) {
            CSelectTextBlock block = get(i);
            if (block.getStart() == start) {
                return block;
            }
        }
        return null;
    }

    /**
     * ����ѡ��β����ָ������Ķ���
     * @param end int
     * @return CSelectTextBlock
     */
    private CSelectTextBlock getEndSelectedBlock(int end) {
        for (int i = 0; i < size(); i++) {
            CSelectTextBlock block = get(i);
            if (block.getEnd() == end) {
                return block;
            }
        }
        return null;
    }

    /**
     * ����ѡ���
     * @param start int
     * @param end int
     * @return CSelectTextBlock
     */
    public CSelectTextBlock add(int start, int end) {
        if (start == end) {
            return null;
        }
        if (end < start) {
            int temp = start;
            start = end;
            end = temp;
        }
        CSelectTextBlock block = new CSelectTextBlock(getText(), start, end);
        //����ѡ��������ѡ���б�
        block.setSelectModel(getText().getFocusManager().getSelectedModel().
                             getSelectedList());
        if (getSelectList() != null) {
            //���������ӵ�ѡ������ǰ���н���
            //ɾ���н�����ѡ��
            CSelectTextBlock[] b = getIncludes(block);
            for (int i = 0; i < b.length; i++) {
                getText().getFocusManager().getSelectedModel().removeSelectList(
                        b[i].getSelectModel());
            }
        }
        if (getSelectList() == null) {
            createSelectList();
        }
        getSelectList().add(block);
        return block;
    }

    /**
     * �õ����ڽ�����ѡ���
     * @param block CSelectTextBlock
     * @return SelectBlock[]
     */
    public CSelectTextBlock[] getIncludes(CSelectTextBlock block) {
        if (block == null) {
            return null;
        }
        int count = size();
        if (count == 0) {
            return null;
        }
        ArrayList list = new ArrayList();
        for (int i = 0; i < count; i++) {
            CSelectTextBlock b = get(i);
            if (b.isIncludes(block)) {
                list.add(b);
            }
        }
        return (CSelectTextBlock[]) list.toArray(new CSelectTextBlock[] {});
    }

    /**
     * �õ�ѡ������
     * @return int
     */
    public int size() {
        if (getSelectList() == null) {
            return 0;
        }
        return getSelectList().size();
    }

    /**
     * �õ�һ��ѡ���
     * @param index int
     * @return CSelectTextBlock
     */
    public CSelectTextBlock get(int index) {
        if (index < 0) {
            return null;
        }
        TList list = getSelectList();
        if (list == null || list.size() <= index) {
            return null;
        }
        return (CSelectTextBlock) list.get(index);
    }

    /**
     * �ַ��Ƿ�ѡ��
     * @param index int
     * @return boolean
     */
    public boolean isSelectedChar(int index) {
        int count = size();
        if (count == 0) {
            return false;
        }
        for (int i = 0; i < count; i++) {
            CSelectTextBlock b = get(i);
            if (b.isInclude(index)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        int count = size();
        for (int i = 0; i < count; i++) {
            CSelectTextBlock b = get(i);
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(b);
        }
        return sb.toString();
    }

    /**
     * �ƶ�ѡ��λ��
     * @param position int
     */
    public void move(int position) {
        if (!isSelected()) {
            return;
        }
        for (int i = size() - 1; i >= 0; i--) {
            CSelectTextBlock selectBlock = get(i);
            selectBlock.move(position);
        }
    }

    /**
     * ���ָ��ѡ��
     * @param position int
     * @param text EText
     */
    public void separate(int position, EText text) {
        if (!isSelected()) {
            return;
        }
        for (int i = size() - 1; i >= 0; i--) {
            CSelectTextBlock selectBlock = get(i);
            if (selectBlock.getEnd() <= position) {
                continue;
            }
            if (selectBlock.getStart() >= position) {
                selectBlock.setStart(selectBlock.getStart() - position);
                selectBlock.setEnd(selectBlock.getEnd() - position);
                selectBlock.setText(text);
                remove(selectBlock);
                text.getSelectedModel().insert(selectBlock);
                continue;
            }
            if (selectBlock.getEnd() > position) {
                //�ָ�ѡ������
                CSelectTextBlock block = new CSelectTextBlock(text, 0,
                        selectBlock.getEnd() - position);
                block.setSelectModel(selectBlock.getSelectModel());
                selectBlock.setEnd(position);
                //���ݽ�����
                if (selectBlock.isStopPoint()) {
                    selectBlock.setStopPoint(false);
                    block.setStopPoint(true);
                }
                int index = selectBlock.getSelectModel().indexOf(selectBlock);
                if (index != -1) {
                    selectBlock.getSelectModel().add(index + 1, block);
                }
                text.getSelectedModel().insert(block);
                continue;
            }
        }
    }

    /**
     * �ƶ��ı������ʼ��ĸ��ָ�����ı������
     * ѡ���������
     * @param text EText
     * @param size int
     */
    public void move(EText text, int size) {
        if (!isSelected()) {
            return;
        }
        for (int i = size() - 1; i >= 0; i--) {
            CSelectTextBlock selectBlock = get(i);
            if (selectBlock.getStart() >= size) {
                selectBlock.setStart(selectBlock.getStart() - size);
                selectBlock.setEnd(selectBlock.getEnd() - size);
                continue;
            }
            if (selectBlock.getEnd() <= size) {
                remove(selectBlock);
                selectBlock.setText(text);
                selectBlock.setStart(text.getLength() - size +
                                     selectBlock.getStart());
                selectBlock.setEnd(text.getLength() - size + selectBlock.getEnd());
                text.getSelectedModel().append(selectBlock);
                continue;
            }
            //�ָ�ѡ������
            CSelectTextBlock block = new CSelectTextBlock(text,
                    text.getLength() - size + selectBlock.getStart(),
                    text.getLength());
            block.setSelectModel(selectBlock.getSelectModel());
            selectBlock.setStart(0);
            selectBlock.setEnd(selectBlock.getEnd() - size);
            //������ʼ��
            if (selectBlock.isStartPoint()) {
                selectBlock.setStartPoint(false);
                block.setStartPoint(true);
            }
            int index = selectBlock.getSelectModel().indexOf(selectBlock);
            if (index != -1) {
                selectBlock.getSelectModel().add(index, block);
            }
            text.getSelectedModel().append(block);
        }
    }

    /**
     * ���
     */
    public void clear() {
        for (int i = 0; i < size(); i++) {
            CSelectTextBlock block = get(i);
            if (block.getSelectModel() != null) {
                block.getSelectModel().remove(block);
            }
        }
        removeAll();
    }

    /**
     * ɾ��Ԫ��
     * @param eText EText
     */
    public void removeThisComponent(CSelectTextBlock block) {
        EText eText = block.getText();
        if (eText == null) {
            return;
        }
        //ץȡԪ������ɾ��;
        //ץȡ��;
        if (eText instanceof ECapture) {
            if (((ECapture) eText).isLocked()) {
                return;
            }
        }

        //����Ԫ������ɾ��;
        if (eText instanceof EFixed) {
            if (((EFixed) eText).isLocked()) {
                return;
            }
        }

        if (eText instanceof EFixed) {
            ((EFixed) eText).deleteFixed();
        } else if (eText instanceof EMacroroutine) {
            ((EMacroroutine) eText).delete();
        } else {
            return;
        }
        //����
        eText.getFocusManager().update();
    }


}
