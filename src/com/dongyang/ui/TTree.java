package com.dongyang.ui;

import com.dongyang.ui.base.TTreeBase;
import com.dongyang.ui.edit.TAttributeList.TAttribute;
import com.dongyang.ui.edit.TUIAdapter;
import com.dongyang.config.TConfigParse.Row;
import com.dongyang.util.StringTool;
import com.dongyang.config.TConfigParse.TObject;
import com.dongyang.ui.edit.TAttributeList;
import com.dongyang.util.TypeTool;

public class TTree extends TTreeBase{
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
        object.setValue("Width","81");
        object.setValue("Height","81");
        object.setValue("SpacingRow","1");
        object.setValue("RowHeight","20");
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
        data.add(new TAttribute("ControlClassName","String","","Left"));
        data.add(new TAttribute("Pics","String","","Left"));
        data.add(new TAttribute("ClickedItemAction","String","","Left"));
        data.add(new TAttribute("DoubleClickedItemAction","String","","Left"));
        data.add(new TAttribute("TreeExpandedAction","String","","Left"));
        data.add(new TAttribute("TreeCollapsedAction","String","","Left"));
        data.add(new TAttribute("RowHeight","int","0","Right"));
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
        if("Tip".equalsIgnoreCase(name))
        {
            setToolTipText(value);
            getTObject().setValue("Tip",value);
            return;
        }
        if("ControlClassName".equalsIgnoreCase(name))
        {
            getTObject().setValue("ControlClassName",value);
            return;
        }
        if("Text".equalsIgnoreCase(name))
        {
            setText(value);
            getTObject().setValue("Text",value);
            return;
        }
        if("Pics".equalsIgnoreCase(name))
        {
            setPics(value);
            getTObject().setValue("Pics",value);
        }
        if("DoubleClickedItemAction".equalsIgnoreCase(name))
        {
            setDoubleClickedItemAction(value);
            getTObject().setValue("DoubleClickedItemAction",value);
        }
        if("ClickedItemAction".equalsIgnoreCase(name))
        {
            setClickedItemAction(value);
            getTObject().setValue("ClickedItemAction",value);
        }
        if("TreeExpandedAction".equalsIgnoreCase(name))
        {
            setTreeExpandedAction(value);
            getTObject().setValue("TreeExpandedAction",value);
        }
        if("treeCollapsedAction".equalsIgnoreCase(name))
        {
            setTreeCollapsedAction(value);
            getTObject().setValue("treeCollapsedAction",value);
        }
        if("RowHeight".equalsIgnoreCase(name))
        {
            setRowHeight(TypeTool.getInt(value));
            getTObject().setValue("RowHeight",value);
            return;
        }
        if("X".equalsIgnoreCase(name))
        {
            setX(TypeTool.getInt(value));
            return;
        }
        if("Y".equalsIgnoreCase(name))
        {
            setY(TypeTool.getInt(value));
            return;
        }
        if("Width".equalsIgnoreCase(name))
        {
            setWidth(TypeTool.getInt(value));
            return;
        }
        if("Height".equalsIgnoreCase(name))
        {
            setHeight(TypeTool.getInt(value));
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
        updateUI();
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
        updateUI();
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
        updateUI();
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
        updateUI();
    }
}
