package com.dongyang.ui;

import com.dongyang.config.TConfigParse.TObject;
import com.dongyang.ui.edit.TAttributeList.TAttribute;
import com.dongyang.ui.edit.TAttributeList;
import com.dongyang.ui.event.TComboBoxEvent;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;

/**
 *
 * <p>Title: 字体尺寸下拉列表框</p>
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
public class TFontSizeCombo extends TComboBox
{
    /**
     * 构造器
     */
    public TFontSizeCombo()
    {
        setData(new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","14","16","18","20","22","24","26","28","36","48","72"});
        setPreferredWidth(40);
        setExpandWidth(50);
        setShowID(false);
        setCanEdit(true);
    }
    /**
     * 新建对象的初始值
     * @param object TObject
     */
    public void createInit(TObject object)
    {
        //System.out.println("TFontSizeCombo createInit");
        if(object == null)
            return;
        object.setValue("Width","81");
        object.setValue("Height","23");
        object.setValue("Editable","Y");
        object.setValue("Tip","字体尺寸");
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
        //System.out.println("font size cmbo onActionPerformed!");
        final TComboNode node = getSelectedNode();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                callEvent(TComboBoxEvent.SELECTED,new Object[]{},new String[]{});
                callMessage(getTag() + "->" + TComboBoxEvent.SELECTED,node);
            }
        });
    }
}
