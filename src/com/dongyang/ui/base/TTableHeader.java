package com.dongyang.ui.base;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.UIManager;

/**
 * 目前被废除 调用点为JTableBase中
 * <p>Title: </p>
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
public class TTableHeader extends JTableHeader
{
    public TTableHeader()
    {
        this(null);
    }
    public TTableHeader(TableColumnModel cm)
    {
        super(cm);
    }
    public void updateUI(){
        com.sun.java.swing.plaf.windows.WindowsTableHeaderUI a;
        TableHeaderUI ui = (TableHeaderUI)UIManager.getUI(this);
        System.out.println(ui.getClass().getName());
        setUI(ui);
        resizeAndRepaint();
        invalidate();//PENDING
    }
}
