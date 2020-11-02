package com.dongyang.tui.text;

import java.util.Vector;
import java.awt.Graphics;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.DPoint;
import com.dongyang.tui.io.DataInputStream;
import java.io.IOException;
import com.dongyang.util.ImageTool;
import java.awt.Color;
import java.awt.Dimension;
import com.dongyang.ui.TWindow;

/**
 *
 * <p>Title: ��ѡ</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.8.27
 * @version 1.0
 */
public class EMultiChoose extends EFixed
{
    /**
     * ����
     */
    private Vector data = new Vector();
    /**
     * ������
     * @param panel EPanel
     */
    public EMultiChoose(EPanel panel)
    {
        super(panel);
    }
    /**
     * �����ı�
     * @param text String
     */
    public void setText(String text)
    {
        super.setText("{" + text + "}");
    }
    /**
     * �õ��ı�
     * @return String
     */
    public String getText()
    {
        String text = super.getText();
        if(text.length() < 2)
            return "";
        return text.substring(1,text.length() - 1);
    }
    /**
     * ��������
     * @param data Vector
     */
    public void setData(Vector data)
    {
        if(data == null)
            data = new Vector();
        this.data = data;
    }
    /**
     * �õ�����
     * @return Vector
     */
    public Vector getData()
    {
        return data;
    }
    /**
     * ���ݸ���
     * @return int
     */
    public int sizeData()
    {
        return data.size();
    }
    /**
     * ��������
     * @param name String
     * @param value String
     */
    public void addData(String name,String value)
    {
        Vector v = new Vector();
        v.add(name);
        v.add(value);
        getData().add(v);
    }
    /**
     * ��������
     * @param index int λ��
     * @param name String
     * @param value String
     */
    public void addData(int index,String name,String value)
    {
        if(name == null)
            name = "";
        Vector v = new Vector();
        v.add(name);
        v.add(value);
        getData().add(index,v);
    }
    /**
     * ɾ������
     * @param index int
     */
    public void removeData(int index)
    {
        getData().remove(index);
    }
    /**
     * �õ�����
     * @param index int
     * @return Vector
     */
    public Vector getData(int index)
    {
        return (Vector)getData().get(index);
    }
    /**
     * �õ���������
     * @param index int
     * @return String
     */
    public String getDataName(int index)
    {
        Vector v = getData(index);
        if(v == null || v.size() == 0)
            return null;
        return (String)v.get(0);
    }
    /**
     * �õ�����ֵ
     * @param index int
     * @return String
     */
    public String getDataValue(int index)
    {
        Vector v = getData(index);
        if(v == null || v.size() < 2)
            return null;
        return (String)v.get(1);
    }
    /**
     * ɾ������
     * @param name String
     */
    public void removeValue(String name)
    {
        if(name == null)
            return;
        for(int i = sizeData() - 1;i >= 0;i --)
            if(name.equals(getDataName(i)))
                removeData(i);
    }
    /**
     * �õ��¶���
     * @param index int
     * @return EFixed
     */
    public EFixed getNewObject(int index)
    {
        return getPanel().newMultiChoose(index);
    }
    /**
     * �õ�����λ��
     * @return String
     */
    public String getIndexString()
    {
        EPanel panel = getPanel();
        if(panel == null)
            return "Parent:Null";
        return panel.getIndexString() + ",EMultiChoose:" + findIndex();
    }
    public String toString()
    {
        return "<EMultiChoose name=" + getName() + ",start=" + getStart() + ",end=" + getEnd() + ",s=" + getString() + ",position=" + getPosition() + ",index=" + getIndexString() +
                ",p=" + (getPreviousFixed() != null?"[" + getPreviousFixed().getIndexString() + "]":"null") +
                ",n=" + (getNextFixed() != null?"[" + getNextFixed().getIndexString() + "]":"null") + ">";
    }
    /**
     * ���ƹ̶��ı����㱳��
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintFixedFocusBack(Graphics g,int x,int y,int width,int height)
    {
        super.paintFixedFocusBack(g,x,y,width,height);
        if(getNextFixed() == null)
            paintComboFlg(g,x + width - 2,y + height);
    }
    /**
     * ������������
     * @param g Graphics
     * @param x int
     * @param y int
     */
    public void paintComboFlg(Graphics g,int x,int y)
    {
        g.setColor(new Color(255,0,0));
        g.drawLine(x - 2,y,x + 2,y);
        g.drawLine(x - 1,y + 1,x + 1,y + 1);
        g.drawLine(x - 1,y + 2,x + 1,y + 2);
        g.drawLine(x,y + 3,x,y + 3);
    }
    /**
     * ˫��
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        openVauePopupMenu();
        return true;
    }
    /**
     * ֵѡ��Ի���
     */
    public void openVauePopupMenu()
    {
        if(getKeyIndex() != getModifyNodeIndex())
            return;
        DPoint point = getScreenPoint();
        int h = (int)(getHeight() * getZoom() / 75.0 + 0.5) + 3;
        Dimension dimension = ImageTool.getScreenSize();
        TWindow propertyWindow = (TWindow)getPM().getUI().openWindow("%ROOT%\\config\\database\\MultiChoosePopMenu.x", getHeadFixed() ,true);
        int pw = propertyWindow.getWidth();
        int ph = propertyWindow.getHeight();
        int x = point.getX();
        if(x < 0)
            x = 0;
        if(x + pw > dimension.getWidth())
            x = (int)dimension.getWidth() - pw;
        int y = point.getY();
        if(y + h + ph > dimension.getHeight())
            y = y - ph - 3;
        else
            y = y + h;
        if(y < 0)
            y = 0;
        propertyWindow.setX(x);
        propertyWindow.setY(y);
        propertyWindow.setVisible(true);
        getPM().getEventManager().setPropertyMenuWindow(propertyWindow);
    }
    /**
     * ���ԶԻ���
     */
    public void openPropertyDialog()
    {
        EFixed fixed = getHeadFixed();
        String value = (String)getPM().getUI().openDialog("%ROOT%\\config\\database\\MultiChooseEdit.x",fixed);
        if(value == null || !"OK".equals(value))
            return;
        fixed.getPM().getFileManager().update();
    }
    /**
     * д��������
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        super.writeObjectAttribute(s);
        s.writeShort(100);
        s.writeInt(sizeData());
        for(int i = 0;i < sizeData();i++)
        {
            Vector data = (Vector)getData().get(i);
            s.writeInt(data.size());
            for(int j = 0;j < data.size();j++)
                s.writeString((String)data.get(j));
        }
    }
    /**
     * ����������
     * @param id int
     * @param s DataInputStream
     * @return boolean
     * @throws IOException
     */
    public boolean readObjectAttribute(int id,DataInputStream s)
            throws IOException
    {
        if (super.readObjectAttribute(id, s))
            return true;
        switch (id)
        {
        case 100:
            int count = s.readInt();
            for(int i = 0;i < count;i++)
            {
                Vector v = new Vector();
                int c = s.readInt();
                for(int j = 0;j < c;j++)
                {
                    v.add(s.readString());
                }
                getData().add(v);
            }
            return true;
        }
        return false;
    }
    /**
     * �õ���������
     * @return int
     */
    public int getObjectType()
    {
        return MULTI_CHOOSE_TYPE;
    }
    /**
     * ˢ������
     * @param action EAction
     */
    public void resetData(EAction action)
    {
        if(action.getType() == EAction.PREVIEW_STATE ||
           action.getType() == EAction.EDIT_STATE)
        {
            setModify(true);
            return;
        }
    }
    /**
     * �õ���ʾ�ִ�
     * @return String
     */
    public String getShowString()
    {
        if(!isPreview())
            return super.getShowString();
        String s = getString();
        //System.out.println("s===="+s);
        if(s.length() == 0)
            return "";
        //���Ԥ���д������
        if((s.equals("{")||s.equals("}"))&&s.length() == 1){
                return "";
        }
        //
        if(getPreviousFixed() == null)
            s = s.substring(1);
        if(getNextFixed() == null){
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
    /**
     * ����
     */
    public void update()
    {
        getFocusManager().update();
    }
    /**
     * �Ƿ���Tab����
     * @return boolean
     */
    public boolean isTabFocus()
    {
        return true;
    }
    /**
     * ����Tab����
     */
    public void setTabFocus()
    {
        setFocus(1);
    }
    /**
     * ��¡
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel)
    {
        EMultiChoose multiChoose = new EMultiChoose(panel);
        multiChoose.setStart(panel.getDString().size());
        multiChoose.setEnd(getStart());
        multiChoose.setString(getString());
        multiChoose.setKeyIndex(getKeyIndex());
        multiChoose.setDeleteFlg(isDeleteFlg());
        multiChoose.setStyleOther(getStyle());
        multiChoose.setName(getName());
        multiChoose.setGroupName(getGroupName());
        multiChoose.setMicroName(getMicroName());
        multiChoose.setDataElements(isIsDataElements());
        multiChoose.setAllowNull(isAllowNull());

        for(int i = 0;i < sizeData();i++)
        {
            Vector d = (Vector)getData().get(i);
            multiChoose.addData((String)d.get(0),(String)d.get(1));
        }
        return multiChoose;
    }
}
