package com.dongyang.ui;

import com.dongyang.ui.base.TLabelBase;
import com.dongyang.ui.edit.TUIAdapter;
import com.dongyang.config.TConfigParse.TObject;
import com.dongyang.config.TConfigParse.Row;
import com.dongyang.ui.edit.TAttributeList.TAttribute;
import com.dongyang.ui.edit.TAttributeList;
import com.dongyang.util.StringTool;
import com.dongyang.util.TypeTool;

public class TLabel extends TLabelBase{
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
        object.setValue("Width","72");
        object.setValue("Height","15");
        object.setValue("Text","TLabel");
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
        data.add(new TAttribute("Text","String","","Left"));//,"TYPE:LabelEditText;TITLE:��ʾ����"));
        data.add(new TAttribute("zhText","String","","Left"));
        data.add(new TAttribute("enText","String","","Left"));
        data.add(new TAttribute("HorizontalAlignment","HorizontalAlignmentComboBox","","Left"));
        data.add(new TAttribute("VerticalAlignment","VerticalAlignmentComboBox","","Left"));
        data.add(new TAttribute("FontName","String","����","Left"));
        data.add(new TAttribute("FontSize","int","14","Right"));
        data.add(new TAttribute("FontStyle","int","0","Right"));
        data.add(new TAttribute("IconName","String","","Left"));
        data.add(new TAttribute("AutoIconSize","boolean","N","Center"));
        data.add(new TAttribute("PictureName","String","","Left"));
        data.add(new TAttribute("controlClassName","String","","Left"));
        data.add(new TAttribute("Border","String","","Left"));
        data.add(new TAttribute("color","String","��","Left"));
        data.add(new TAttribute("BKColor","String","","Left"));
        data.add(new TAttribute("DrawControlClassName","String","","Left"));
        data.add(new TAttribute("CursorType","int","0","Right"));
        data.add(new TAttribute("Action","String","","Left"));
        data.add(new TAttribute("X","int","0","Right"));
        data.add(new TAttribute("Y","int","0","Right"));
        data.add(new TAttribute("Width","int","0","Right"));
        data.add(new TAttribute("Height","int","0","Right"));
        data.add(new TAttribute("AutoCenter","boolean","N","Center"));
        data.add(new TAttribute("AutoX","boolean","N","Center"));
        data.add(new TAttribute("AutoY","boolean","N","Center"));
        data.add(new TAttribute("AutoWidth","boolean","N","Center"));
        data.add(new TAttribute("AutoHeight","boolean","N","Center"));
        data.add(new TAttribute("AutoW","boolean","N","Center"));
        data.add(new TAttribute("AutoH","boolean","N","Center"));
        data.add(new TAttribute("AutoSize","int","5","Right"));
        data.add(new TAttribute("AutoWidthSize","int","0","Right"));
        data.add(new TAttribute("AutoHeightSize","int","0","Right"));
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
        if("ControlClassName".equalsIgnoreCase(name))
        {
            getTObject().setValue("ControlClassName",value);
            return;
        }
        if("DrawControlClassName".equalsIgnoreCase(name))
        {
            setDrawControlClassName(value);
            getTObject().setValue("DrawControlClassName",value);
            repaint();
            return;
        }
        if("Text".equalsIgnoreCase(name))
        {
            setText(value);
            getTObject().setValue("Text",value);
            return;
        }
        if("zhText".equalsIgnoreCase(name))
        {
            setZhText(value);
            getTObject().setValue("zhText",value);
            return;
        }
        if("enText".equalsIgnoreCase(name))
        {
            setEnText(value);
            getTObject().setValue("enText",value);
            return;
        }
        if("FontName".equalsIgnoreCase(name))
        {
            setFontName(value);
            getTObject().setValue("FontName",value);
            return;
        }
        if("FontSize".equalsIgnoreCase(name))
        {
            setFontSize(Integer.parseInt(value));
            getTObject().setValue("FontSize",value);
            return;
        }
        if("FontStyle".equalsIgnoreCase(name))
        {
            setFontStyle(Integer.parseInt(value));
            getTObject().setValue("FontStyle",value);
            return;
        }
        if("HorizontalAlignment".equalsIgnoreCase(name))
        {
            setHorizontalAlignment(StringTool.horizontalAlignment(value));
            getTObject().setValue("HorizontalAlignment",value);
            return;
        }
        if("VerticalAlignment".equalsIgnoreCase(name))
        {
            setVerticalAlignment(StringTool.verticalAlignment(value));
            getTObject().setValue("VerticalAlignment",value);
            return;
        }
        if("IconName".equalsIgnoreCase(name))
        {
            setIconName(value);
            getTObject().setValue("IconName",value);
            return;
        }
        if("AutoIconSize".equalsIgnoreCase(name))
        {
            setAutoIconSize(TypeTool.getBoolean(value));
            getTObject().setValue("AutoIconSize",value);
            return;
        }
        if("PictureName".equalsIgnoreCase(name))
        {
            setPictureName(value);
            getTObject().setValue("PictureName",value);
            return;
        }
        if("Border".equalsIgnoreCase(name))
        {
            setBorder(value);
            getTObject().setValue("Border",value);
            return;
        }
        if("Color".equalsIgnoreCase(name))
        {
            setColor(value);
            getTObject().setValue("Color",value);
            return;
        }
        if("BKColor".equalsIgnoreCase(name))
        {
            setBKColor(value);
            getTObject().setValue("BKColor",value);
            return;
        }
        if("CursorType".equalsIgnoreCase(name))
        {
            setCursorType(StringTool.getInt(value));
            getTObject().setValue("CursorType",value);
            return;
        }
        if("Action".equalsIgnoreCase(name))
        {
            setActionMessage(value);
            getTObject().setValue("Action",value);
            return;
        }
        if("X".equalsIgnoreCase(name))
        {
            setX(StringTool.getInt(value));
            return;
        }
        if("Y".equalsIgnoreCase(name))
        {
            setY(StringTool.getInt(value));
            return;
        }
        if("Width".equalsIgnoreCase(name))
        {
            setWidth(StringTool.getInt(value));
            return;
        }
        if("Height".equalsIgnoreCase(name))
        {
            setHeight(StringTool.getInt(value));
            return;
        }
        if("AutoCenter".equalsIgnoreCase(name))
        {
            setAutoCenter(StringTool.getBoolean(value));
            getTObject().setValue("AutoCenter",value);
            return;
        }
        if("AutoX".equalsIgnoreCase(name))
        {
            setAutoX(StringTool.getBoolean(value));
            getTObject().setValue("AutoX",value);
            return;
        }
        if("AutoY".equalsIgnoreCase(name))
        {
            setAutoY(StringTool.getBoolean(value));
            getTObject().setValue("AutoY",value);
            return;
        }
        if("AutoWidth".equalsIgnoreCase(name))
        {
            setAutoWidth(StringTool.getBoolean(value));
            getTObject().setValue("AutoWidth",value);
            return;
        }
        if("AutoHeight".equalsIgnoreCase(name))
        {
            setAutoHeight(StringTool.getBoolean(value));
            getTObject().setValue("AutoHeight",value);
            return;
        }
        if("AutoW".equalsIgnoreCase(name))
        {
            setAutoW(StringTool.getBoolean(value));
            getTObject().setValue("AutoW",value);
            return;
        }
        if("AutoH".equalsIgnoreCase(name))
        {
            setAutoH(StringTool.getBoolean(value));
            getTObject().setValue("AutoH",value);
            return;
        }
        if("AutoSize".equalsIgnoreCase(name))
        {
            setAutoSize(StringTool.getInt(value));
            getTObject().setValue("AutoSize",value);
            return;
        }
        if("AutoWidthSize".equalsIgnoreCase(name))
        {
            setAutoWidthSize(StringTool.getInt(value));
            getTObject().setValue("AutoWidthSize",value);
            return;
        }
        if("AutoHeightSize".equalsIgnoreCase(name))
        {
            setAutoHeightSize(StringTool.getInt(value));
            getTObject().setValue("AutoHeightSize",value);
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
     * ���ÿ��
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
        return "TLabel";
    }
}
