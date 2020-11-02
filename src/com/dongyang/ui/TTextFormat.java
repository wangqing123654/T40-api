package com.dongyang.ui;

import com.dongyang.ui.base.TTextFormatBase;
import com.dongyang.ui.edit.TAttributeList.TAttribute;
import com.dongyang.ui.edit.TUIAdapter;
import com.dongyang.config.TConfigParse.Row;
import com.dongyang.util.StringTool;
import com.dongyang.config.TConfigParse.TObject;
import com.dongyang.ui.edit.TAttributeList;
import com.dongyang.util.TypeTool;

/**
 *
 * <p>Title: ��ʽ¼����</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *R
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TTextFormat extends TTextFormatBase
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
        object.setValue("Text","TTextFormat");
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
        data.add(new TAttribute("HorizontalAlignment","HorizontalAlignmentComboBox","","Left"));
        data.add(new TAttribute("FontName","String","����","Left"));
        data.add(new TAttribute("FontSize","int","14","Right"));
        data.add(new TAttribute("FontStyle","int","0","Right"));
        data.add(new TAttribute("color","String","��","Left"));
        data.add(new TAttribute("BKColor","String","","Left"));
        //������չ����
        getEnlargeAttributes(data);

        data.add(new TAttribute("DynamicDownload","boolean","N","Center"));
        data.add(new TAttribute("NextFocus","String","","Left"));
        data.add(new TAttribute("EditValueAction","String","","Left"));
        data.add(new TAttribute("ControlClassName","String","","Left"));
        data.add(new TAttribute("InputLength","int","0","Right"));
        data.add(new TAttribute("Action","String","","Left"));
        data.add(new TAttribute("FocusLostAction","String","","Left"));
        data.add(new TAttribute("ClickedAction","String","","Left"));
        data.add(new TAttribute("RightClickedAction","String","","Left"));
        data.add(new TAttribute("DoubleClickedAction","String","","Left"));
        /*data.add(new TAttribute("PyTag","String","","Left"));
        data.add(new TAttribute("StateTag","String","","Left"));
        data.add(new TAttribute("CityTag","String","","Left"));
        data.add(new TAttribute("ExePID","boolean","","Center"));
        data.add(new TAttribute("ForeignerTag","String","","Left"));
        data.add(new TAttribute("SexTag","String","","Left"));
        data.add(new TAttribute("BirdayTag","String","","Left"));*/
        data.add(new TAttribute("X","int","0","Right"));
        data.add(new TAttribute("Y","int","0","Right"));
        data.add(new TAttribute("Width","int","0","Right"));
        data.add(new TAttribute("Height","int","0","Right"));
        data.add(new TAttribute("AutoX","boolean","N","Center"));
        data.add(new TAttribute("AutoY","boolean","N","Center"));
        data.add(new TAttribute("AutoWidth","boolean","N","Center"));
        data.add(new TAttribute("AutoHeight","boolean","N","Center"));
        data.add(new TAttribute("AutoSize","int","5","Right"));
        //�������ӳ���;
        data.add(new TAttribute("DBPoolName","String","","Left"));
        //System.out.println("-------��������Required------");
        //���������
        data.add(new TAttribute("Required","boolean","N","Center")); 
        
        return data;
    }
    /**
     * ������չ����
     * @param data TAttributeList
     */
    public void getEnlargeAttributes(TAttributeList data)
    {
        data.add(new TAttribute("FormatType","String","","Left"));
        data.add(new TAttribute("Format","String","","Left"));
        data.add(new TAttribute("ShowDownButton","boolean","N","Center"));
        data.add(new TAttribute("PopupMenuWidth","int","200","Right"));
        data.add(new TAttribute("PopupMenuHeight","int","100","Right"));
        data.add(new TAttribute("PopupMenuHeader","String","","Left"));
        data.add(new TAttribute("PopupMenuEnHeader","String","","Left"));
        data.add(new TAttribute("PopupMenuSQL","String","","Left"));
        data.add(new TAttribute("HisOneNullRow","boolean","N","Center"));
        data.add(new TAttribute("PopupMenuFilter","String","","Left"));
        data.add(new TAttribute("InputPopupMenu","boolean","Y","Center"));
        data.add(new TAttribute("ShowColumnList","String","","Left"));
        data.add(new TAttribute("LanguageMap","String","","Left"));
        data.add(new TAttribute("ValueColumn","String","","Left"));       
         
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
        if("Text".equalsIgnoreCase(name))
        {
            setText(value);
            getTObject().setValue("Text",value);
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
        if("ControlClassName".equalsIgnoreCase(name))
        {
            getTObject().setValue("ControlClassName",value);
            return;
        }
        if("Action".equalsIgnoreCase(name))
        {
            setActionMessage(value);
            getTObject().setValue("Action",value);
            return;
        }
        if("HorizontalAlignment".equalsIgnoreCase(name))
        {
            setHorizontalAlignment(StringTool.horizontalAlignment(value));
            getTObject().setValue("HorizontalAlignment",value);
            return;
        }
        if("FormatType".equalsIgnoreCase(name))
        {
            setFormatType(value);
            getTObject().setValue("FormatType",value);
            return;
        }
        if("Format".equalsIgnoreCase(name))
        {
            setFormat(value);
            getTObject().setValue("Format",value);
            return;
        }
        if("showDownButton".equalsIgnoreCase(name))
        {
            setShowDownButton(StringTool.getBoolean(value));
            getTObject().setValue("showDownButton",value);
            return;
        }
        if("PopupMenuWidth".equalsIgnoreCase(name))
        {
            setPopupMenuWidth(StringTool.getInt(value));
            getTObject().setValue("PopupMenuWidth",value);
            return;
        }
        if("PopupMenuHeight".equalsIgnoreCase(name))
        {
            setPopupMenuHeight(StringTool.getInt(value));
            getTObject().setValue("PopupMenuHeight",value);
            return;
        }
        if("PopupMenuHeader".equalsIgnoreCase(name))
        {
            setPopupMenuHeader(value);
            getTObject().setValue("PopupMenuHeader",value);
            return;
        }
        if("PopupMenuEnHeader".equalsIgnoreCase(name))
        {
            setPopupMenuEnHeader(value);
            getTObject().setValue("PopupMenuEnHeader",value);
            return;
        }
        if("LanguageMap".equalsIgnoreCase(name))
        {
            setLanguageMap(value);
            getTObject().setValue("LanguageMap",value);
            return;
        }
        if("PopupMenuSQL".equalsIgnoreCase(name))
        {
            setPopupMenuSQL(value);
            getTObject().setValue("PopupMenuSQL",value);
            return;
        }
        if("HisOneNullRow".equalsIgnoreCase(name))
        {
            setHisOneNullRow(TypeTool.getBoolean(value));
            getTObject().setValue("HisOneNullRow",value);
            return;
        }
        if("PopupMenuFilter".equalsIgnoreCase(name))
        {
            setPopupMenuFilter(value);
            getTObject().setValue("PopupMenuFilter",value);
            return;
        }
        if("InputPopupMenu".equalsIgnoreCase(name))
        {
            setInputPopupMenu(TypeTool.getBoolean(value));
            getTObject().setValue("InputPopupMenu",value);
            return;
        }
        if("ShowColumnList".equalsIgnoreCase(name))
        {
            setShowColumnList(value);
            getTObject().setValue("ShowColumnList",value);
            return;
        }
        if("ValueColumn".equalsIgnoreCase(name))
        {
            setValueColumn(value);
            getTObject().setValue("ValueColumn",value);
            return;
        }
        if("DynamicDownload".equalsIgnoreCase(name))
        {
            setDynamicDownload(TypeTool.getBoolean(value));
            getTObject().setValue("DynamicDownload",value);
            return;
        }
        if("EditValueAction".equalsIgnoreCase(name))
        {
            setEditValueAction(value);
            getTObject().setValue("EditValueAction",value);
            return;
        }
        if("NextFocus".equalsIgnoreCase(name))
        {
            setNextFocus(value);
            getTObject().setValue("NextFocus",value);
            return;
        }

        if("FocusLostAction".equalsIgnoreCase(name))
        {
            setFocusLostAction(value);
            getTObject().setValue("FocusLostAction",value);
            return;
        }
        if("ClickedAction".equalsIgnoreCase(name))
        {
            setClickedAction(value);
            getTObject().setValue("ClickedAction",value);
            return;
        }
        if("DoubleClickedAction".equalsIgnoreCase(name))
        {
            setDoubleClickedAction(value);
            getTObject().setValue("DoubleClickedAction",value);
            return;
        }
        if("RightClickedAction".equalsIgnoreCase(name))
        {
            setRightClickedAction(value);
            getTObject().setValue("RightClickedAction",value);
            return;
        }
        /*if("PyTag".equalsIgnoreCase(name))
        {
            setPyTag(value);
            getTObject().setValue("PyTag",value);
            return;
        }
        if("StateTag".equalsIgnoreCase(name))
        {
            setStateTag(value);
            getTObject().setValue("StateTag",value);
            return;
        }
        if("CityTag".equalsIgnoreCase(name))
        {
            setCityTag(value);
            getTObject().setValue("CityTag",value);
            return;
        }
        if("ExePID".equalsIgnoreCase(name))
        {
            setExePID(StringTool.getBoolean(value));
            getTObject().setValue("ExePID",value);
            return;
        }
        if("ForeignerTag".equalsIgnoreCase(name))
        {
            setForeignerTag(value);
            getTObject().setValue("ForeignerTag",value);
            return;
        }
        if("SexTag".equalsIgnoreCase(name))
        {
            setSexTag(value);
            getTObject().setValue("SexTag",value);
            return;
        }
        if("BirdayTag".equalsIgnoreCase(name))
        {
            setBirdayTag(value);
            getTObject().setValue("BirdayTag",value);
            return;
        }*/
        if("InputLength".equalsIgnoreCase(name))
        {
            setInputLength(StringTool.getInt(value));
            getTObject().setValue("InputLength",value);
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
        if("AutoSize".equalsIgnoreCase(name))
        {
            setAutoSize(StringTool.getInt(value));
            getTObject().setValue("AutoSize",value);
            return;
        }

        //
        if ("DBPoolName".equalsIgnoreCase(name)) {
            setDbPoolName(value);
            getTObject().setValue("DBPoolName", value);
            return;
        }
        //
        if ("Required".equalsIgnoreCase(name)) {
        	setRequired(StringTool.getBoolean(value));
            getTObject().setValue("Required", value);
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
        return "TTextFormat";
    }

}
