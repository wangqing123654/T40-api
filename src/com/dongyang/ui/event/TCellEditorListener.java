package com.dongyang.ui.event;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import com.dongyang.ui.TComponent;

public class TCellEditorListener implements CellEditorListener,TEvent {
    /**
     * 编辑停止事件
     */
    public static final String EDITING_STOPPED = "editingStopped";
    /**
     * 编辑取消
     */
    public static final String EDITING_CANCELED = "editingCanceled";
    /**
     * 父类组件
     */
    private TComponent component;
    /**
     * 构造器
     * @param component TComponent
     */
    public TCellEditorListener(TComponent component) {
        QUEUE_EVENT.INIT = true;
        this.component = component;
    }
    /**
     * 编辑停止事件
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
     * 编辑取消
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
