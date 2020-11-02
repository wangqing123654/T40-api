package com.dongyang.tui.text;

import com.dongyang.util.TList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * <p>Title: 文本对象选蓝处理对象</p>
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
     * 文本对象
     */
    private EText text;
    /**
     * 选择块列表
     */
    private TList selectList;

    private List selectComList = new ArrayList();
    /**
     * 构造器
     * @param text EText
     */
    public CSelectedTextModel(EText text) {
        setText(text);
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
     * 是否选蓝
     * @return boolean
     */
    public boolean isSelected() {
        return size() > 0;
    }

    /**
     * 设置选择块列表
     * @param list TList
     */
    public void setSelectList(TList list) {
        this.selectList = list;
    }

    /**
     * 得到选择块列表
     * @return TList
     */
    public TList getSelectList() {
        return selectList;
    }

    /**
     * 创建选择块
     */
    public void createSelectList() {
        if (getSelectList() == null) {
            setSelectList(new TList());
        }
    }

    /**
     * 清空选择块
     */
    public void removeAll() {
        setSelectList(null);
    }

    /**
     * 删除选择块
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
     * 删除选择块
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
     * 插入选择块
     * @param block CSelectTextBlock
     */
    public void insert(CSelectTextBlock block) {
        if (getSelectList() == null) {
            createSelectList();
        }
        getSelectList().add(0, block);
        //处理选蓝合并
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
     * 追加选择块
     * @param block CSelectTextBlock
     */
    public void append(CSelectTextBlock block) {
        if (getSelectList() == null) {
            createSelectList();
        }
        getSelectList().add(block);
        //处理选蓝合并
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
     * 查找选蓝首部是指定坐标的对象
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
     * 查找选蓝尾部是指定坐标的对象
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
     * 增加选择块
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
        //设置选蓝从属的选蓝列表
        block.setSelectModel(getText().getFocusManager().getSelectedModel().
                             getSelectedList());
        if (getSelectList() != null) {
            //如果本次添加的选蓝和以前的有交集
            //删除有交集的选蓝
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
     * 得到存在交集的选择块
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
     * 得到选择块个数
     * @return int
     */
    public int size() {
        if (getSelectList() == null) {
            return 0;
        }
        return getSelectList().size();
    }

    /**
     * 得到一个选择块
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
     * 字符是否选中
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
     * 移动选蓝位置
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
     * 被分割处理选蓝
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
                //分割选蓝对象
                CSelectTextBlock block = new CSelectTextBlock(text, 0,
                        selectBlock.getEnd() - position);
                block.setSelectModel(selectBlock.getSelectModel());
                selectBlock.setEnd(position);
                //传递结束点
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
     * 移动文本组件开始字母到指定的文本组件中
     * 选蓝跟随调整
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
            //分割选蓝对象
            CSelectTextBlock block = new CSelectTextBlock(text,
                    text.getLength() - size + selectBlock.getStart(),
                    text.getLength());
            block.setSelectModel(selectBlock.getSelectModel());
            selectBlock.setStart(0);
            selectBlock.setEnd(selectBlock.getEnd() - size);
            //传递起始点
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
     * 清除
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
     * 删除元件
     * @param eText EText
     */
    public void removeThisComponent(CSelectTextBlock block) {
        EText eText = block.getText();
        if (eText == null) {
            return;
        }
        //抓取元件不能删除;
        //抓取框;
        if (eText instanceof ECapture) {
            if (((ECapture) eText).isLocked()) {
                return;
            }
        }

        //锁定元件不能删除;
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
        //更新
        eText.getFocusManager().update();
    }


}
