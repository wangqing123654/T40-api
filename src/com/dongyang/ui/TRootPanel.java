package com.dongyang.ui;

import com.dongyang.ui.base.TRootPanelBase;
import com.dongyang.ui.edit.TAttributeList.TAttribute;
import com.dongyang.ui.edit.TAttributeList;

/**
 *
 * <p>Title: 顶部面板</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.4.17
 * @version 1.0
 */
public class TRootPanel extends TRootPanelBase
{
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
        data.add(new TAttribute("controlClassName","String","","Left"));
        data.add(new TAttribute("Border","String","","Left"));
        data.add(new TAttribute("BKColor","String","161,220,230","Left"));
        data.add(new TAttribute("FocusList","String","","Left"));
        data.add(new TAttribute("ButtonGroupFlg","boolean","Y","Center"));
        data.add(new TAttribute("closeClickedAction","String","","Left"));
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
        return data;
    }
    /**
     * 设置属性
     * @param name String 属性名
     * @param value String 属性值
     */
    public void setAttribute(String name,String value)
    {
        if("closeClickedAction".equalsIgnoreCase(name))
        {
            setCloseClickedAction(value);
            getTObject().setValue("closeClickedAction",value);
            return;
        }
        super.setAttribute(name,value);
    }
}
