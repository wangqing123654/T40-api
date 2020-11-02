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
     * 内部编辑器对象
     */
    private JTextAreaBase textArea;
    /**
     * 动作消息
     */
    private String actionMessage;
    /**
     * 动作列表
     */
    private ActionMessage actionList;
    /**
     * 鼠标事件监听对象
     */
    TMouseListener mouseListenerObject;

    /**
     * 构造器
     */
    public TTextAreaBase() {
        setTextArea(new JTextAreaBase());
        getTextArea().setTextArea(this);
        actionList = new ActionMessage();
    }
    /**
     * 初始化
     */
    public void onInit()
    {
        //初始化监听事件
        initListeners();
        //初始化参数准备
        if (getControl() != null)
            getControl().onInitParameter();
        //初始化控制类
        if (getControl() != null)
            getControl().onInit();
    }
    /**
     * 初始化监听事件
     */
    public void initListeners()
    {
        super.initListeners();
        //监听鼠标事件
        if(mouseListenerObject == null)
        {
            mouseListenerObject = new TMouseListener(this);
            getTextArea().addMouseListener(mouseListenerObject);
        }


        //监听Mouse事件
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_LEFT_CLICKED,"onClicked");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_DOUBLE_CLICKED,"onDoubleClicked");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_RIGHT_CLICKED,"onRightClicked");
        //addEventListener(getTag() + "->" + TActionListener.ACTION_PERFORMED,"onAction");
    }
    /**
     * 点击
     * @param e MouseEvent
     */
    public void onClicked(MouseEvent e)
    {
        exeAction(getClickedAction());
    }
    /**
     * 右击
     * @param e MouseEvent
     */
    public void onRightClicked(MouseEvent e)
    {
        exeAction(getRightClickedAction());
    }
    /**
     * 双击
     * @param e MouseEvent
     */
    public void onDoubleClicked(MouseEvent e)
    {
        exeAction(getDoubleClickedAction());
    }
    /**
     * 设置原始编辑对象
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
     * 设置原始编辑对象
     * @return JTextAreaBase
     */
    public JTextAreaBase getTextArea()
    {
        return textArea;
    }
    /**
     * 设置是否自动换行
     * @param wrap boolean
     */
    public void setLineWrap(boolean wrap)
    {
        getTextArea().setLineWrap(wrap);
    }
    /**
     * 得到是否自动换行
     * @return boolean
     */
    public boolean isLineWrap()
    {
        return getTextArea().getLineWrap();
    }
    /**
     * 设置文本
     * @param text String
     */
    public void setText(String text) {
        getTextArea().setText(text);
    }

    /**
     * 得到文本
     * @return String
     */
    public String getText() {
        return getTextArea().getText();
    }
    /**
     * 设置值
     * @param value String
     */
    public void setValue(String value) {
        setText(value);
    }

    /**
     * 得到值
     * @return String
     */
    public String getValue() {
        return getText();
    }
    /**
     * 设置动作消息
     * @param actionMessage String
     */
    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    /**
     * 得到动作消息
     * @return String
     */
    public String getActionMessage() {
        return actionMessage;
    }
    /**
     * 得到焦点
     */
    public void grabFocus() {
        super.grabFocus();
        getTextArea().grabFocus();
    }
    /**
     * 选蓝全部文字
     */
    public void selectAll(){
        getTextArea().selectAll();
    }
    /**
     * 执行默认动作
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
     * 发送动作消息
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
     * 查找Tag对象
     * @param tag String
     * @return TComponent
     */
    public TComponent findObject(String tag)
    {
        return null;
    }
    /**
     * 重新命名属性名称
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[]) {
        super.baseFieldNameChange(value);
        if ("action".equalsIgnoreCase(value[0]))
            value[0] = "actionMessage";
    }
    /**
     * 设置单击动作
     * @param action String
     */
    public void setClickedAction(String action)
    {
        actionList.setAction("clickedAction",action);
    }
    /**
     * 得到单击动作
     * @return String
     */
    public String getClickedAction()
    {
        return actionList.getAction("clickedAction");
    }
    /**
     * 设置双击动作
     * @param action String
     */
    public void setDoubleClickedAction(String action)
    {
        actionList.setAction("doubleClickedAction",action);
    }
    /**
     * 得到双击动作
     * @return String
     */
    public String getDoubleClickedAction()
    {
        return actionList.getAction("doubleClickedAction");
    }
    /**
     * 设置右击动作
     * @param action String
     */
    public void setRightClickedAction(String action)
    {
        actionList.setAction("doubleRightdAction",action);
    }
    /**
     * 得到右击动作
     * @return String
     */
    public String getRightClickedAction()
    {
        return actionList.getAction("doubleRightdAction");
    }
    /**
     * 执行动作
     * @param message String 消息
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
