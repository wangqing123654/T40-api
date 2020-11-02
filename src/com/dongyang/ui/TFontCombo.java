package com.dongyang.ui;

import java.awt.Font;
import java.awt.TextField;
import java.awt.GraphicsEnvironment;
import com.dongyang.config.TConfigParse.TObject;
import com.dongyang.ui.edit.TAttributeList.TAttribute;
import com.dongyang.ui.edit.TAttributeList;
import com.dongyang.ui.event.TComboBoxEvent;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;

/**
 *
 * <p>Title: 字体下拉列表框</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.10.14
 * @version 1.0
 */
public class TFontCombo extends TComboBox
{
    /**
     * 构造器
     */
    public TFontCombo()
    {
        setData(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        setShowID(false);
        setPreferredWidth(80);
        setExpandWidth(170);
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
        object.setValue("Tip","字体");
    }
    /**
     * 增加扩展属性
     * @param data TAttributeList
     */
    public void getEnlargeAttributes(TAttributeList data)
    {
    }
    /**
     * 动作事件
     * @param e ActionEvent
     */
    public void onActionPerformed(ActionEvent e)
    {
        final TComboNode node = getSelectedNode();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                callEvent(TComboBoxEvent.SELECTED,new Object[]{},new String[]{});
                callMessage(getTag() + "->" + TComboBoxEvent.SELECTED,node);
            }
        });
    }
}
