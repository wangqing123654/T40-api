package com.dongyang.ui;

import com.dongyang.ui.base.TDateFieldBase;
import com.dongyang.ui.edit.TAttributeList.TAttribute;
import com.dongyang.ui.edit.TUIAdapter;
import com.dongyang.config.TConfigParse.Row;
import com.dongyang.util.StringTool;
import com.dongyang.config.TConfigParse.TObject;
import com.dongyang.ui.edit.TAttributeList;

public class TDateField extends TDateFieldBase
{
    /**
     * UI������
     */
    private TUIAdapter uiAdapter;
    /**
     * �Ƿ��Ǳ༭�װ�
     */
    private boolean editBoard;
    /**
     * ���ö���
     */
    private TObject tobject;
    /**
     * ����UI������
     * @param uiAdapter TUIAdapter
     */
    public void setUIAdapter(TUIAdapter uiAdapter)
    {
        this.uiAdapter = uiAdapter;
    }
    /**
     * �õ�UI������
     * @return TUIAdapter
     */
    public TUIAdapter getUIAdapter()
    {
        return uiAdapter;
    }
    /**
     * ���óɱ༭�װ�
     * @param editBoard boolean
     */
    public void setEditBoard(boolean editBoard)
    {
        this.editBoard = editBoard;
    }
    /**
     * �Ƿ��Ǳ༭�װ�
     * @return boolean
     */
    public boolean isEditBoard()
    {
        return editBoard;
    }
    /**
     * �������ö���
     * @param tobject TObject
     */
    public void setTObject(TObject tobject)
    {
        this.tobject = tobject;
    }
    /**
     * �õ����ö���
     * @return TObject
     */
    public TObject getTObject()
    {
        return tobject;
    }
    /**
     * ����UI������
     */
    public void createUIAdapter()
    {
        if(getUIAdapter() != null)
            destroyUIAdapter();
        setUIAdapter(new TUIAdapter(this));
    }
    /**
     * �ͷ�UI������
     */
    public void destroyUIAdapter()
    {
        if(getUIAdapter() == null)
            return;
        getUIAdapter().destroy();
        setUIAdapter(null);
    }
    /**
     * ����������
     */
    public void startUIAdapter()
    {
        startUIAdapter(false);
    }
    /**
     * ����������
     * @param isBoard boolean true �ǵװ� false ����
     */
    public void startUIAdapter(boolean isBoard)
    {
        //���ñ༭�װ�
        setEditBoard(isBoard);
        //����UI������
        createUIAdapter();
        if(isBoard)
            //�����ұߡ���������½ǿ�����������
            getUIAdapter().setListPanes("2,4,7");
        //��������
        getUIAdapter().setScreen(true);
    }
    /**
     * ֹͣ������
     */
    public void stopUIAdapter()
    {
        destroyUIAdapter();
    }
    /**
     * ���ص��Զ���
     * @param object TObject
     */
    public void loadTObject(TObject object)
    {
        if(object == null)
            return;
        //�������ö���
        setTObject(object);
        //�������ö���
        object.setComponent(this);
        //ִ�г���
        Row[] rows = object.getRows();
        for(int i = 0;i < rows.length;i++)
        {
            Row row = rows[i];
            String name = row.getName();
            if("ControlClassName".equalsIgnoreCase(name))
                continue;
            if("Item".equalsIgnoreCase(name))
                continue;
            String value = row.getValue();
            onBaseMessage(name + "=" + value,null);
        }
    }
    /**
     * �½�����ĳ�ʼֵ
     * @param object TObject
     */
    public void createInit(TObject object)
    {
        if(object == null)
            return;
        object.setValue("Width","77");
        object.setValue("Height","20");
        object.setValue("Text","0000/00/00");
    }
    /**
     * �õ������б�
     * @return TAttributeList
     */
    public TAttributeList getAttributes()
    {
        TAttributeList data = new TAttributeList();
        data.add(new TAttribute("Tag","String","","Left"));
        data.add(new TAttribute("Visible","boolean","Y","Center"));
        data.add(new TAttribute("Enabled","boolean","Y","Center"));
        data.add(new TAttribute("Name","String","","Left"));
        data.add(new TAttribute("Text","String","","Left"));
        data.add(new TAttribute("Tip","String","","Left"));
        data.add(new TAttribute("zhTip","String","","Left"));
        data.add(new TAttribute("enTip","String","","Left"));
        data.add(new TAttribute("Action","String","","Left"));
        data.add(new TAttribute("FocusLostAction","String","","Left"));
        data.add(new TAttribute("Time","String","","Left"));
        data.add(new TAttribute("X","int","0","Right"));
        data.add(new TAttribute("Y","int","0","Right"));
        data.add(new TAttribute("Width","int","0","Right"));
        data.add(new TAttribute("Height","int","0","Right"));
        return data;
    }
    /**
     * ��������
     * @param name String ������
     * @param value String ����ֵ
     */
    public void setAttribute(String name,String value)
    {
        if("Tag".equalsIgnoreCase(name))
        {
            setTag(value);
            getTObject().setTag(value);
            if(getUIAdapter() != null)
                getUIAdapter().modifiedTag();
            return;
        }
        if("Visible".equalsIgnoreCase(name))
        {
            setVisible(StringTool.getBoolean(value));
            getTObject().setValue("Visible",value);
            return;
        }
        if("Enabled".equalsIgnoreCase(name))
        {
            setEnabled(StringTool.getBoolean(value));
            getTObject().setValue("Enabled",value);
            return;
        }
        if("Name".equalsIgnoreCase(name))
        {
            setName(value);
            getTObject().setValue("Name",value);
            return;
        }
        if("Tip".equalsIgnoreCase(name))
        {
            setToolTipText(value);
            getTObject().setValue("Tip",value);
            return;
        }
        if("zhTip".equalsIgnoreCase(name))
        {
            setZhTip(value);
            getTObject().setValue("zhTip",value);
            return;
        }
        if("enTip".equalsIgnoreCase(name))
        {
            setEnTip(value);
            getTObject().setValue("enTip",value);
            return;
        }
        if("Text".equalsIgnoreCase(name))
        {
            setText(value);
            getTObject().setValue("Text",value);
            return;
        }
        if("Action".equalsIgnoreCase(name))
        {
            setActionMessage(value);
            getTObject().setValue("Action",value);
            return;
        }
        if("FocusLostAction".equalsIgnoreCase(name))
        {
            setFocusLostAction(value);
            getTObject().setValue("FocusLostAction",value);
            return;
        }
        if("Time".equalsIgnoreCase(name))
        {
            setTime(value);
            getTObject().setValue("Time",value);
            return;
        }
        if("X".equalsIgnoreCase(name))
        {
            setX(Integer.parseInt(value));
            return;
        }
        if("Y".equalsIgnoreCase(name))
        {
            setY(Integer.parseInt(value));
            return;
        }
        if("Width".equalsIgnoreCase(name))
        {
            setWidth(Integer.parseInt(value));
            return;
        }
        if("Height".equalsIgnoreCase(name))
        {
            setHeight(Integer.parseInt(value));
            return;
        }
    }
    /**
     * ����X
     * @param x int
     */
    public void setX(int x)
    {
        int oldx = getX();
        if(oldx == x)
            return;
        super.setX(x);
        if(getUIAdapter() != null)
            getUIAdapter().setX(x,oldx);
    }
    /**
     * ����Y
     * @param y int
     */
    public void setY(int y)
    {
        int oldy = getY();
        if(oldy == y)
            return;
        super.setY(y);
        if(getUIAdapter() != null)
            getUIAdapter().setY(y,oldy);
    }
    /**
     * ���ÿ���
     * @param width int
     */
    public void setWidth(int width)
    {
        int oldWidth = getWidth();
        if(oldWidth == width)
            return;
        super.setWidth(width);
        if(getUIAdapter() != null)
            getUIAdapter().setWidth(width,oldWidth);
    }
    /**
     * ���ø߶�
     * @param height int
     */
    public void setHeight(int height)
    {
        int oldHeight = getHeight();
        if(oldHeight == height)
            return;
        super.setHeight(height);
        if(getUIAdapter() != null)
            getUIAdapter().setHeight(height,oldHeight);
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return "TDateField";
    }
}