package com.dongyang.ui;

import com.dongyang.ui.TComboBox;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import com.dongyang.config.TConfigParse.TObject;
import com.dongyang.ui.TComboNode;
import com.dongyang.ui.event.TComboBoxEvent;
import com.dongyang.ui.edit.TAttributeList;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TPositionCombo extends TComboBox {
    public static final String SUB_TEXT="下标";
    public static final String SUP_TEXT="上标";
    public static final String NORMAL_TEXT="普通";
    /**
     *
     */
    public TPositionCombo() {
        setData(new String[] {NORMAL_TEXT,SUP_TEXT,SUB_TEXT });
        setPreferredWidth(80);
        setExpandWidth(170);
        setShowID(false);
        setCanEdit(true);

    }

    /**
     * 新建对象的初始值
     * @param object TObject
     */
    public void createInit(TObject object) {
       //System.out.println(" object is"+object);
        if (object == null) {
            return;
        }
        object.setValue("Width", "81");
        object.setValue("Height", "23");
        object.setValue("Editable", "Y");
        object.setValue("Tip", "文字位置");
    }

    /**
     * 增加扩展属性
     * @param data TAttributeList
     */
    public void getEnlargeAttributes(TAttributeList data) {
    }

    /**
     * 动作事件
     * @param e ActionEvent
     */
    public void onActionPerformed(ActionEvent e) {
        //System.out.println("TPositionCombo onActionPerformed=======================");
        final TComboNode node = getSelectedNode();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                callEvent(TComboBoxEvent.SELECTED, new Object[] {}, new String[] {});
                callMessage(getTag() + "->" + TComboBoxEvent.SELECTED, node);
            }
        });
    }

}
