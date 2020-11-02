package com.dongyang.ui;

import com.dongyang.config.TConfigParse.TObject;
import com.dongyang.ui.edit.TAttributeList;

/**
 *
 * <p>Title: 显示比例缩放拉列表框</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TShowZoomCombo extends TComboBox
{
    /**
     * 构造器
     */
    public TShowZoomCombo()
    {
        setCanEdit(true);
        setData(new String[]{"500%","200%","150%","100%","75%","50%","25%","10%"});
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
        object.setValue("Height","23");
        object.setValue("Editable","Y");
        object.setValue("Tip","显示比例");
    }
    /**
     * 增加扩展属性
     * @param data TAttributeList
     */
    public void getEnlargeAttributes(TAttributeList data)
    {
    }
}
