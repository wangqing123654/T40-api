package com.dongyang.ui.event;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import com.dongyang.ui.TComponent;

public class TCellEditorListener implements CellEditorListener,TEvent {
    /**
     * �༭ֹͣ�¼�
     */
    public static final String EDITING_STOPPED = "editingStopped";
    /**
     * �༭ȡ��
     */
    public static final String EDITING_CANCELED = "editingCanceled";
    /**
     * �������
     */
    private TComponent component;
    /**
     * ������
     * @param component TComponent
     */
    public TCellEditorListener(TComponent component) {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * �༭ֹͣ�¼�
     * @param e ChangeEvent
     */
    public void editingStopped(ChangeEvent e) {
        if (component == null)
            return;
        component.callEvent(component.getTag() + "->" + EDITING_STOPPED,
                            new Object[] {e},
                            new String[] {"javax.swing.event.ChangeEvent"});
    }
    /**
     * �༭ȡ��
     * @param e ChangeEvent
     */
    public void editingCanceled(ChangeEvent e) {
        if (component == null)
            return;
        component.callEvent(component.getTag() + "->" + EDITING_CANCELED,
                            new Object[] {e},
                            new String[] {"javax.swing.event.ChangeEvent"});
    }
}
