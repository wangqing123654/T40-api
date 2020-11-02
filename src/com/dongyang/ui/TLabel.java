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
        object.setValue("Width","72");
        object.setValue("Height","15");
        object.setValue("Text","TLabel");
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
        data.add(new TAttribute("Text","String","","Left"));//,"TYPE:LabelEditText;TITLE:显示文字"));
        data.add(new TAttribute("zhText","String","","Left"));
        data.add(new TAttribute("enText","String","","Left"));
        data.add(new TAttribute("HorizontalAlignment","HorizontalAlignmentComboBox","","Left"));
        data.add(new TAttribute("VerticalAlignment","VerticalAlignmentComboBox","","Left"));
        data.add(new TAttribute("FontName","String","宋体","Left"));
        data.add(new TAttribute("FontSize","int","14","Right"));
        data.add(new TAttribute("FontStyle","int","0","Right"));
        data.add(new TAttribute("IconName","String","","Left"));
        data.add(new TAttribute("AutoIconSize","boolean","N","Center"));
        data.add(new TAttribute("PictureName","String","","Left"));
        data.add(new TAttribute("controlClassName","String","","Left"));
        data.add(new TAttribute("Border","String","","Left"));
        data.add(new TAttribute("color","String","黑","Left"));
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
        return "TLabel";
    }
}
