package com.dongyang.ui.event;

public interface TTableEvent extends TEvent
{
    /**
     * 直改变事件
     */
    public static final String CHANGE_VALUE = "table.changeValue";
    /**
     * 多选框单击
     */
    public static final String CHECK_BOX_CLICKED = "table.checkBoxClicked";
    /**
     * 单击
     */
    public static final String CLICKED = "table.clicked";
    /**
     * 双击
     */
    public static final String DOUBLE_CLICKED = "table.doubleClicked";
    /**
     * 右键点击
     */
    public static final String RIGHT_CLICKED = "table.rightClicked";
    /**
     * 创建编辑控件时
     * "java.awt.Component","int","int"
     */
    public static final String CREATE_EDIT_COMPONENT = "table.createEditComponent";
}
