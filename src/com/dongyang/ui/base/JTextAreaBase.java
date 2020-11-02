package com.dongyang.ui.base;

import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import com.dongyang.ui.event.TActionListener;

public class JTextAreaBase extends JTextArea
{
    /**
     * ����
     */
    private TTextAreaBase textArea;
    /**
     * ���ø���
     * @param textArea TTextAreaBase
     */
    public void setTextArea(TTextAreaBase textArea)
    {
        this.textArea = textArea;
    }
    /**
     * �õ�����
     * @return TTextAreaBase
     */
    public TTextAreaBase getTextArea()
    {
        return textArea;
    }
    /**
     * �����¼�������
     * @param ks KeyStroke
     * @param e KeyEvent
     * @param condition int
     * @param pressed boolean
     * @return boolean
     */
    protected boolean processKeyBinding(KeyStroke ks, KeyEvent e,
                                        int condition, boolean pressed) {
        if(e.isAltDown() && pressed && condition == 0 && e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            getTextArea().callEvent(getTextArea().getTag() + "->" + TActionListener.ACTION_PERFORMED,new Object[]{null},new String[]{"java.awt.event.ActionEvent"});
            getTextArea().callMessage(getTextArea().getTag() + "->" + TActionListener.ACTION_PERFORMED,e);
            return false;
        }
        return super.processKeyBinding(ks,e,condition,pressed);
    }
}
