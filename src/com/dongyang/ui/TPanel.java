package com.dongyang.ui;

import com.dongyang.ui.base.TPanelBase;
import com.dongyang.config.TConfigParse.TObject;
import com.dongyang.ui.edit.TUIAdapter;
import com.dongyang.config.TConfigParse.Row;
import com.dongyang.util.StringTool;
import java.awt.Component;
import com.dongyang.ui.edit.TAttributeList.TAttribute;
import com.dongyang.ui.edit.TAttributeList;
import com.dongyang.ui.event.TComponentListener;

public class TPanel extends TPanelBase{
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
        getUIAdapter().setTitleMove(!isBoard);
        //启动底板适配器
        getUIAdapter().setBoard(true);
        //启动子集适配器
        onAdapterMember(true);
    }
    /**
     * 停止适配器
     */
    public void stopUIAdapter()
    {
        //停止子集适配器
        onAdapterMember(false);
        //停止底板适配器
        getUIAdapter().setBoard(false);
        //释放适配器
        destroyUIAdapter();
    }
    /**
     * 适配器启动
     * @param state boolean
     */
    public void onAdapterMember(boolean state)
    {
        //加载组件
        int count = getTObject().getItemCount();
        for(int i = 0;i < count;i++)
        {
            TObject item = getTObject().getItem(i);
            TComponent component = item.getComponent();
            if(component == null)
                continue;
            if(state)
                component.callFunction("startUIAdapter");
            else
                component.callFunction("stopUIAdapter");
        }
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
            //if("DrawControlClassName".equalsIgnoreCase(name))
            //    continue;
            if("Item".equalsIgnoreCase(name))
                continue;
            String value = row.getValue();
            onBaseMessage(name + "=" + value,null);
        }
        //加载组件
        int count = object.getItemCount();
        for(int i = 0;i < count;i++)
            addItemFromTObject(object.getItem(i));
    }
    /**
     * 增加组件
     * @param item TObject
     * @return TComponent
     */
    public TComponent addItemFromTObject(TObject item)
    {
        return addItemFromTObject(item,false);
    }
    /**
     * 增加组件
     * @param item TObject
     * @param isInit boolean true 初始化默认值 false 不初始化
     * @return TComponent
     */
    public TComponent addItemFromTObject(TObject item,boolean isInit)
    {
        if(item == null)
            return null;
        TComponent component = createObject(item);
        if(component == null)
            return null;
        if(!(component instanceof Component))
            return null;
        Component com = (Component) component;
        if(isInit)
            //初始化组件的默认值
            component.callFunction("createInit",item);
        //加载对象
        component.callFunction("loadTObject",item);

        if (item.getPosition() == null)
        {
            if(getWorkPanel() != null)
                getWorkPanel().add(com);
            else
                add(com);
        }
        else
        {
            if(getWorkPanel() != null)
                getWorkPanel().add(com, StringTool.layoutConstraint(item.getPosition()));
            else
                add(com, StringTool.layoutConstraint(item.getPosition()));
        }
        //增加尺寸同步监听
        if (com instanceof TComponent)
        {
            ((TComponent)component).callFunction("onParentResize",getWidth(),getHeight() - getWorkPanelY());
            callFunction("addEventListener",
                         getTag() + "->" + TComponentListener.RESIZED,
                         com, "onParentResize");

        }
        return component;
    }
    /**
     * 加载对象
     * @param object TObject
     * @return TComponent
     */
    public TComponent createObject(TObject object)
    {
        Object obj = getConfigParm().loadObject(object.getType());
        if(obj == null)
            return null;
        if(!(obj instanceof TComponent))
            return null;
        TComponent component = (TComponent)obj;
        component.setTag(object.getTag());
        component.callFunction("setConfigParm",getConfigParm());
        return component;
    }
    /**
     * 新建对象的初始值
     * @param object TObject
     */
    public void createInit(TObject object)
    {
        if(object == null)
            return;
        object.setValue("Width","81");
        object.setValue("Height","81");
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
        data.add(new TAttribute("LoadFlg","boolean","N","Center"));
        data.add(new TAttribute("TopMenu","boolean","N","Center"));
        data.add(new TAttribute("TopToolBar","boolean","N","Center"));
        data.add(new TAttribute("ShowTitle","boolean","N","Center"));
        data.add(new TAttribute("ShowMenu","boolean","N","Center"));
        data.add(new TAttribute("Title","String","","Left"));
        data.add(new TAttribute("zhTitle","String","","Left"));
        data.add(new TAttribute("enTitle","String","","Left"));
        data.add(new TAttribute("MenuConfig","String","","Left"));
        data.add(new TAttribute("ToolBar","boolean","N","Center"));
        data.add(new TAttribute("MenuMap","String","","Left"));
        data.add(new TAttribute("MenuID","String","","Left"));
        data.add(new TAttribute("ControlClassName","String","","Left"));
        data.add(new TAttribute("Border","String","","Left"));
        data.add(new TAttribute("BKColor","String","161,220,230","Left"));
        data.add(new TAttribute("Opaque","boolean","Y","Center"));
        data.add(new TAttribute("DrawControlClassName","String","","Left"));
        data.add(new TAttribute("MoveType","MoveTypeComboBox","","Left"));
        data.add(new TAttribute("FocusList","String","","Left"));
        data.add(new TAttribute("ButtonGroupFlg","boolean","Y","Center"));
        data.add(new TAttribute("ShowWindowAction","String","","Left"));
        data.add(new TAttribute("ChildToolBarAction","String","","Left"));
        data.add(new TAttribute("X","int","0","Right"));
        data.add(new TAttribute("Y","int","0","Right"));
        data.add(new TAttribute("Width","int","0","Right"));
        data.add(new TAttribute("Height","int","0","Right"));
        data.add(new TAttribute("AutoX","boolean","N","Center"));
        data.add(new TAttribute("AutoY","boolean","N","Center"));
        data.add(new TAttribute("AutoWidth","boolean","N","Center"));
        data.add(new TAttribute("AutoHeight","boolean","N","Center"));
        data.add(new TAttribute("AutoW","boolean","N","Center"));
        data.add(new TAttribute("AutoH","boolean","N","Center"));
        data.add(new TAttribute("AutoSize","int","5","Right"));
        data.add(new TAttribute("AutoHSize","int","0","Right"));
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
        if("TopMenu".equalsIgnoreCase(name))
        {
            //setTopMenu(StringTool.getBoolean(value));
            getTObject().setValue("TopMenu",value);
            return;
        }
        if("LoadFlg".equalsIgnoreCase(name))
        {
            setLoadFlg(StringTool.getBoolean(value));
            getTObject().setValue("LoadFlg",value);
            return;
        }
        if("TopToolBar".equalsIgnoreCase(name))
        {
            //setTopToolBar(StringTool.getBoolean(value));
            getTObject().setValue("TopToolBar",value);
            return;
        }
        if("ShowTitle".equalsIgnoreCase(name))
        {
            setShowTitle(StringTool.getBoolean(value));
            getTObject().setValue("ShowTitle",value);
            return;
        }
        if("ShowMenu".equalsIgnoreCase(name))
        {
            setShowMenu(StringTool.getBoolean(value));
            getTObject().setValue("ShowMenu",value);
            return;
        }
        if("Title".equalsIgnoreCase(name))
        {
            setTitle(value);
            getTObject().setValue("Title",value);
            return;
        }
        if("zhTitle".equalsIgnoreCase(name))
        {
            setZhTitle(value);
            getTObject().setValue("zhTitle",value);
            return;
        }
        if("enTitle".equalsIgnoreCase(name))
        {
            setEnTitle(value);
            getTObject().setValue("enTitle",value);
            return;
        }
        if("MenuConfig".equalsIgnoreCase(name))
        {
            setMenuConfig(value);
            getTObject().setValue("MenuConfig",value);
            return;
        }
        if("ToolBar".equalsIgnoreCase(name))
        {
            setShowToolBar(StringTool.getBoolean(value));
            getTObject().setValue("ToolBar",value);
            return;
        }
        if("MenuMap".equalsIgnoreCase(name))
        {
            setMenuMap(value);
            getTObject().setValue("MenuMap",value);
            return;
        }
        if("MenuID".equalsIgnoreCase(name))
        {
            setMenuID(value);
            getTObject().setValue("MenuID",value);
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
        if ("MoveType".equalsIgnoreCase(name))
        {
            setMoveType(StringTool.getInt(value));
            getTObject().setValue("MoveType", value);
            return;
        }
        if("Border".equalsIgnoreCase(name))
        {
            setBorder(value);
            getTObject().setValue("Border",value);
            return;
        }
        if("BKColor".equalsIgnoreCase(name))
        {
            setBKColor(value);
            getTObject().setValue("BKColor",value);
            return;
        }
        if("Opaque".equalsIgnoreCase(name))
        {
            setOpaque(StringTool.getBoolean(value));
            getTObject().setValue("Opaque",value);
            return;
        }
        if("FocusList".equalsIgnoreCase(name))
        {
            setFocusList(value);
            getTObject().setValue("FocusList",value);
            return;
        }
        if("ButtonGroupFlg".equalsIgnoreCase(name))
        {
            setButtonGroupFlg(StringTool.getBoolean(value));
            getTObject().setValue("ButtonGroupFlg",value);
            return;
        }
        if("ShowWindowAction".equalsIgnoreCase(name))
        {
            setShowWindowAction(value);
            getTObject().setValue("ShowWindowAction",value);
            return;
        }
        if("ChildToolBarAction".equalsIgnoreCase(name))
        {
            setChildToolBarAction(value);
            getTObject().setValue("ChildToolBarAction",value);
            return;
        }
        if("X".equalsIgnoreCase(name))
        {
            if(getUIAdapter() != null && isEditBoard())
            {
                getTObject().setValue("X",value);
                return;
            }
            setX(StringTool.getInt(value));
            return;
        }
        if("Y".equalsIgnoreCase(name))
        {
            if(getUIAdapter() != null && isEditBoard())
            {
                getTObject().setValue("Y",value);
                return;
            }
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
        if("AutoHSize".equalsIgnoreCase(name))
        {
            setAutoHSize(StringTool.getInt(value));
            getTObject().setValue("AutoHSize",value);
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
}
