package com.dongyang.ui.base;

import com.dongyang.ui.TScrollPane;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.event.TMouseListener;
import com.dongyang.util.StringTool;
import com.dongyang.ui.event.TActionListener;
import com.dongyang.ui.event.ActionMessage;
import java.awt.event.MouseEvent;

public class TTextAreaBase extends TScrollPane
{
    /**
     * �ڲ��༭������
     */
    private JTextAreaBase textArea;
    /**
     * ������Ϣ
     */
    private String actionMessage;
    /**
     * �����б�
     */
    private ActionMessage actionList;
    /**
     * ����¼���������
     */
    TMouseListener mouseListenerObject;

    /**
     * ������
     */
    public TTextAreaBase() {
        setTextArea(new JTextAreaBase());
        getTextArea().setTextArea(this);
        actionList = new ActionMessage();
    }
    /**
     * ��ʼ��
     */
    public void onInit()
    {
        //��ʼ�������¼�
        initListeners();
        //��ʼ������׼��
        if (getControl() != null)
            getControl().onInitParameter();
        //��ʼ��������
        if (getControl() != null)
            getControl().onInit();
    }
    /**
     * ��ʼ�������¼�
     */
    public void initListeners()
    {
        super.initListeners();
        //��������¼�
        if(mouseListenerObject == null)
        {
            mouseListenerObject = new TMouseListener(this);
            getTextArea().addMouseListener(mouseListenerObject);
        }


        //����Mouse�¼�
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_LEFT_CLICKED,"onClicked");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_DOUBLE_CLICKED,"onDoubleClicked");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_RIGHT_CLICKED,"onRightClicked");
        //addEventListener(getTag() + "->" + TActionListener.ACTION_PERFORMED,"onAction");
    }
    /**
     * ���
     * @param e MouseEvent
     */
    public void onClicked(MouseEvent e)
    {
        exeAction(getClickedAction());
    }
    /**
     * �һ�
     * @param e MouseEvent
     */
    public void onRightClicked(MouseEvent e)
    {
        exeAction(getRightClickedAction());
    }
    /**
     * ˫��
     * @param e MouseEvent
     */
    public void onDoubleClicked(MouseEvent e)
    {
        exeAction(getDoubleClickedAction());
    }
    /**
     * ����ԭʼ�༭����
     * @param textArea JTextAreaBase
     */
    public void setTextArea(JTextAreaBase textArea)
    {
        if(this.textArea == textArea)
            return;
        if(this.textArea != null)
            getViewport().remove(this.textArea);
        this.textArea = textArea;
        getViewport().add(textArea);
    }
    /**
     * ����ԭʼ�༭����
     * @return JTextAreaBase
     */
    public JTextAreaBase getTextArea()
    {
        return textArea;
    }
    /**
     * �����Ƿ��Զ�����
     * @param wrap boolean
     */
    public void setLineWrap(boolean wrap)
    {
        getTextArea().setLineWrap(wrap);
    }
    /**
     * �õ��Ƿ��Զ�����
     * @return boolean
     */
    public boolean isLineWrap()
    {
        return getTextArea().getLineWrap();
    }
    /**
     * �����ı�
     * @param text String
     */
    public void setText(String text) {
        getTextArea().setText(text);
    }

    /**
     * �õ��ı�
     * @return String
     */
    public String getText() {
        return getTextArea().getText();
    }
    /**
     * ����ֵ
     * @param value String
     */
    public void setValue(String value) {
        setText(value);
    }

    /**
     * �õ�ֵ
     * @return String
     */
    public String getValue() {
        return getText();
    }
    /**
     * ���ö�����Ϣ
     * @param actionMessage String
     */
    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    /**
     * �õ�������Ϣ
     * @return String
     */
    public String getActionMessage() {
        return actionMessage;
    }
    /**
     * �õ�����
     */
    public void grabFocus() {
        super.grabFocus();
        getTextArea().grabFocus();
    }
    /**
     * ѡ��ȫ������
     */
    public void selectAll(){
        getTextArea().selectAll();
    }
    /**
     * ִ��Ĭ�϶���
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object onDefaultActionMessage(String message, Object parm) {
        String value[] = StringTool.getHead(message, "->");
        if (value[0].equalsIgnoreCase(getTag()) &&
            value[1].equalsIgnoreCase(TActionListener.ACTION_PERFORMED)) {
            Object result = sendActionMessage();
            if (result != null)
                return result;
            callMessage("afterFocus|" + getTag());
        }
        return null;
    }
    /**
     * ���Ͷ�����Ϣ
     * @return Object
     */
    public Object sendActionMessage() {
        if (getActionMessage() == null || getActionMessage().length() == 0)
            return null;
        String value[] = StringTool.parseLine(getActionMessage(), ';',
                                              "[]{}()''\"\"");
        for (int i = 0; i < value.length; i++) {
            callMessage(value[i]);
        }
        return "OK";
    }
    /**
     * ����Tag����
     * @param tag String
     * @return TComponent
     */
    public TComponent findObject(String tag)
    {
        return null;
    }
    /**
     * ����������������
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[]) {
        super.baseFieldNameChange(value);
        if ("action".equalsIgnoreCase(value[0]))
            value[0] = "actionMessage";
    }
    /**
     * ���õ�������
     * @param action String
     */
    public void setClickedAction(String action)
    {
        actionList.setAction("clickedAction",action);
    }
    /**
     * �õ���������
     * @return String
     */
    public String getClickedAction()
    {
        return actionList.getAction("clickedAction");
    }
    /**
     * ����˫������
     * @param action String
     */
    public void setDoubleClickedAction(String action)
    {
        actionList.setAction("doubleClickedAction",action);
    }
    /**
     * �õ�˫������
     * @return String
     */
    public String getDoubleClickedAction()
    {
        return actionList.getAction("doubleClickedAction");
    }
    /**
     * �����һ�����
     * @param action String
     */
    public void setRightClickedAction(String action)
    {
        actionList.setAction("doubleRightdAction",action);
    }
    /**
     * �õ��һ�����
     * @return String
     */
    public String getRightClickedAction()
    {
        return actionList.getAction("doubleRightdAction");
    }
    /**
     * ִ�ж���
     * @param message String ��Ϣ
     * @return Object
     */
    public Object exeAction(String message)
    {
        if (message == null || message.length() == 0)
            return null;
        String value[] = StringTool.parseLine(message, ';',"[]{}()''\"\"");
        for (int i = 0; i < value.length; i++) {
            String actionMessage = value[i];
            if ("call".equalsIgnoreCase(actionMessage))
                actionMessage = getText();
            callMessage(actionMessage);
        }
        return "OK";
    }
}
