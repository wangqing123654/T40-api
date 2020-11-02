package com.dongyang.ui.base;

import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import com.dongyang.ui.event.TActionListener;

public class JTextAreaBase extends JTextArea
{
    /**
     * 父类
     */
    private TTextAreaBase textArea;
    /**
     * 设置父类
     * @param textArea TTextAreaBase
     */
    public void setTextArea(TTextAreaBase textArea)
    {
        this.textArea = textArea;
    }
    /**
     * 得到父类
     * @return TTextAreaBase
     */
    public TTextAreaBase getTextArea()
    {
        return textArea;
    }
    /**
     * 键盘事件处理器
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
