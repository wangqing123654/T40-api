package com.dongyang.ui;

import com.dongyang.jdo.MaxLoad;

public interface TContainer {
    /**
     * ����Tag����
     * @param tag String
     * @return TComponent
     */
    public TComponent findObject(String tag);
    /**
     * �õ��̳м�����
     * @return MaxLoad
     */
    public MaxLoad getMaxLoad();
}
