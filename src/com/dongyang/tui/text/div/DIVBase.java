package com.dongyang.tui.text.div;

import com.dongyang.ui.TWindow;
import com.dongyang.tui.text.MPage;
import com.dongyang.tui.DText;
import com.dongyang.tui.text.PM;
import java.awt.Graphics;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.control.TControl;
import com.dongyang.tui.text.MView;
/**
 *
 * <p>Title: DIV����</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.5.22
 * @version 1.0
 */
public class DIVBase implements DIV
{
    /**
     * ����
     */
    private MV parent;
    /**
     * ��ʾ
     */
    private boolean visible = true;
    /**
     * ����
     */
    private String name = "<none>";
    /**
     * ���Դ���
     */
    private TWindow propertyWindow;
    /**
     * ���ø���
     * @param parent MV
     */
    public void setParent(MV parent)
    {
        this.parent = parent;
    }
    /**
     * �õ�����
     * @return MV
     */
    public MV getParent()
    {
        return parent;
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        if(getParent() == null)
            return null;
        return getParent().getPM();
    }
    /**
     * �õ�UI
     * @return DText
     */
    public DText getUI()
    {
        PM pm = getPM();
        if(pm == null)
            return null;
        return pm.getUI();
    }
    /**
     * �õ�ҳ�������
     * @return MPage
     */
    public MPage getPageManager()
    {
        PM pm = getPM();
        if(pm == null)
            return null;
        return pm.getPageManager();
    }
    /**
     * �õ���ʾ������
     * @return MView
     */
    public MView getViewManager()
    {
        return getPM().getViewManager();
    }
    /**
     * �õ����ű���
     * @return double
     */
    public double getZoom()
    {
        return getViewManager().getZoom();
    }
    /**
     * ����
     */
    public void update()
    {
        MPage mPage = getPageManager();
        if(mPage == null)
            return;
        mPage.update();
    }
    /**
     * �����Ƿ���ʾ
     * @param visible boolean
     */
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    /**
     * �Ƿ���ʾ
     * @return boolean
     */
    public boolean isVisible()
    {
        return visible;
    }
    /**
     * ��������
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * �������Դ���
     * @param propertyWindow TWindow
     */
    public void setPropertyWindow(TWindow propertyWindow)
    {
        this.propertyWindow = propertyWindow;
    }
    /**
     * �õ����Դ���
     * @return TWindow
     */
    public TWindow getPropertyWindow()
    {
        return propertyWindow;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return "DIV";
    }
    /**
     * �õ�����
     * @return int
     */
    public int getTypeID()
    {
        return 0;
    }
    /**
     * ����
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
    }
    /* ����
     * @param g Graphics
     */
    public void print(Graphics g)
    {
    }
    /**
     * �õ����ԶԻ�������
     * @return String
     */
    public String getPropertyDialogName()
    {
        return "";
    }
    /**
     * �����ԶԻ���
     */
    public void openProperty()
    {
        String name = getPropertyDialogName();
        if(name == null || name.length() == 0)
            return;
        if(propertyWindow == null || propertyWindow.isClose())
        {
            propertyWindow = (TWindow) getUI().openWindow(name, this, true);
            propertyWindow.setVisible(true);
        }
        else
            propertyWindow.setVisible(true);
    }
    /**
     * �޸Ĳ���
     * @param name String
     */
    public void modify(String name)
    {
        getParent().modify(this,name);
    }
    /**
     * ���ԶԻ����޸Ĳ���
     * @param name String
     */
    public void propertyModify(String name)
    {
        if(getPropertyWindow() == null || getPropertyWindow().isClose())
            return;
        TControl control = getPropertyWindow().getControl();
        if(control == null)
            return;
        control.callFunction("modify",name);
    }
    /**
     * �ͷ�
     */
    public void DC()
    {
        if(propertyWindow == null || propertyWindow.isClose())
            return;
        propertyWindow.onClosed();
    }
    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
    }
    /**
     * ������
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
    }
    /**
     * �����Ƿ��Ǻ�ģ��
     * @return boolean
     */
    public boolean isMacroroutineModel()
    {
        if(getParent() == null)
            return false;
        return getParent().isMacroroutineModel();
    }
    /**
     * �õ�����
     * @param column String
     * @return Object
     */
    public Object getMacroroutineData(String column)
    {
        return getParent().getMacroroutineData(column);
    }
    /**
     * �õ�ҳ��
     * @return int
     */
    public int getPageIndex()
    {
        return getParent().getPageIndex();
    }
}
