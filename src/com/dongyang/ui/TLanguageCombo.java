package com.dongyang.ui;

import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import com.dongyang.config.TConfigParse.TObject;
import com.dongyang.ui.event.TComboBoxEvent;
import com.dongyang.ui.edit.TAttributeList;
import com.dongyang.config.TConfig;
import com.dongyang.util.StringTool;

/**
 *
 * <p>Title: 语种下拉列表框</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2010.2.22
 * @version 1.0
 */
public class TLanguageCombo extends TComboBox
{
    /**
     * 构造器
     */
    public TLanguageCombo()
    {
        setData(StringTool.parseLine(TConfig.getSystemValue("Language"),";"),",");
        setPreferredWidth(100);
        setExpandWidth(50);
        setShowID(false);
        //setCanEdit(true);
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
        object.setValue("Tip","语种");
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
    /**
     * 设置语种
     * @param language String
     */
    public void changeLanguage(String language)
    {
    }
}
