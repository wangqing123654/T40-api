package com.dongyang.ui;

import com.dongyang.ui.base.TWindowBase;
import java.awt.Frame;

/**
 *
 * <p>Title: 浮动窗口</p>
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
public class TWindow extends TWindowBase
{
    /**
     * 构造器
     */
    public TWindow()
    {
    }
    /**
     * 构造器
     * @param owner Frame
     */
    public TWindow(Frame owner)
    {
        super(owner);
    }

}
