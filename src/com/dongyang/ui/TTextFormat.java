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
 * <p>Title: 格式录入期</p>
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
     * UI适配器
     */
    private TUIAdapter uiAdapter;
    /**
     * 是否是编辑底板
     */
    private boolean editBoard;
    /**
     * 配置对象
     */
    private TObject tobject;
    /**
     * 设置UI适配器
     * @param uiAdapter TUIAdapter
     */
    public void setUIAdapter(TUIAdapter uiAdapter)
    {
        this.uiAdapter = uiAdapter;
    }
    /**
     * 得到UI适配器
     * @return TUIAdapter
     */
    public TUIAdapter getUIAdapter()
    {
        return uiAdapter;
    }
    /**
     * 设置成编辑底板
     * @param editBoard boolean
     */
    public void setEditBoard(boolean editBoard)
    {
        this.editBoard = editBoard;
    }
    /**
     * 是否是编辑底板
     * @return boolean
     */
    public boolean isEditBoard()
    {
        return editBoard;
    }
    /**
     * 设置配置对象
     * @param tobject TObject
     */
    public void setTObject(TObject tobject)
    {
        this.tobject = tobject;
    }
    /**
     * 得到配置对象
     * @return TObject
     */
    public TObject getTObject()
    {
        return tobject;
    }
    /**
     * 创建UI适配器
     */
    public void createUIAdapter()
    {
        if(getUIAdapter() != null)
            destroyUIAdapter();
        setUIAdapter(new TUIAdapter(this));
    }
    /**
     * 释放UI适配器
     */
    public void destroyUIAdapter()
    {
        if(getUIAdapter() == null)
            return;
        getUIAdapter().destroy();
        setUIAdapter(null);
    }
    /**
     * 启动适配器
     */
    public void startUIAdapter()
    {
        startUIAdapter(false);
    }
    /**
     * 启动适配器
     * @param isBoard boolean true 是底版 false 不是
     */
    public void startUIAdapter(boolean isBoard)
    {
        //设置编辑底板
        setEditBoard(isBoard);
        //创建UI适配器
        createUIAdapter();
        if(isBoard)
            //设置右边、下面和右下角可以拖拉控制
            getUIAdapter().setListPanes("2,4,7");
        //启动屏风
        getUIAdapter().setScreen(true);
    }
    /**
     * 停止适配器
     */
    public void stopUIAdapter()
    {
        destroyUIAdapter();
    }
    /**
     * 加载调试对象
     * @param object TObject
     */
    public void loadTObject(TObject object)
    {
        if(object == null)
            return;
        //保存配置对象
        setTObject(object);
        //连接配置对象
        object.setComponent(this);
        //执行程序
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
     * 新建对象的初始值
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
     * 得到属性列表
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
        data.add(new TAttribute("FontName","String","宋体","Left"));
        data.add(new TAttribute("FontSize","int","14","Right"));
        data.add(new TAttribute("FontStyle","int","0","Right"));
        data.add(new TAttribute("color","String","黑","Left"));
        data.add(new TAttribute("BKColor","String","","Left"));
        //增加扩展属性
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
        //增加连接池名;
        data.add(new TAttribute("DBPoolName","String","","Left"));
        //System.out.println("-------增加属性Required------");
        //加入必须项
        data.add(new TAttribute("Required","boolean","N","Center")); 
        
        return data;
    }
    /**
     * 增加扩展属性
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
     * 设置属性
     * @param name String 属性名
     * @param value String 属性值
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
     * 设置X
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
     * 设置Y
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
     * 设置宽度
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
     * 设置高度
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
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return "TTextFormat";
    }

}
