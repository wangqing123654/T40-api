package com.dongyang.tui.text;

import java.util.Vector;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.DPoint;
import com.dongyang.tui.io.DataInputStream;
import java.io.IOException;
import com.dongyang.util.ImageTool;
import java.awt.Color;
import java.awt.Dimension;
import com.dongyang.ui.TWindow;
import java.awt.Graphics;

/**
 * <p>Title: 关联元件类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class EAssociateChoose extends EFixed {
    public static final String SUB_TEMPLATE_PATH = "JHW/片语";
    /**
     * 关联开始标记
     */
    private String startTag = "";

    /**
     * 关联结束标记
     */
    private String endTag = "";


    /**
     * 数据
     */
    private Vector data = new Vector();

    public EAssociateChoose(EPanel panel) {
        super(panel);
    }

    /**
     * 设置文本
     * @param text String
     */
    public void setText(String text) {
        super.setText("{" + text + "}");
    }

    /**
     * 得到文本
     * @return String
     */
    public String getText() {
        String text = super.getText();
        if (text.length() < 2) {
            return "";
        }
        return text.substring(1, text.length() - 1);
    }

    /**
     * 设置数据
     * @param data Vector
     */
    public void setData(Vector data) {
        if (data == null) {
            data = new Vector();
        }
        this.data = data;
    }

    /**
     * 得到数据
     * @return Vector
     */
    public Vector getData() {
        return data;
    }

    /**
     * 数据个数
     * @return int
     */
    public int sizeData() {
        return data.size();
    }

    /**
     * 增加数据
     * @param name String
     * @param value String
     * @param mainId String       模版主分类
     * @param templateId String   子模版唯一文件名
     */
    public void addData(String name, String value, String mainId,
                        String templateId) {
        Vector v = new Vector();
        v.add(name);
        v.add(value);

        v.add(mainId);
        v.add(templateId);

        getData().add(v);
    }

    /**
     * 插入数据
     * @param index int 位置
     * @param name String
     * @param value String
     * @param mainId String
     * @param templateId String
     */
    public void addData(int index, String name, String value, String mainId,
                        String templateId) {
        if (name == null) {
            name = "";
        }
        Vector v = new Vector();
        v.add(name);
        v.add(value);

        v.add(mainId);
        v.add(templateId);

        getData().add(index, v);
    }

    /**
     * 删除数据
     * @param index int
     */
    public void removeData(int index) {
        getData().remove(index);
    }

    /**
     * 得到数据
     * @param index int
     * @return Vector
     */
    public Vector getData(int index) {
        return (Vector) getData().get(index);
    }

    /**
     * 得到数据名称
     * @param index int
     * @return String
     */
    public String getDataName(int index) {
        Vector v = getData(index);
        if (v == null || v.size() == 0) {
            return null;
        }
        return (String) v.get(0);
    }

    /**
     * 得到数据值
     * @param index int
     * @return String
     */
    public String getDataValue(int index) {
        Vector v = getData(index);
        if (v == null || v.size() < 2) {
            return null;
        }
        return (String) v.get(1);
    }

    /**
     * 删除数据
     * @param name String
     */
    public void removeValue(String name) {
        if (name == null) {
            return;
        }
        for (int i = sizeData() - 1; i >= 0; i--) {
            if (name.equals(getDataName(i))) {
                removeData(i);
            }
        }
    }

    /**
     * 得到新对象
     * @param index int
     * @return EFixed
     */
    public EFixed getNewObject(int index) {
        //?????需要修改;
        return getPanel().newAssociateChoose(index);
    }

    /**
     * 得到坐标位置
     * @return String
     */
    public String getIndexString() {
        EPanel panel = getPanel();
        if (panel == null) {
            return "Parent:Null";
        }
        return panel.getIndexString() + ",EAssociateChoose:" + findIndex();
    }

    public String toString() {
        return "<EAssociateChoose name=" + getName() + ",start=" + getStart() +
                ",end=" + getEnd() + ",s=" + getString() + ",position=" +
                getPosition() + ",index=" + getIndexString() +
                ",p=" +
                (getPreviousFixed() != null ?
                 "[" + getPreviousFixed().getIndexString() + "]" : "null") +
                ",n=" +
                (getNextFixed() != null ?
                 "[" + getNextFixed().getIndexString() + "]" : "null") + ">";
    }

    /**
     * 绘制固定文本焦点背景
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintFixedFocusBack(Graphics g, int x, int y, int width,
                                    int height) {
        super.paintFixedFocusBack(g, x, y, width, height);
        if (getNextFixed() == null) {
            paintComboFlg(g, x + width - 2, y + height);
        }
    }

    /**
     * 绘制下拉焦点
     * @param g Graphics
     * @param x int
     * @param y int
     */
    public void paintComboFlg(Graphics g, int x, int y) {
        g.setColor(new Color(255, 0, 0));
        g.drawLine(x - 2, y, x + 2, y);
        g.drawLine(x - 1, y + 1, x + 1, y + 1);
        g.drawLine(x - 1, y + 2, x + 1, y + 2);
        g.drawLine(x, y + 3, x, y + 3);
    }

    /**
     * 双击
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX, int mouseY) {
        openVauePopupMenu();
        return true;
    }

    /**
     * 值选择对话框
     */
    public void openVauePopupMenu() {
        if (getKeyIndex() != getModifyNodeIndex()) {
            return;
        }
        DPoint point = getScreenPoint();
        int h = (int) (getHeight() * getZoom() / 75.0 + 0.5) + 3;
        Dimension dimension = ImageTool.getScreenSize();
        //修改
        TWindow propertyWindow = (TWindow) getPM().getUI().openWindow(
                "%ROOT%\\config\\database\\AssociatePopMenu.x", getHeadFixed(), true);
        int pw = propertyWindow.getWidth();
        int ph = propertyWindow.getHeight();
        int x = point.getX();
        if (x < 0) {
            x = 0;
        }
        if (x + pw > dimension.getWidth()) {
            x = (int) dimension.getWidth() - pw;
        }
        int y = point.getY();
        if (y + h + ph > dimension.getHeight()) {
            y = y - ph - 3;
        } else {
            y = y + h;
        }
        if (y < 0) {
            y = 0;
        }
        propertyWindow.setX(x);
        propertyWindow.setY(y);
        propertyWindow.setVisible(true);
        getPM().getEventManager().setPropertyMenuWindow(propertyWindow);
    }

    /**
     * 属性对话框
     */
    public void openPropertyDialog() {
        EFixed fixed = getHeadFixed();
        //修改对话框;
        String value = (String) getPM().getUI().openDialog(
                "%ROOT%\\config\\database\\AssociateChooseEdit.x", fixed);
        if (value == null || !"OK".equals(value)) {
            return;
        }
        fixed.getPM().getFileManager().update();
    }

    /**
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s) throws IOException {
        super.writeObjectAttribute(s);
        s.writeShort(100);
        s.writeInt(sizeData());
        for (int i = 0; i < sizeData(); i++) {
            Vector data = (Vector) getData().get(i);
            s.writeInt(data.size());
            for (int j = 0; j < data.size(); j++) {
                s.writeString((String) data.get(j));
            }
        }

        s.writeString(101, getStartTag(), "");
        s.writeString(102, getEndTag(), "");
    }

    /**
     * 读对象属性
     * @param id int
     * @param s DataInputStream
     * @return boolean
     * @throws IOException
     */
    public boolean readObjectAttribute(int id, DataInputStream s) throws
            IOException {
        if (super.readObjectAttribute(id, s)) {
            return true;
        }
        switch (id) {
        case 100:
            int count = s.readInt();
            for (int i = 0; i < count; i++) {
                Vector v = new Vector();
                int c = s.readInt();
                for (int j = 0; j < c; j++) {
                    v.add(s.readString());
                }
                getData().add(v);
            }
            return true;
        case 101:
            setStartTag(s.readString());
            return true;
        case 102:
            setEndTag(s.readString());
            return true;
        }
        return false;
    }

    /**
     * 得到对象类型
     * @return int
     */
    public int getObjectType() {
        return ASSOCIATE_CHOOSE_TYPE;
    }

    /**
     * 刷新数据
     * @param action EAction
     */
    public void resetData(EAction action) {
        if (action.getType() == EAction.PREVIEW_STATE ||
            action.getType() == EAction.EDIT_STATE) {
            setModify(true);
            return;
        }
    }

    /**
     * 得到显示字串
     * @return String
     */
    public String getShowString() {
        if (!isPreview()) {
            return super.getShowString();
        }
        String s = getString();
        if (s.length() == 0) {
            return "";
        }
        if (getPreviousFixed() == null) {
            s = s.substring(1);
        }
        if (getNextFixed() == null) {
        	try{
            s = s.substring(0, s.length() - 1);
        	}catch(Exception e){
        		s="";
        	}
        }
        return s;
    }

    /**
     * 得到数据行数
     * @return int
     */
    public int getDataSize() {
        return getData().size();
    }

    /**
     * 查找选中行号
     * @param text String
     * @return int
     */
    private int getSelectRow(String text) {
        int count = getDataSize();
        for (int i = 0; i < count; i++) {
            String value = getDataName(i);
            if (value != null && value.equals(text)) {
                return i;
            }
        }
        return -1;
    }


    /**
     * 向上移动光标
     * @param x int
     */
    public void onFocusToUp(int x) {
        if (getDataSize() == 0) {
            return;
        }
        if (getKeyIndex() != getModifyNodeIndex()) {
            return;
        }

        int row = getSelectRow(getText());
        if (row == -1) {
            row = 0;
        } else {
            row--;
            if (row == -1) {
                row = getDataSize() - 1;
            }
        }
        setText(getDataName(row));
        //更新关联范围值;
        onSelect(row);
        update();
    }

    /**
     * 向下移动光标
     * @param x int
     */
    public void onFocusToDown(int x) {
        if (getDataSize() == 0) {
            return;
        }
        if (getKeyIndex() != getModifyNodeIndex()) {
            return;
        }
        int row = getSelectRow(getText());
        if (row == -1) {
            row = 0;
        } else {
            row++;
            if (row >= getDataSize()) {
                row = 0;
            }
        }
        setText(getDataName(row));
        //更新关联范围值;
        onSelect(row);
        update();
    }

    /**
     * 选中，更新范围
     */
    private void onSelect(int row) {
        String startTag = getStartTag();
        String endTag = getEndTag();
        if (row != -1) {
            Vector d = getData();
            //取关联的子模版
            String fileName = (String) (((Vector) d.get(row))).get(3);
            if (fileName!=null&&!fileName.equals("")) {
                //删除对范围的内容；
                clear();
                //插入子模版内容
                EFixed fiexed = (EFixed) getFocusManager().findObject(startTag,
                        EComponent.FIXED_TYPE);
                fiexed.setFocusLast();
                boolean b = fiexed.getPM().getFocusManager().onInsertFile(
                        SUB_TEMPLATE_PATH, fileName, 2, false);
                //删除一行子模版开始的回车
                fiexed.setFocusLast();
                boolean b1 = fiexed.deleteChar();
                //删除一行结束符子模版结束的回车
                EFixed fiexedEnd = (EFixed) getFocusManager().findObject(endTag,
                        EComponent.FIXED_TYPE);
                fiexedEnd.setFocus();
                fiexedEnd.backspaceChar();
            } else {
                //删除范围内的内容；
                clear();
            }
        }
        //
        EAssociateChoose eassociateChoose = (EAssociateChoose) getFocusManager().findObject(getName(),
                        EComponent.ASSOCIATE_CHOOSE_TYPE);
        eassociateChoose.setFocus(1);
    }
    /**
     * 清除设置范围之间的全部对象
     */
    public void clear()
    {
        //取结束范围对象
        EFixed endFixed = (EFixed) (getFocusManager().
                            findObject(getEndTag(), EComponent.FIXED_TYPE));
        if(endFixed == null)
            return;
        EFixed startFixed = (EFixed)getFocusManager().
                              findObject(startTag, EComponent.FIXED_TYPE);
        startFixed.setFocusLast();
        getFocusManager().setStartSelected();
        endFixed.setFocus();
        getFocusManager().setEndSelected();
        getFocusManager().delete(0);
        setFocusLast();

    }
    /**
     * 更新
     */
    public void update() {
        getFocusManager().update();
    }

    /**
     * 是否是Tab焦点
     * @return boolean
     */
    public boolean isTabFocus() {
        return true;
    }

    /**
     * 设置Tab焦点
     */
    public void setTabFocus() {
        setFocus(1);
    }

    /**
     * 克隆
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel) {
        EAssociateChoose associateChoose = new EAssociateChoose(panel);
        associateChoose.setStart(panel.getDString().size());
        associateChoose.setEnd(getStart());
        associateChoose.setString(getString());
        associateChoose.setKeyIndex(getKeyIndex());
        associateChoose.setDeleteFlg(isDeleteFlg());
        associateChoose.setStyleOther(getStyle());
        associateChoose.setName(getName());
        associateChoose.setStartTag(getStartTag());
        associateChoose.setEndTag(getEndTag());
        associateChoose.setGroupName(getGroupName());
        associateChoose.setDataElements(isIsDataElements());
        associateChoose.setAllowNull(isAllowNull());
        for (int i = 0; i < sizeData(); i++) {
            Vector d = (Vector) getData().get(i);
            associateChoose.addData((String) d.get(0), (String) d.get(1),
                                    (String) d.get(2), (String) d.get(3));
        }
        return associateChoose;
    }


    /**
     * 设置联动开始元素名
     * @param groupName String
     */
    public void setStartTag(String startTag) {
        this.startTag = startTag;
    }

    /**
     * 得到联动开始元素名
     * @return String
     */
    public String getStartTag() {
        return startTag;
    }

    /**
     * 设置联动结束元素名
     * @param groupName String
     */
    public void setEndTag(String endTag) {
        this.endTag = endTag;
    }

    /**
     * 得到联动结束元素名
     * @return String
     */
    public String getEndTag() {
        return endTag;
    }


}
