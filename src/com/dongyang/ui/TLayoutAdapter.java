package com.dongyang.ui;

import com.dongyang.config.TConfigParse.TObject;

public class TLayoutAdapter extends TLabel
{
    /**
     * �½�����ĳ�ʼֵ
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
