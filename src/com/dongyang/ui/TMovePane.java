package com.dongyang.ui;

import com.dongyang.ui.base.TMovePaneBase;
import com.dongyang.config.TConfigParse.TObject;
import com.dongyang.ui.edit.TAttributeList.TAttribute;
import com.dongyang.ui.edit.TAttributeList;
import com.dongyang.util.StringTool;
import com.dongyang.util.TypeTool;

public class TMovePane extends TMovePaneBase{
    /**
     * 新建对象的初始值
     * @param object TObject
     */
    public void createInit(TObject object)
    {
        if(object == null)
            return;
        object.setValue("Width","100");
        object.setValue("Height","15");
        object.setValue("Text","TMovePane");
        object.setValue("MoveType","0");
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
        data.add(new TAttribute("color","String","黑","Left"));
        data.add(new TAttribute("BKColor","String","","Left"));
        data.add(new TAttribute("HorizontalAlignment","HorizontalAlignmentComboBox","","Left"));
        data.add(new TAttribute("VerticalAlignment","VerticalAlignmentComboBox","","Left"));
        data.add(new TAttribute("controlClassName","String","","Left"));
        data.add(new TAttribute("Border","String","","Left"));
        data.add(new TAttribute("EntityData","String","","Left"));
        data.add(new TAttribute("MoveParent","boolean","N","Center"));
        data.add(new TAttribute("ResizeParent","boolean","N","Center"));
        data.add(new TAttribute("MoveType","MoveTypeComboBox","","Left"));
        data.add(new TAttribute("Style","int","0","Right"));
        data.add(new TAttribute("CursorType","int","0","Right"));
        data.add(new TAttribute("DoubleClickType","int","0","Right"));
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
        if ("EntityData".equalsIgnoreCase(name))
        {
            setEntityData(value);
            getTObject().setValue("EntityData", value);
            return;
        }
        if ("MoveType".equalsIgnoreCase(name))
        {
            setMoveType(StringTool.getInt(value));
            getTObject().setValue("MoveType", value);
            return;
        }
        if ("Style".equalsIgnoreCase(name))
        {
            setStyle(StringTool.getInt(value));
            getTObject().setValue("Style", value);
            return;
        }
        if ("CursorType".equalsIgnoreCase(name))
        {
            setCursorType(StringTool.getInt(value));
            getTObject().setValue("CursorType", value);
            return;
        }
        if ("DoubleClickType".equalsIgnoreCase(name))
        {
            setDoubleClickType(StringTool.getInt(value));
            getTObject().setValue("DoubleClickType", value);
            return;
        }
        if ("MoveParent".equalsIgnoreCase(name))
        {
            setMoveParent(TypeTool.getBoolean(value));
            getTObject().setValue("MoveParent", value);
            return;
        }
        if ("ResizeParent".equalsIgnoreCase(name))
        {
            setResizeParent(TypeTool.getBoolean(value));
            getTObject().setValue("ResizeParent", value);
            return;
        }
        super.setAttribute(name,value);
    }
}
