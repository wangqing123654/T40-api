package com.dongyang.ui;

import com.dongyang.jdo.MaxLoad;

public interface TContainer {
    /**
     * 查找Tag对象
     * @param tag String
     * @return TComponent
     */
    public TComponent findObject(String tag);
    /**
     * 得到继承加载器
     * @return MaxLoad
     */
    public MaxLoad getMaxLoad();
}
