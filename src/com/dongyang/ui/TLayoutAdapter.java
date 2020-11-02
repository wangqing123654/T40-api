package com.dongyang.ui;

import com.dongyang.config.TConfigParse.TObject;

public class TLayoutAdapter extends TLabel
{
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
        object.setValue("Text","TLayoutAdapter");
    }
}
