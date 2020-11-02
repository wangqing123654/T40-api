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
 * <p>Title: ���������б��</p>
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
     * ������
     */
    public TFontCombo()
    {
        setData(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        setShowID(false);
        setPreferredWidth(80);
        setExpandWidth(170);
    }
    /**
     * �½�����ĳ�ʼֵ
     * @param object TObject
     */
    public void createInit(TObject object)
    {
        if(object == null)
            return;
        object.setValue("Width","81");
        object.setValue("Height","23");
        object.setValue("Editable","Y");
        object.setValue("Tip","����");
    }
    /**
     * ������չ����
     * @param data TAttributeList
     */
    public void getEnlargeAttributes(TAttributeList data)
    {
    }
    /**
     * �����¼�
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
