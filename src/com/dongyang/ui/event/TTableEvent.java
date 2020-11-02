package com.dongyang.ui.event;

public interface TTableEvent extends TEvent
{
    /**
     * ֱ�ı��¼�
     */
    public static final String CHANGE_VALUE = "table.changeValue";
    /**
     * ��ѡ�򵥻�
     */
    public static final String CHECK_BOX_CLICKED = "table.checkBoxClicked";
    /**
     * ����
     */
    public static final String CLICKED = "table.clicked";
    /**
     * ˫��
     */
    public static final String DOUBLE_CLICKED = "table.doubleClicked";
    /**
     * �Ҽ����
     */
    public static final String RIGHT_CLICKED = "table.rightClicked";
    /**
     * �����༭�ؼ�ʱ
     * "java.awt.Component","int","int"
     */
    public static final String CREATE_EDIT_COMPONENT = "table.createEditComponent";
}
